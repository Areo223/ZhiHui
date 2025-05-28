package org.areo.zhihui.utils;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.areo.zhihui.annotation.Exists;
import org.areo.zhihui.pojo.entity.User;
import org.areo.zhihui.utils.enums.RoleEnum;

import java.util.Arrays;
import java.util.List;

@Slf4j
public class ExistsValidator implements ConstraintValidator<Exists, Object> {
    @Data
    public static class ValidationErrorResponse {
        private int status;
        private String message;
        private List<FieldErrorDetail> errors;
        private long timestamp;


        @Data
        public static class FieldErrorDetail {
            private String field;
            private Object rejectedValue;
            private String message;

        }
    }

    private Class<?> entity;
    private String field;
    private RoleEnum[] roles;

    @Override
    public void initialize(Exists constraintAnnotation) {
        this.entity = constraintAnnotation.entity();
        this.field = constraintAnnotation.field();
        this.roles = constraintAnnotation.roles();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        if (value == null) {
            return true; // 空值检查交给@NotNull处理
        }
        //先判断value是否是list,如果是list,则遍历list,判断每个元素是否存在
        if (value instanceof List<?> list) {
            for (Object obj : list) {
                if (!isValid(obj, context)) {
                    return false;
                }
            }
            return true;
        }
        BaseMapper<Object> mapper = MybatisReflect.getMapper(entity);
        if (mapper == null) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("未找到对应mapper: " + entity.getName())
                   .addConstraintViolation();
            log.error("未找到对应mapper: {}", entity.getName());
            return false;
        }
        //如果是User的话,需要判断身份是否正确对应
        if (entity.getName().equals("org.areo.zhihui.pojo.entity.User")) {
            //获取当前登录的用户
            Object obj = mapper.selectOne(new QueryWrapper<>().eq("id", value));
            if (obj != null) {
                User user = (User) obj;
                //如果user的role不在role集合中,则返回false
                if (roles != null && !Arrays.asList(roles).contains(user.getRole())) {
                    context.disableDefaultConstraintViolation();
                    context.buildConstraintViolationWithTemplate("身份不匹配")
                          .addConstraintViolation();
                    log.debug("身份不匹配,当前用户id:{}",value);
                    return false;
                }

            }
            else {
                context.disableDefaultConstraintViolation();
                context.buildConstraintViolationWithTemplate("用户不存在")
                      .addConstraintViolation();
                log.debug("用户不存在,当前用户id:{}",value);
                return false;
            }

        }
        return mapper.exists(new QueryWrapper<>().eq(field, value));



    }

}