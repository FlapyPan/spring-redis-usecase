package com.example.redissonlock.controller;

import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RAtomicLong;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

@Slf4j
@RestController
public class IndexController {

    private final RedissonClient redisson;

    public IndexController(RedissonClient redisson) {
        this.redisson = redisson;
    }

    @RequestMapping("/count")
    public String count() {
        RAtomicLong atomicLong = redisson.getAtomicLong("count");
        return "count: " + atomicLong.incrementAndGet();
    }

    @RequestMapping("/lock")
    public String lock() {
        RLock rLock = redisson.getLock("lock");

        try {
            // 获取锁，如果获取到了，最长30秒自动释放
            rLock.lock(30L, TimeUnit.SECONDS);
            // 成功获得锁，在这里处理业务
            // 模拟一个 5 秒的业务
            TimeUnit.SECONDS.sleep(5);
            return "业务执行成功";
        } catch (Exception e) {
            return "获取锁失败" + e.getMessage();
        } finally {
            // 最后都要解锁
            rLock.unlock();
        }
    }

}
