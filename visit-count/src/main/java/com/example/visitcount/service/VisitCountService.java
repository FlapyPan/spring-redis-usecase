package com.example.visitcount.service;

import com.example.visitcount.dao.VisitCountMapper;
import com.example.visitcount.entity.VisitCount;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class VisitCountService {
    private final VisitCountMapper visitCountMapper;
    private final RedisTemplate<String, String> redisTemplate;

    private static final String REDIS_VISIT_COUNT_KEY = "visit_count";

    public VisitCountService(VisitCountMapper visitCountMapper,
                             RedisTemplate<String, String> redisTemplate) {
        this.visitCountMapper = visitCountMapper;
        this.redisTemplate = redisTemplate;
    }

    public Long count() {
        String s = redisTemplate.opsForValue().get(REDIS_VISIT_COUNT_KEY);
        return s == null ? 0L : Long.parseLong(s);
    }

    public void incr() {
        redisTemplate.opsForValue().increment(REDIS_VISIT_COUNT_KEY);
    }

    @PostConstruct
    public void load() {
        VisitCount visitCount = visitCountMapper.loadVisitCount();
        Long count = visitCount.getCount();
        log.info("从数据库读取: {}", count);
        redisTemplate.opsForValue().set(REDIS_VISIT_COUNT_KEY, String.valueOf(count));
    }

    @Scheduled(initialDelay = 10000, fixedDelay = 10000)
    public void store() {
        Long count = count();
        log.info("存入数据库：{}", count);
        visitCountMapper.storeVisitCount(count);
    }

}
