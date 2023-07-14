package com.example.springcache.service;

import com.example.springcache.entity.Info;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

@Slf4j
@Service
@CacheConfig(cacheManager = "redis-cache", cacheNames = "test")
public class TestService {

    @Cacheable(key = "'infos'")
    public List<Info> listInfos() {
        Random random = ThreadLocalRandom.current();
        log.info("生成新数据");
        return List.of(
                new Info(random.nextInt(1, 100), "title" + random.nextInt(), "content" + random.nextInt()),
                new Info(random.nextInt(1, 100), "title" + random.nextInt(), "content" + random.nextInt()),
                new Info(random.nextInt(1, 100), "title" + random.nextInt(), "content" + random.nextInt()),
                new Info(random.nextInt(1, 100), "title" + random.nextInt(), "content" + random.nextInt()),
                new Info(random.nextInt(1, 100), "title" + random.nextInt(), "content" + random.nextInt())
        );
    }

    @CacheEvict(key = "'infos'")
    public void delCache() {
        log.info("缓存已删除");
    }
}
