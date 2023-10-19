package com.example.exception;

import lombok.Getter;

/**
 * 自定义服务异常类
 */
@Getter
public class ServiceException extends RuntimeException{
    private Integer code;

    public ServiceException(Integer code, String msg) {
        super(msg);
        this.code = code;
    }
}
