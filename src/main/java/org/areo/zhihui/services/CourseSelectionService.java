package org.areo.zhihui.services;

import org.areo.zhihui.pojo.dto.Result;
import org.springframework.stereotype.Service;

@Service
public interface CourseSelectionService {
    Result<Void> selectCourse(String studentIdentifier, String teachingClassCode);
    void asyncSaveSelection(String studentIdentifier, String teachingClassCode);
    Result<Void> withdrawCourse(String studentIdentifier, String teachingClassCode);
    void asyncWithdrawSelection(String studentIdentifier, String teachingClassCode);
}
