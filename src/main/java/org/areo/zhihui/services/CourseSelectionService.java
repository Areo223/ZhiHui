package org.areo.zhihui.services;

import org.areo.zhihui.pojo.dto.Result;
import org.springframework.stereotype.Service;

@Service
public interface CourseSelectionService {
    Result<Void> selectCourse(String studentIdentifier, String courseOfferingId);
    void asyncSaveSelection(String studentIdentifier, String courseOfferingId);
    Result<Void> withdrawCourse(String studentIdentifier, String courseOfferingId);
    void asyncWithdrawSelection(String studentIdentifier, String courseOfferingId);
}
