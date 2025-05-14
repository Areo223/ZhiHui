package org.areo.zhihui.pojo;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

public class Restful {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Getter
    public static class ResultJson{
        @Schema(description = "状态码", example = "200")
        private final int code;
        @Schema(description = "提示信息", example = "操作成功")
        private final String msg;
        @Schema(description = "数据", example = "{}")
        private final Object data;

        private ResultJson(int code, String msg, Object data) {
            this.code = code;
            this.msg = msg;
            this.data = data;
        }

        public String toJsonString() {
            try {
                return objectMapper.writeValueAsString(this);
            }catch (Exception e) {
                return "{\"code\": 500, \"msg\": \"内部错误\", \"data\": null}";
            }
        }

    }

    // 状态码常量
    public static final int SUCCESS_CODE = 200;
    public static final int BAD_REQUEST_CODE = 400;
    public static final int UNAUTHORIZED_CODE = 401;
    public static final int INTERNAL_ERROR_CODE = 500;

    // 成功响应（核心方法）
    public static ResultJson success(String msg, Object data) {
        return new ResultJson(SUCCESS_CODE, msg, data);
    }

    public static ResultJson success(Object data) {
        return success("操作成功", data);
    }

    public static ResultJson success(String msg) {
        return success(msg, null);
    }

    public static ResultJson success() {
        return success("操作成功", null);
    }

    public static ResultJson error(int code, String msg, Object data) {
        return new ResultJson(code, msg, data);
    }

    public static ResultJson badRequest(String msg) {
        return error(BAD_REQUEST_CODE, msg, null);
    }

    public static ResultJson unauthorized(String msg) {
        return error(UNAUTHORIZED_CODE, msg, null);
    }

    public static ResultJson serverError(String msg) {
        return error(INTERNAL_ERROR_CODE, msg, null);
    }

    public static ResultJson loginSuccess(Object data) {
        return success("登录成功", data);
    }

}
