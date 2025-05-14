  package org.areo.zhihui.pojo.request;

 import jakarta.validation.constraints.NotNull;
 import lombok.Data;

 @Data
 public class BaseCourseRequest {
    // 课程id
    @NotNull(message = "课程id不能为空")
    private Integer id;
  }
