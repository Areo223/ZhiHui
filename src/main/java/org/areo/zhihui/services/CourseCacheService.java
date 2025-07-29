package org.areo.zhihui.services;


import org.springframework.stereotype.Service;

@Service
public interface CourseCacheService {
    void initCourseStockCache(String teachingClassCode,Integer capacity);
    Integer getCourseStock(String teachingClassCodeeId);
    boolean reduceCourseStock(String teachingClassCode);
    boolean increaseCourseStock(String teachingClassCode);
    Long addStudentToCourse(String teachingClassCode,String studentIdentifier);
    Long removeStudentFromCourse(String teachingClassCode,String studentIdentifier);
    boolean isStudentInCourse(String teachingClassCode,String studentIdentifier);
    Long getCourseStudentCount(String teachingClassCode);
    Boolean tryLock(String teachingClassCode,long expireSeconds);
    Boolean releaseLock(String teachingClassCode);
}
