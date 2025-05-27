package org.areo.zhihui.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import org.areo.zhihui.utils.ExistsValidator;
import org.areo.zhihui.utils.enums.RoleEnum;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ExistsValidator.class)
public @interface Exists {
    String message() default "不存在";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

    Class<?> entity();
    String field() default "id";

    //添加一个参数,用于指定要对应的身份
    RoleEnum[] roles() default {RoleEnum.ADMIN};
}
