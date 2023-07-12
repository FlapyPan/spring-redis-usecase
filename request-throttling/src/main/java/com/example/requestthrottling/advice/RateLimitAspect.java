package com.example.requestthrottling.advice;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.concurrent.TimeUnit;

@Slf4j
@Component
@Aspect
public class RateLimitAspect {

    private final StringRedisTemplate stringRedisTemplate;
    private final HttpServletRequest httpServletRequest;

    public RateLimitAspect(StringRedisTemplate stringRedisTemplate, HttpServletRequest httpServletRequest) {
        this.stringRedisTemplate = stringRedisTemplate;
        this.httpServletRequest = httpServletRequest;
    }

    @Around("@annotation(rateLimit)")
    public Object rateLimit(ProceedingJoinPoint pjp, RateLimit rateLimit) throws Throwable {
        // 获取方法名
        String methodName = pjp.getSignature().toShortString();
        // 获取 ip
        String ip = getClientIP(httpServletRequest);
        // 组装 key
        String key = "rate_limit:" + methodName + ":" + ip;
        log.info(key);
        int limit = rateLimit.limit();
        int timeout = rateLimit.timeout();
        TimeUnit timeUnit = rateLimit.timeUnit();
        // 请求次数++
        Long count = stringRedisTemplate.opsForValue().increment(key);
        if (count == 1) {
            // 请求数为 1 添加过期时间
            stringRedisTemplate.expire(key, timeout, timeUnit);
        }
        if (count > limit) {
            // 超出限制禁止访问
            throw new RuntimeException("请求频率过高");
        }
        return pjp.proceed();
    }

    private String getClientIP(HttpServletRequest request) {
        String header = request.getHeader("X-Forwarded-For");
        if (StringUtils.hasText(header)) {
            return header.split(",")[0].trim();
        }
        return header;
    }

}
