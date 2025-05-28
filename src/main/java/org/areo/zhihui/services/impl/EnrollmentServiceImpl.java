package org.areo.zhihui.services.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.areo.zhihui.exception.CommonException;
import org.areo.zhihui.mapper.EnrollmentMapper;
import org.areo.zhihui.pojo.dto.Result;
import org.areo.zhihui.pojo.entity.Enrollment;
import org.areo.zhihui.services.EnrollmentService;
import org.areo.zhihui.utils.enums.SemesterEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class EnrollmentServiceImpl implements EnrollmentService {

    private final EnrollmentMapper enrollmentMapper;

    @Override
    public Result<Void> selectCourse(Enrollment enrollment) {
        log.debug("selectCourse:{}", enrollment);
        // 检查学生是否已经选择了该课程
        if (enrollmentMapper.checkIfStudentHasSelectedCourse(enrollment.getStudentId(), enrollment.getCourseId())) {
            // 如果存在选课记录，则返回报错信息
            log.error("学生已选择该课程，标识: {}",enrollment.getStudentId());
            return Result.failure(new CommonException("学生已选择该课程"));
        }

        try {
            // 根据当前时间填充enrollment对象的学期枚举字段
            enrollment.setSemester(SemesterEnum.semesterSet());
            int affectedRows = enrollmentMapper.insert(enrollment);
            if (affectedRows == 0) {
                log.error("数据库写入失败，标识: {}",enrollment.getStudentId());
                return Result.failure(new CommonException("选课失败,数据库写入异常"));
            }
            log.info("选课成功，标识: {}",enrollment.getStudentId());
            return Result.success(null);
        }catch (Exception e){
            log.error("写入异常，标识: {} 错误:{}",enrollment.getStudentId(),e.getMessage());
            return Result.failure(e);
        }
    }
}
