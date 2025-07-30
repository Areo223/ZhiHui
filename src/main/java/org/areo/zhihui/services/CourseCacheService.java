package org.areo.zhihui.services;


import org.springframework.stereotype.Service;

@Service
public interface CourseCacheService {
    void initCourseStockCache(String courseOfferingId,Integer capacity);
    Integer getCourseStock(String courseOfferingId);
    boolean reduceCourseStock(String courseOfferingId);
    boolean increaseCourseStock(String courseOfferingId);
    Long addStudentToCourse(String courseOfferingId,String studentIdentifier);
    Long removeStudentFromCourse(String teachingClassCode,String courseOfferingId);
    boolean isStudentInCourse(String courseOfferingId,String studentIdentifier);
    Long getCourseStudentCount(String courseOfferingId);
    Boolean tryLock(String courseOfferingId,long expireSeconds);
    Boolean releaseLock(String courseOfferingId);
}
