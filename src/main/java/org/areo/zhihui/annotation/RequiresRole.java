package org.areo.zhihui.annotation;

import org.areo.zhihui.utils.enums.RoleEnum;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD) // 注解作用在方法上
@Retention(RetentionPolicy.RUNTIME) // 运行时生效
public @interface RequiresRole {
    RoleEnum[] value(); // 允许的角色列表，例如 {"admin", "user"}
}
