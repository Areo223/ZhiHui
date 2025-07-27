package org.areo.zhihui.services;


import org.springframework.stereotype.Service;

@Service
public interface CourseCacheService {
    void initCourseStockCache(String courseId,Integer capacity);
    Integer getCourseStock(String courseId);
    boolean reduceCourseStock(String courseId);
    boolean increaseCourseStock(String courseId);
    Long addStudentToCourse(String courseId,String studentId);
    Long removeStudentFromCourse(String courseId,String studentId);
    boolean isStudentInCourse(String courseId,String studentId);
    Long getCourseStudentCount(String courseId);
    Boolean tryLock(String courseId,long expireSeconds);
    Boolean releaseLock(String courseId);
}
