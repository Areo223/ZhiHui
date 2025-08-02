  package org.areo.zhihui.pojo.request.course;

 import io.swagger.v3.oas.annotations.media.Schema;
 import jakarta.validation.constraints.NotNull;
 import lombok.Data;

  @Data
 public class CourseBaseRequest {
    // 课程id
    @Schema(description = "课程id", example = "1")
    @NotNull
    private Integer id;
  }
