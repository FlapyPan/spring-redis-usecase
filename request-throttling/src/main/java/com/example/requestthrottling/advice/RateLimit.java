package com.example.requestthrottling.advice;

import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.concurrent.TimeUnit;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface RateLimit {

    /**
     * 单位时间内请求数量
     */
    @AliasFor("limit")
    int value() default 10;

    /**
     * 单位时间内请求数量
     */
    @AliasFor("value")
    int limit() default 10;

    /**
     * 限流超时时间
     */
    int timeout() default 3;

    /**
     * 时间单位
     */
    TimeUnit timeUnit() default TimeUnit.SECONDS;

}
