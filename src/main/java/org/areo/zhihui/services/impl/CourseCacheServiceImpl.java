package org.areo.zhihui.services.impl;

import io.lettuce.core.RedisCommandExecutionException;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.areo.zhihui.exception.CommonException;
import org.areo.zhihui.services.CourseCacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class CourseCacheServiceImpl implements CourseCacheService {
    private final RedisTemplate<String, Object> redisTemplate;
    private final StringRedisTemplate stringRedisTemplate;
    private String selectScriptSha;
    private String withdrawScriptSha;


    private static final String COURSE_CACHE_KEY = "course:stock:";
    private static final String COURSE_STUDENTS_KEY = "course:students:";
    private static final String LOCK_KEY = "course:lock:";

    @PostConstruct
    public void init() {
        this.selectScriptSha = loadScript("lua/select_course.lua");
        log.info("selectScriptSha:{}",selectScriptSha);
        this.withdrawScriptSha = loadScript("lua/withdraw_course.lua");
        log.info("withdrawScriptSha:{}",withdrawScriptSha);
    }

    private String loadScript(String scriptPath) {
        try {
            String script = new String(Files.readAllBytes(
                    Paths.get(getClass().getClassLoader().getResource(scriptPath).toURI())), StandardCharsets.UTF_8);
            return redisTemplate.execute(
                    (RedisCallback<String>) connection ->
                            connection.scriptLoad(script.getBytes()));
        } catch (Exception e) {
            throw new RuntimeException("Failed to load Lua script", e);
        }
    }


    @Autowired
    public CourseCacheServiceImpl(RedisTemplate<String, Object> redisTemplate, StringRedisTemplate stringRedisTemplate) {
        this.redisTemplate = redisTemplate;
        this.stringRedisTemplate = stringRedisTemplate;
    }

    //初始化课程库存缓存
    public void initCourseStockCache(String courseOfferingId, Integer capacity){
        redisTemplate.opsForHash().put(COURSE_CACHE_KEY+ courseOfferingId,"stock",capacity);
    }

    //获取课程缓存
    public Integer getCourseStock(String courseOfferingId){
        Object stock = redisTemplate.opsForHash().get(COURSE_CACHE_KEY + courseOfferingId, "stock");
        return stock !=null ? Integer.parseInt(stock.toString()):null;
    }

    //减少库存方法
    public boolean reduceCourseStock(String courseOfferingId){
        return redisTemplate.opsForHash().increment(COURSE_CACHE_KEY+ courseOfferingId,"stock",-1L)>0;

    }
    //增加库存方法
    public boolean increaseCourseStock(String courseOfferingId){
        return  redisTemplate.opsForHash().increment(COURSE_CACHE_KEY+ courseOfferingId,"stock",1L)>0;
    }

    //添加学生到课程中
    public Long addStudentToCourse(String courseOfferingId, String studentIdentifier){
        return redisTemplate.opsForSet().add(COURSE_STUDENTS_KEY+ courseOfferingId, studentIdentifier);
    }

    //从课程中移除学生
    public Long removeStudentFromCourse(String courseOfferingId, String studentIdentifier){
        return redisTemplate.opsForSet().remove(COURSE_STUDENTS_KEY+ courseOfferingId, studentIdentifier);
    }

    //检查学生是否已选课程
    public boolean isStudentInCourse(String courseOfferingId, String studentIdentifier){
        return Boolean.TRUE.equals(redisTemplate.opsForSet().isMember(COURSE_STUDENTS_KEY + courseOfferingId, studentIdentifier));
    }

    //获取课程已选学生数量
    public Long getCourseStudentCount(String courseOfferingId){
        return redisTemplate.opsForSet().size(COURSE_STUDENTS_KEY+ courseOfferingId);
    }

    //获取分布式锁
    public Boolean tryLock(String courseOfferingId, long expireSeconds){
        return stringRedisTemplate.opsForValue().setIfAbsent(
                LOCK_KEY+ courseOfferingId,
                "1",
                expireSeconds,
                TimeUnit.SECONDS
        );
    }

    //释放分布式锁
    public Boolean releaseLock(String courseOfferingId){
        return stringRedisTemplate.delete(LOCK_KEY+ courseOfferingId);
    }

    @Override
    public Integer getCourseMaxCapacity(String courseOfferingId) {
        Object maxCapacity = redisTemplate.opsForHash().get(COURSE_CACHE_KEY + courseOfferingId, "maxCapacity");
        return maxCapacity != null ? Integer.parseInt(maxCapacity.toString()) : null;
    }

    @Override
    public void initCourseMaxCapacityCache(String courseOfferingId, Integer maxCapacity) {
        redisTemplate.opsForHash().put(COURSE_CACHE_KEY+ courseOfferingId,"maxCapacity",maxCapacity);
        redisTemplate.expire(COURSE_CACHE_KEY+ courseOfferingId,1, TimeUnit.HOURS);
    }
}
