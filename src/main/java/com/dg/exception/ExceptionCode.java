package com.dg.exception;


import com.alibaba.druid.util.StringUtils;
import java.util.Arrays;

/**
 * 全局异常枚举
 *
 * @author The Fool on 2021/7/12
 * */
public enum ExceptionCode implements ErrorCode {
    SUCCESS("000000", "正常"),
    SYS_ERROR("100001", "系统异常"),
    SYS_VALID("100002", "验证不通过"),
    SYS_NOT_LOGIN("100003", "访问需要登录"),
    SYS_UNAUTHORIZED("100004","认证失败"),
    PARAM_ERROR("100005","参数异常"),
    ;

    private String code;

    private String message;

    ExceptionCode(String code, String message){
        this.code = code;
        this.message = message;
    }

    @Override
    public String getCode() { return code; }

    @Override
    public String getMessage() { return message; }

    public static ExceptionCode getAutumnCodeByCode(String code){
        return Arrays.stream(values())
            .filter(each -> StringUtils.equals(each.getCode(), code))
            .findFirst()
            .orElse(null);
    }
}
