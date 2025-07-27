package org.areo.zhihui.services;

import org.areo.zhihui.pojo.dto.Result;
import org.springframework.stereotype.Service;

@Service
public interface CourseSelectionService {
    Result<Void> selectCourse(String studentIdentifier, String teachingClassCode);
    void asynSaveSelection(String studentIdentifier, String teachingClassCode);
}
