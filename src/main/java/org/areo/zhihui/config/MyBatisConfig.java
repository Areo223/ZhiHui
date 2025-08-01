package org.areo.zhihui.config;

import com.baomidou.mybatisplus.autoconfigure.ConfigurationCustomizer;
import org.areo.zhihui.utils.UUIDTypeHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.UUID;

@Configuration
public class MyBatisConfig {
    @Bean
    public ConfigurationCustomizer configurationCustomizer() {
        return configuration -> {
            configuration.getTypeHandlerRegistry().register(UUID.class, new UUIDTypeHandler());
        };
    }
}
