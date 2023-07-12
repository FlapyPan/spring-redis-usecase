package com.example.requestthrottling.advice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理类
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    /**
     * 处理其他异常
     *
     * @param e 未捕获的异常
     * @return 统一返回结构
     */
    @ExceptionHandler
    @ResponseStatus(HttpStatus.OK) // 统一http返回状态为200
    public String handlerException(Exception e) {
        log.error("服务器内部错误：", e);
        return e.getMessage();
    }
}
