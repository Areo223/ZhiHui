package org.areo.zhihui.config;

import lombok.RequiredArgsConstructor;
import org.areo.zhihui.interceptor.LoginCheckInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class WebConfig implements WebMvcConfigurer {
    private final LoginCheckInterceptor loginCheckInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginCheckInterceptor)
                .addPathPatterns("/**") // 拦截所有请求
                .excludePathPatterns("/user/login",
                        "/swagger-ui/**","/v3/**",//swagger组件
                        "/error","/webjars/**",//系统组件
                        "/user/forgetPassword",//忘记密码
                        "/user/resetPassword"//重置密码
                        );
    }
}
