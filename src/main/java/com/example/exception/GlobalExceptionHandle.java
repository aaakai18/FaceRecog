package com.example.exception;

import com.example.Result;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 全局的异常处理
 */
//这是一个 Spring Framework 的注解，用于定义全局的异常处理器。它告诉 Spring 在整个应用程序中使用这个类来处理异常。
@ControllerAdvice
public class GlobalExceptionHandle {

    //这个注解标记了 handle 方法，表示这个方法用于处理 ServiceException 类型的异常。
    @ExceptionHandler(ServiceException.class)
    @ResponseBody//这个注解告诉 Spring MVC 将方法的返回值直接作为 HTTP 响应的内容在这里，它确保方法返回的是 JSON 格式的数据。
    public Result handle(ServiceException se){
        return new Result(se.getCode(),null,se.getMessage());
    }
}
