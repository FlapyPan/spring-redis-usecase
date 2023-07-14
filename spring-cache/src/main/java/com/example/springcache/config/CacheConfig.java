package com.example.springcache.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;

@Configuration
@EnableCaching
public class CacheConfig {

    @Bean(name = "redis-cache")
    public CacheManager cacheManager(RedisConnectionFactory factory) {
        ObjectMapper objectMapper = new ObjectMapper();
        return RedisCacheManager.builder(factory)
                .cacheDefaults(RedisCacheConfiguration
                        .defaultCacheConfig()
                        .disableCachingNullValues()
                        // key 使用 string 序列化
                        .serializeKeysWith(RedisSerializationContext
                                .SerializationPair
                                .fromSerializer(RedisSerializer.string())
                        )
                        // value 使用 json 序列化
                        .serializeValuesWith(RedisSerializationContext
                                .SerializationPair
                                .fromSerializer(new GenericJackson2JsonRedisSerializer(objectMapper))
                        )
                )
                .build();
    }
}
