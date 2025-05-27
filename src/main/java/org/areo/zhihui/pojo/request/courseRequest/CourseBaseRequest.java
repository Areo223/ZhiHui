  package org.areo.zhihui.pojo.request.courseRequest;

 import io.swagger.v3.oas.annotations.media.Schema;
 import lombok.Data;
 import org.springframework.lang.Nullable;

  @Data
 public class CourseBaseRequest {
    // 课程id
    @Schema(description = "课程id", example = "1")
    @Nullable
    private Integer id;
  }
