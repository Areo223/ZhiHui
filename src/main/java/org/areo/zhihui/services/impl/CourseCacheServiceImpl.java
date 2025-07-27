package org.areo.zhihui.services.impl;

import org.areo.zhihui.services.CourseCacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class CourseCacheServiceImpl implements CourseCacheService {
    private final RedisTemplate<String, Object> redisTemplate;
    private final StringRedisTemplate stringRedisTemplate;

    private static final String COURSE_CACHE_KEY = "course:stock:";
    private static final String COURSE_STUDENTS_KEY = "course:students:";
    private static final String LOCK_KEY = "course:lock:";

    @Autowired
    public CourseCacheServiceImpl(RedisTemplate<String, Object> redisTemplate, StringRedisTemplate stringRedisTemplate) {
        this.redisTemplate = redisTemplate;
        this.stringRedisTemplate = stringRedisTemplate;
    }

    //初始化课程库存缓存
    public void initCourseStockCache(String courseId,Integer capacity){
        redisTemplate.opsForHash().put(COURSE_CACHE_KEY+courseId,"stock",capacity);
    }

    //获取课程缓存
    public Integer getCourseStock(String courseId){
        Object stock = redisTemplate.opsForHash().get(COURSE_CACHE_KEY + courseId, "stock");
        return stock !=null ? Integer.parseInt(stock.toString()):null;
    }

    //减少库存方法
    public boolean reduceCourseStock(String courseId){
        return redisTemplate.opsForHash().increment(COURSE_CACHE_KEY+courseId,"stock",-1L)>0;

    }
    //增加库存方法
    public boolean increaseCourseStock(String courseId){
        return  redisTemplate.opsForHash().increment(COURSE_CACHE_KEY+courseId,"stock",1L)>0;
    }

    //添加学生到课程中
    public Long addStudentToCourse(String courseId,String studentId){
        return redisTemplate.opsForSet().add(COURSE_STUDENTS_KEY+courseId,studentId);
    }

    //从课程中移除学生
    public Long removeStudentFromCourse(String courseId,String studentId){
        return redisTemplate.opsForSet().remove(COURSE_STUDENTS_KEY+courseId,studentId);
    }

    //检查学生是否已选课程
    public boolean isStudentInCourse(String courseId,String studentId){
        return Boolean.TRUE.equals(redisTemplate.opsForSet().isMember(COURSE_STUDENTS_KEY + courseId, studentId));
    }

    //获取课程已选学生数量
    public Long getCourseStudentCount(String courseId){
        return redisTemplate.opsForSet().size(COURSE_STUDENTS_KEY+courseId);
    }

    //获取分布式锁
    public Boolean tryLock(String courseId,long expireSeconds){
        return stringRedisTemplate.opsForValue().setIfAbsent(
                LOCK_KEY+courseId,
                "1",
                expireSeconds,
                TimeUnit.SECONDS
        );
    }

    //释放分布式锁
    public Boolean releaseLock(String courseId){
        return stringRedisTemplate.delete(LOCK_KEY+courseId);
    }
}
