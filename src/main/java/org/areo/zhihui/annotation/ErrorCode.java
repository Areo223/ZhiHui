package org.areo.zhihui.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)  // 注解在运行时生效
@Target(ElementType.TYPE)            // 只能标注在类上（如异常类）
public @interface ErrorCode {
    int value() default 400;         // 默认 HTTP 状态码为 400
    String message();   // 默认错误消息
    


}