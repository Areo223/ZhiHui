  package org.areo.zhihui.pojo.request.course;

 import io.swagger.v3.oas.annotations.media.Schema;
 import jakarta.validation.constraints.NotNull;
 import lombok.Data;
 import org.aspectj.lang.annotation.DeclareAnnotation;
 import org.springframework.lang.Nullable;

  @Data
 public class CourseBaseRequest {
    // 课程id
    @Schema(description = "课程id", example = "1")
    @NotNull
    private Integer id;
  }
