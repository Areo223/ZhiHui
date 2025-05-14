package org.areo.zhihui.servises.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.areo.zhihui.exception.UserException.UserDeleteErrorException;
import org.areo.zhihui.exception.ValidatorException;
import org.areo.zhihui.mapper.UserMapper;
import org.areo.zhihui.pojo.dto.Result;
import org.areo.zhihui.pojo.entity.Student;
import org.areo.zhihui.pojo.entity.Teacher;
import org.areo.zhihui.pojo.entity.User;
import org.areo.zhihui.pojo.vo.LoginVO;
import org.areo.zhihui.pojo.vo.StudentVO;
import org.areo.zhihui.pojo.vo.TeacherVO;
import org.areo.zhihui.pojo.vo.UserVO;
import org.areo.zhihui.servises.UserService;
import org.areo.zhihui.utils.JwtUtils;
import org.areo.zhihui.utils.UserHolder;
import org.areo.zhihui.utils.enums.LockedEnum;
import org.areo.zhihui.utils.enums.RoleEnum;
import org.areo.zhihui.utils.regex.RegexValidator;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.security.auth.login.AccountLockedException;

@Slf4j
@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class UserServiceImpl implements UserService {

    final private UserMapper userMapper;
    final private PasswordEncoder passwordEncoder;
    final private JwtUtils jwtUtils;

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
    public Result<Void> addUser(String identifier, String password,String name,RoleEnum role) {
        log.info("开始注册流程，用户标识: {}", identifier);

        // 检查用户是否已存在（防止并发注册）
        if (userMapper.exists(new QueryWrapper<User>().eq("identifier", identifier))) {
            log.warn("注册失败，用户已存在: {}", identifier);
            return Result.failure(new IllegalArgumentException("该账号已注册"));
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
    public Result<UserVO> getUser(Integer id) {
        log.debug("开始查询用户，ID: {}", id);
        User user = userMapper.selectById(id);
        if (user == null) {
            log.warn("用户不存在，ID: {}", id);
            return Result.failure(new UserDeleteErrorException("用户不存在"));
        }
        log.info("用户查询成功，ID: {}", id);
        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(user,userVO,UserVO.class);
        return Result.success(userVO);
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

        log.info("用户未注册，尝试自动注册，标识: {}", identifier);
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