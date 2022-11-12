package com.dg.exception;

import lombok.Data;

/**
 * 自定义异常
 *
 * @author The Fool on 2021/7/12
 * */
@Data
public class BusinessException extends RuntimeException {

    private ErrorCode errorCode;

    private String bizCode;
    private String bizMessage;

    public BusinessException() {}

    public BusinessException(ErrorCode errorCode) {
        super(errorCode.getCode() + ":" + errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public BusinessException(ErrorCode errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public BusinessException(String bizCode, String bizMessage) {
        super(bizCode + ":" + bizMessage);
        this.bizCode = bizCode;
        this.bizMessage = bizMessage;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }

    @Override
    public String toString() {
        if (this.errorCode == null) {
            return String.format("bizCode:%s, bizMessage:%s", this.bizCode, this.bizMessage);
        }
        return String.format("errorCode:%s, msg:%s", this.errorCode.getCode(), this.errorCode.getMessage());
    }
}
