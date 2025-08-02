package org.areo.zhihui.services.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.areo.zhihui.exception.CommonException;
import org.areo.zhihui.exception.MailSendErrorException;
import org.areo.zhihui.exception.UserException.UserDeleteErrorException;
import org.areo.zhihui.exception.UserException.UserNotRegisteredException;
import org.areo.zhihui.exception.ValidatorException;
import org.areo.zhihui.mapper.UserMapper;
import org.areo.zhihui.pojo.dto.Result;
import org.areo.zhihui.pojo.entity.Student;
import org.areo.zhihui.pojo.entity.Teacher;
import org.areo.zhihui.pojo.entity.User;
import org.areo.zhihui.pojo.request.auth.UserListRequest;
import org.areo.zhihui.pojo.vo.*;
import org.areo.zhihui.services.UserService;
import org.areo.zhihui.utils.JwtUtils;
import org.areo.zhihui.utils.MailMsg;
import org.areo.zhihui.utils.UserHolder;
import org.areo.zhihui.utils.enums.LockedEnum;
import org.areo.zhihui.utils.enums.RoleEnum;
import org.areo.zhihui.utils.regex.RegexValidator;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.security.auth.login.AccountLockedException;
import java.time.Duration;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class UserServiceImpl implements UserService {

    final private UserMapper userMapper;
    final private PasswordEncoder passwordEncoder;
    final private JwtUtils jwtUtils;
    private final RedisTemplate redisTemplate;
    private final MailMsg mailMsg;

    @Override
    public Result<LoginVO> login(String identifier, String password) {
        log.info("开始登录流程，用户标识: {}", identifier);
        return validateInput(identifier, password)
                .flatMap(__ -> {
                    log.debug("输入参数校验通过");
                    return checkPasswordStrength(password);
                })
                .flatMap(__ -> {
                    log.debug("密码强度校验通过");
                    return checkUserStatus(identifier);
                })
                .flatMap(user -> {
                    log.info("用户状态检查完成，准备登录");
                    return doLogin(user, password);
                })
                .ifSuccess(loginResult ->
                        log.info("用户登录成功，标识: {}", identifier))
                .ifFailure((e, code) ->
                        log.warn("登录失败: 标识={}, 错误码={}, 原因={}", identifier, code, e.getMessage()));
    }

    @Override
    @Transactional
    public Result<Void> addUser(String identifier, String password,String name,RoleEnum role) {
        log.info("开始注册流程，用户标识: {}", identifier);

        // 检查用户是否已存在（防止并发注册）
        if (userMapper.exists(new QueryWrapper<User>().eq("identifier", identifier))) {
            log.warn("注册失败，用户已存在: {}", identifier);
            return Result.failure(new CommonException("该账号已注册"));
        }

        log.debug("开始加密密码");
        String encryptedPassword = passwordEncoder.encode(password);

        log.debug("构建用户对象");
        User user = new User();
        user.setIdentifier(identifier);
        user.setPassword(encryptedPassword);
        user.setRole(role);
        user.setName(name);
        user.setLocked(LockedEnum.NOT_LOCKED);

        try {
            log.debug("准备写入数据库");
            int affectedRows = userMapper.insert(user);
            if (affectedRows == 0) {
                log.error("数据库写入失败，标识: {}", identifier);
                return Result.failure(new RuntimeException("注册失败，数据库写入异常"));
            }
            log.info("用户注册成功，标识: {}", identifier);
            return Result.success(null);
        } catch (Exception e) {
            log.error("注册异常: identifier={}, 错误: {}", identifier, e.getMessage(), e);
            return Result.failure(e);
        }
    }

    @Override
    public Result<Void> deleteUser(Integer id) {
        log.debug("开始删除用户，ID: {}", id);
        try {
            int affectedRows = userMapper.deleteById(id);
            if (affectedRows == 0) {
                log.warn("用户不存在或已被删除，ID: {}", id);
                return Result.failure(new UserDeleteErrorException("用户不存在或已被删除"));
            }
            log.info("用户删除成功，ID: {}", id);
            return Result.success(null);
        } catch (Exception e) {
            throw new UserDeleteErrorException(e.getMessage());
        }
    }

    @Override
    public Result<Void> updateUser(Integer id, String name, String role, String identifier, String password) {

        log.debug("开始更新用户，ID: {}", id);
        User user = userMapper.selectById(id);
        if (user == null) {
            log.warn("用户不存在，ID: {}", id);
            return Result.failure(new UserDeleteErrorException("用户不存在"));
        }
        if (name != null) {
            user.setName(name);
        }
        if (role!= null) {
            user.setRole(RoleEnum.getEnumByDesc(role));
        }
        if (identifier!= null) {
            user.setIdentifier(identifier);
        }
        if (password!= null) {
            user.setPassword(passwordEncoder.encode(password));
        }
        try {
            int affectedRows = userMapper.updateById(user);
            if (affectedRows == 0) {
                log.warn("更新失败，ID: {}", id);
                return Result.failure(new UserDeleteErrorException("更新失败"));
            }
            log.info("用户更新成功，ID: {}", id);
            return Result.success(null);
        }
        catch (Exception e) {
            log.error("更新失败，ID: {}", id, e);
            return Result.failure(e);
        }
    }


    @Override
    public Result<Object> getOwnUserInfo(Integer id) {
        log.debug("开始查询当前用户信息");
        RoleEnum role = UserHolder.getUser().getRole();
        switch (role) {
            case ADMIN:
                log.debug("当前用户为管理员，查询用户 ID: {}", id);
                User user = userMapper.selectById(id);
                UserVO userVO = new UserVO();
                BeanUtils.copyProperties(user,userVO,UserVO.class);
                return Result.success(userVO);
            case TEACHER:
                log.debug("当前用户为教师，查询用户 ID: {}", id);
                Teacher teacherInfo = userMapper.getOwnTeacherInfo(id);
                TeacherVO teacherVO = new TeacherVO();
                BeanUtils.copyProperties(teacherInfo,teacherVO,TeacherVO.class);
                return Result.success(teacherVO);
            case STUDENT:
                log.debug("当前用户为学生，查询用户 ID: {}", id);
                Student studentInfo =userMapper.getOwnStudentInfo(id);
                StudentVO studentVO = new StudentVO();
                BeanUtils.copyProperties(studentInfo,studentVO,StudentVO.class);
                return Result.success(studentVO);
            default:
                log.warn("未知用户角色: {}", role);
                return Result.failure(new IllegalArgumentException("未知用户角色"));
        }

    }

    @Override
    public Result<List<UserVO>> getAllUser() {
        log.debug("开始查询所有用户");
        List<User> userList = userMapper.selectList(null);
        if (userList.isEmpty()) {
            log.warn("用户列表为空");
            return Result.failure(new UserDeleteErrorException("用户列表为空"));
        }
        //转换为UserVO列表
        List<UserVO> userVOList = userList.stream()
                                         .map(user -> {
                                             UserVO userVO = new UserVO();
                                             BeanUtils.copyProperties(user, userVO, UserVO.class);
                                             return userVO;
                                         })
                                         .toList();
        log.info("用户查询成功，共查询到 {} 条记录", userList.size());
        return Result.success(userVOList);
    }

    @Override
    public Result<List<UserVO>> getUsers(List<Integer> ids) {
        log.debug("开始查询用户，ID列表: {}", ids);
        List<User> userList = userMapper.selectByIds(ids);
        if (userList.isEmpty()) {
            log.warn("用户列表为空，ID列表: {}", ids);
            return Result.failure(new UserDeleteErrorException("用户列表为空"));
        }
        //转换为UserVO列表
        List<UserVO> userVOList = userList.stream()
                                        .map(user -> {
                                             UserVO userVO = new UserVO();
                                             BeanUtils.copyProperties(user, userVO, UserVO.class);
                                             return userVO;
                                         })
                                        .toList();
        return Result.success(userVOList);
    }

    @Override
    public Result<Void> passwordUpdate(String oldPassword, String newPassword) {
        // 先获取当前用户
        User user = UserHolder.getUser();
        // 然后检查旧密码是否正确
        if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
            log.warn("旧密码错误，用户ID: {}", user.getId());
            return Result.failure(new ValidatorException.ErrorPasswordException("旧密码错误"));
        }
        // 检查新密码强度
        if (!checkPasswordStrength(newPassword).isSuccess()) {
            log.warn("新密码强度不符合要求，用户ID: {}", user.getId());
            return Result.failure(new ValidatorException.InvalidPasswordException("新密码强度不符合要求"));
        }
        // 更新密码
        user.setPassword(passwordEncoder.encode(newPassword));
        try {
            int affectedRows = userMapper.updateById(user);
            if (affectedRows == 0) {
                log.error("密码更新失败，用户ID: {}", user.getId());
                return Result.failure(new RuntimeException("密码更新失败"));
            }
            log.info("密码更新成功，用户ID: {}", user.getId());
            return Result.success(null);
        } catch (Exception e) {
            log.error("密码更新异常，用户ID: {}", user.getId(), e);
            return Result.failure(e);
        }
    }

    @Override
    public Result<Void> forgetPassword(String identifier) {
        // 检查用户是否存在
        User user = userMapper.selectOne(new QueryWrapper<User>().eq("identifier", identifier));
        if (user == null) {
            log.warn("用户不存在，标识: {}", identifier);
            return Result.failure(new UserNotRegisteredException("用户不存在"));
        }
        //如果用户存在
        //检查用户是否被锁定
        if (user.checkIfLocked()) {
            log.warn("用户已被锁定，标识: {}", identifier);
            return Result.failure(new AccountLockedException("用户已被锁定"));
        }

        String token = jwtUtils.generateToken(user);
        //将token存入redis，设置过期时间为10分钟
        redisTemplate.opsForValue().set("pwd_reset:"+token, user.getIdentifier(), Duration.ofMinutes(10));

        String resetLink = "http://localhost:8080/user/resetPassword?token=" + token;
        //发送邮箱验证码
        try {
            if(!mailMsg.linkMail(user.getEmail(),resetLink)){
                log.warn("发送邮箱验证码失败，标识: {}", identifier);
                return Result.failure(new MailSendErrorException("发送邮箱验证码失败"));
            }
        } catch (MessagingException e) {
            throw new MailSendErrorException(e.getMessage());
        }
        return Result.success(null);
    }

    @Override
    public Result<Void> resetPassword(String token, String newPassword) {
        // 检查token是否存在
        String identifier = (String) redisTemplate.opsForValue().get("pwd_reset:" + token);
        if (identifier == null) {
            log.warn("无效或过期的token，token: {}", token);
            return Result.failure(new ValidatorException.InvalidTokenException("无效或过期的token"));
        }
        // 检查新密码强度
        if (!checkPasswordStrength(newPassword).isSuccess()) {
            log.warn("新密码强度不符合要求，token: {}", token);
            return Result.failure(new ValidatorException.InvalidPasswordException("新密码强度不符合要求"));
        }
        // 更新密码
        User user = userMapper.selectOne(new QueryWrapper<User>().eq("identifier", identifier));
        user.setPassword(passwordEncoder.encode(newPassword));
        try {
            int affectedRows = userMapper.updateById(user);
            if (affectedRows == 0) {
                log.error("密码重置失败，标识: {}", identifier);
                return Result.failure(new CommonException("密码重置失败"));
            }
            log.info("密码重置成功，标识: {}", identifier);
            return Result.success(null);
        }
        catch (Exception e) {
            log.error("密码重置异常，标识: {}", identifier, e);
            return Result.failure(e);
        }
    }

    @Override
    public Result<QueryVO<Object>> getUsers(Integer pageNum, Integer pageSize, Map<String, Boolean> sorts, UserListRequest.Conditions conditions) {
        log.debug("开始查询用户列表，页码: {}, 每页大小: {}", pageNum, pageSize);
        Page<User> page = new Page<>(pageNum, pageSize);

        //根据排序字段依次排序
        if (sorts != null) {
            //按排序字段顺序优先级排序,降序为true,升序为false
            for (Map.Entry<String, Boolean> entry : sorts.entrySet()) {
                String sortField = entry.getKey();
                boolean isDesc = entry.getValue();
                OrderItem orderItem = new OrderItem();
                orderItem.setColumn(sortField);
                orderItem.setAsc(!isDesc);
                page.addOrder(orderItem);
            }
        }

        IPage<User> userPage = userMapper.selectPage(page,new LambdaQueryWrapper<User>()
                .eq(conditions.getRole() != null, User::getRole, conditions.getRole())
                .like(conditions.getName() != null, User::getName, conditions.getName())
                .like(conditions.getIdentifier() != null, User::getIdentifier, conditions.getIdentifier())
                .eq(conditions.getLocked() != null, User::getLocked, conditions.getLocked())
                .between(conditions.getStartTime() != null && conditions.getEndTime() != null
                        , User::getCreateTime, conditions.getStartTime(), conditions.getEndTime()));
        IPage<Object> userInfoPage = userPage.convert(user -> switch (user.getRole()) {
            case ADMIN -> user;
            case TEACHER -> userMapper.getOwnTeacherInfo(user.getId());
            case STUDENT -> userMapper.getOwnStudentInfo(user.getId());
            //            return switch (user.getRole()) {
            //                case ADMIN -> user;
            //                case TEACHER -> userMapper.getOwnTeacherInfo(user.getId());
            //                case STUDENT -> userMapper.getOwnStudentInfo(user.getId());
            //                default -> null;
            //            };
        });


        QueryVO<Object> queryVO = new QueryVO<>();
        queryVO.setData(userInfoPage.getRecords());
        queryVO.setTotal(userInfoPage.getTotal());
        queryVO.setPageNum((int) userInfoPage.getCurrent());
        queryVO.setPageSize((int) userInfoPage.getSize());

        log.info("用户列表查询成功，共查询到 {} 条记录", userPage.getTotal());
        return Result.success(queryVO);

    }

//    @Override
//    public Result<Object> getUserCount() {
//        //查询每个角色的用户数
//        Map<String, Long> userCountMap = userMapper.getUserCount();
//        log.info("用户数量查询成功，结果: {}", userCountMap);
//        return Result.success(userCountMap);
//    }

    private Result<Void> checkPasswordStrength(String password) {
        log.debug("检查密码强度");
        boolean isValid = RegexValidator.validate(password, RegexValidator.RegexPattern.PASSWORD);
        if (!isValid) {
            log.warn("密码强度不符合要求");
        }
        return isValid
                ? Result.success(null)
                : Result.failure(new ValidatorException.InvalidPasswordException("密码强度不符合要求"));
    }

    /**
     * 检查用户状态，如果未注册则自动注册
     */
    private Result<User> checkUserStatus(String identifier) {
        log.debug("检查用户状态，标识: {}", identifier);
        User user = userMapper.selectOne(new QueryWrapper<User>().eq("identifier", identifier));

        if (user != null) {
            log.debug("用户存在，检查状态");
            if (user.checkIfLocked()) {
                log.warn("用户已被锁定，标识: {}", identifier);
                return Result.failure(new AccountLockedException("用户已被锁定"));
            }
            if (!RegexValidator.validate(identifier, RegexValidator.RegexPattern.IDENTIFIER)) {
                log.warn("账号格式错误，标识: {}", identifier);
                return Result.failure(new IllegalArgumentException("账号格式错误"));
            }
            log.debug("用户状态正常");
            return Result.success(user);
        }

        log.debug("用户未注册，标识: {}", identifier);
        return Result.failure(new AccountLockedException("用户未注册"));
//        return addUser(request.getIdentifier(), identifier, password, request.getRole())
//                .ifSuccess(__ -> log.info("自动注册成功，标识: {}", identifier))
//                .flatMap(__ -> {
//                    log.debug("注册成功，重新查询用户信息");
//                    User newUser = userMapper.selectOne(new QueryWrapper<User>().eq("identifier", identifier));
//                    if (newUser == null) {
//                        log.error("注册后查询用户失败，标识: {}", identifier);
//                        return Result.failure(new RuntimeException("注册成功但查询用户失败"));
//                    }
//                    return Result.success(newUser);
//                });
    }

    private Result<Void> validateInput(String identifier, String password) {
        log.debug("验证输入参数");
        if (identifier == null || password == null) {
            log.warn("参数为空，标识: {}, 密码为空: {}", identifier, password == null);
            return Result.failure(new IllegalArgumentException("参数不能为空"));
        }
        return Result.success(null);
    }

    private Result<LoginVO> doLogin(User user, String password) {
        log.debug("开始登录验证，用户ID: {}", user.getId());

        if (!passwordEncoder.matches(password, user.getPassword())) {
            log.warn("密码验证失败，用户标识: {}", user.getIdentifier());
            return Result.failure(new ValidatorException.ErrorPasswordException("密码错误"));
        }

        log.debug("密码验证成功，生成Token");
        String token = jwtUtils.generateToken(user);
        log.info("登录成功，用户ID: {}, 标识: {}, 身份: {}", user.getId(), user.getIdentifier(),user.getRole());
        return Result.success(new LoginVO(token));
    }
}