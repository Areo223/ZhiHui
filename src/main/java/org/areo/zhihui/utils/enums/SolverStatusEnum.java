package org.areo.zhihui.utils.enums;

import com.baomidou.mybatisplus.annotation.IEnum;
import com.fasterxml.jackson.annotation.JsonValue;

public enum SolverStatusEnum implements IEnum<Integer> {
    SOLVE_FAILED(-1,"求解失败"),
    NOT_SOLVING(0,"未求解"),
    SOLVING_ACTIVE(1,"求解中"),
    SOLVING_COMPLETED(2,"求解完成"),
    TERMINATED_EARLY(4,"已终止");

    private final Integer code;
    @JsonValue
    private final String message;
    SolverStatusEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }
    @Override
    public Integer getValue() {
        return code;
    }
}
