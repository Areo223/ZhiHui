package org.areo.zhihui.config;

import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.scripting.support.ResourceScriptSource;

import java.util.Map;

@Configuration
public class RedisConfig {

    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(factory);
        // 设置序列化器
        template.setKeySerializer(new StringRedisSerializer());
        template.setHashKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        template.setHashValueSerializer(new GenericJackson2JsonRedisSerializer());
        return template;
    }
//
//    @Bean
//    public RedisScript<Map<String, Object>> selectCourseScript() {
//        DefaultRedisScript<Map<String, Object>> script = new DefaultRedisScript<>();
//        script.setScriptSource(new ResourceScriptSource(new ClassPathResource("lua/select_course.lua")));
//        script.setResultType(new TypeReference<Map<String, Object>>() {});
//        return script;
//    }
//
//    @Bean
//    public RedisScript<Map<String, Object>> withdrawCourseScript() {
//        DefaultRedisScript<Map<String, Object>> script = new DefaultRedisScript<>();
//        script.setScriptSource(new ResourceScriptSource(new ClassPathResource("lua/withdraw_course.lua")));
//        script.setResultType(new TypeReference<Map<String, Object>>() {});
//        return script;
//    }
}
