package org.areo.zhihui.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.scripting.support.ResourceScriptSource;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class LuaToRedis {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    public Long executeSelectionScript(List<String> keys, List<String> args) {
        DefaultRedisScript<Long> redisScript = new DefaultRedisScript<>();
        redisScript.setResultType(Long.class);
        redisScript.setScriptSource(new ResourceScriptSource(new ClassPathResource("lua/select_course.lua")));
        return stringRedisTemplate.execute(redisScript, keys, args.toArray(new String[0]));
    }

    public Long executeWithdrawScript(List<String> keys, List<String> args) {
        DefaultRedisScript<Long> redisScript = new DefaultRedisScript<>();
        redisScript.setResultType(Long.class);
        redisScript.setScriptSource(new ResourceScriptSource(new ClassPathResource("lua/withdraw_course.lua")));
        return stringRedisTemplate.execute(redisScript, keys, args.toArray(new String[0]));
    }
}
