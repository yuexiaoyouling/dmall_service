package com.dg.entity.VO;

import com.dg.exception.ExceptionCode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 结果包装类
 *
 * @author The Fool
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Result<T> {

    private String code;

    private Object message;

    private T data;

    public Result(ExceptionCode autumnCode) {
        this.code = autumnCode.getCode();
        this.message = autumnCode.getMessage();
        this.data = null;
    }

    public static <T> Result<T> success(T data) {
        return new Result<T>(ExceptionCode.SUCCESS.getCode(), ExceptionCode.SUCCESS.getMessage(), data);
    }

    public static <T> Result<T> success() {
        return new Result<T>(ExceptionCode.SUCCESS.getCode(), ExceptionCode.SUCCESS.getMessage(), null);
    }

    public static <T> Result<T> failed(Object message) {
        return new Result<T>(ExceptionCode.SYS_ERROR.getCode(), message, null);
    }

    public static <T> Result<T> failed() {
        return new Result<T>(ExceptionCode.SYS_ERROR.getCode(), ExceptionCode.SYS_ERROR.getMessage(), null);
    }

    public static <T> Result<T> failed(String code, Object message) {
        return new Result<T>(code, message, null);
    }

    public static <T> Result<T> failed(String code, Object message, T data) {
        return new Result<T>(code, message, data);
    }

    public static <T> Result<T> unauthorized() {
        return new Result<>(ExceptionCode.SYS_UNAUTHORIZED);
    }
}
