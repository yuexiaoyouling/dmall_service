package com.dg.exception.handler;

import com.dg.entity.VO.Result;
import com.dg.exception.BusinessException;
import com.dg.exception.ExceptionCode;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.HandlerMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 全局处理异常
 *
 * @author The Fool
 * */
@Slf4j
@RestControllerAdvice(basePackages = {
        "com.dg"})
public class GlobalHandler {

    @ExceptionHandler(value = BusinessException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public Result<?> exception(BusinessException exception, HandlerMethod handler, HttpServletRequest request) {
        log.error("system error, AutumnException: {}", this.parseExceptionAsString(exception));
        String code = exception.getBizCode();
        String message = exception.getBizMessage();
        if (exception.getErrorCode() != null) {
            code = exception.getErrorCode().getCode();
            message = exception.getErrorCode().getMessage();
        }
        if (StringUtils.isNoneBlank(exception.getMessage())) {
            message = exception.getMessage();
        }
        return Result.failed(code, message);
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public Result<?> exception(HttpServletResponse response, MethodArgumentNotValidException exception, HttpServletRequest request) {
        log.error("invalid args error,", exception);
        return this.getValid(exception.getBindingResult());
    }

    @ExceptionHandler(value = BindException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public Result<?> exception(HttpServletResponse response, BindException exception, HttpServletRequest request) {
        log.error("invalid args error,", exception);
        return this.getValid(exception.getBindingResult());
    }

    @ExceptionHandler({Error.class, Exception.class, Throwable.class, RuntimeException.class})
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public Result<?> exception(HttpServletResponse response, Throwable exception, HttpServletRequest request) throws IOException {
        log.error("system error, ", exception);
        return Result.failed(ExceptionCode.SYS_ERROR.getCode(), ExceptionCode.SYS_ERROR.getMessage());
    }

    private String parseExceptionAsString(BusinessException ex) {
        StringBuilder sb = new StringBuilder();
        sb.append(ex.getClass().getName());
        sb.append(":");
        sb.append(ex.getMessage());
        sb.append(":");
        sb.append(ex.toString());
        sb.append("\n");
        StackTraceElement[] elements = ex.getStackTrace();
        if (elements != null) {
            for (StackTraceElement each : elements) {
                sb.append("\tat ");
                sb.append(each.toString());
                sb.append("\n");
            }
        }
        return sb.toString();
    }

    private Result<?> getValid(BindingResult bindingResult) {
        Map<String, Object> data = new HashMap<>(bindingResult.getErrorCount());
        bindingResult.getFieldErrors().forEach(each -> data.put(each.getField(), each.getDefaultMessage()));
        return Result.failed(ExceptionCode.SYS_VALID.getCode(), data);
    }
}
