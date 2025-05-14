package org.areo.zhihui.pojo.dto;


import lombok.Getter;
import org.areo.zhihui.annotation.ErrorCode;
import org.areo.zhihui.pojo.Restful;

import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;

@Getter
public final class Result<T> {
    private final T value;
    private final Throwable error;
    private final int errorCode; // 自定义错误码（可选）

    private Result(T value, Throwable error, int errorCode) {
        this.value = value;
        this.error = error;
        this.errorCode = errorCode;
    }

    // 成功工厂方法
    public static <T> Result<T> success(T value) {
        return new Result<>(value, null, 0);
    }

    // 失败工厂方法（支持自定义错误码）
    public static <T> Result<T> failure(Throwable error, int errorCode) {
        return new Result<>(null, error, errorCode);
    }

    public static <T> Result<T> failure(Throwable error) {
        int code = Optional.ofNullable(error.getClass().getAnnotation(ErrorCode.class))
                .map(ErrorCode::value)
                .orElse(Restful.BAD_REQUEST_CODE); // 默认 400
        return new Result<>(null, error, code);
    }

    // 链式处理：成功时消费值，失败时不操作
    public Result<T> ifSuccess(Consumer<T> action) {
        if (isSuccess()) action.accept(value);
        return this;
    }

    // 链式处理：失败时消费错误
    public Result<T> ifFailure(BiConsumer<Throwable, Integer> action) {
        if (!isSuccess()) action.accept(error, errorCode);
        return this;
    }

    // 转换为 Restful.ResultJson
    public Restful.ResultJson toJson() {
        return isSuccess()
                ? Restful.success(value)
                : Restful.error(errorCode, error.getMessage(), null);
    }

    // 快速检查状态
    public boolean isSuccess() {
        return error == null;
    }

    /**
     * 转换成功的值，失败时保持不变
     *
     * @param mapper 转换函数
     * @return 新的 Result
     */
    public <R> Result<R> map(Function<? super T, ? extends R> mapper) {
        if (isSuccess()) {
            return Result.success(mapper.apply(value));
        } else {
            return Result.failure(error, errorCode);
        }
    }

    /**
     * 链式转换：成功时通过函数返回新Result，失败时保持错误
     *
     * @param mapper 转换函数（必须返回Result类型）
     */
    public <R> Result<R> flatMap(Function<? super T, Result<R>> mapper) {
        return isSuccess()
                ? mapper.apply(value)
                : Result.failure(error, errorCode);
    }
}