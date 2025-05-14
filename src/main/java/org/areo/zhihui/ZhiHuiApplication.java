package org.areo.zhihui;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(
        title = "智慧融媒体平台",
        version = "1.0",
        description = "ZhiHui API Documentation"
))

public class ZhiHuiApplication {


    public static void main(String[] args) {
        SpringApplication.run(ZhiHuiApplication.class, args);
    }

    @Bean // 配置PasswordEncoder
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
