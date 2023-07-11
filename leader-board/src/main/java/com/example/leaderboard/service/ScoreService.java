package com.example.leaderboard.service;

import com.example.leaderboard.dao.ScoreMapper;
import com.example.leaderboard.entity.Score;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.DefaultTypedTuple;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@Service
public class ScoreService {
    private static final String LEADER_BOARD_REDIS_KEY = "leader_board";
    private final ScoreMapper scoreMapper;
    private final StringRedisTemplate stringRedisTemplate;

    public ScoreService(ScoreMapper scoreMapper, StringRedisTemplate stringRedisTemplate) {
        this.scoreMapper = scoreMapper;
        this.stringRedisTemplate = stringRedisTemplate;
    }

    public List<Score> leaderBoard() throws JsonProcessingException {
        if (Boolean.FALSE.equals(stringRedisTemplate.hasKey(LEADER_BOARD_REDIS_KEY))) {
            // 如果 redis 中不存在对应的 key，从数据库加载
            loadFromDB();
        }
        return getOrderedScore();
    }

    @PostConstruct
    public void loadFromDB() throws JsonProcessingException {
        // 从数据库获取所有数据
        List<Score> all = scoreMapper.findALL();
        Set<ZSetOperations.TypedTuple<String>> tuples = new HashSet<>(all.size());
        ObjectMapper objectMapper = new ObjectMapper();
        // 循环生成值和分数对应的 tuple
        for (Score score : all) {
            DefaultTypedTuple<String> tuple = new DefaultTypedTuple<>(
                    objectMapper.writeValueAsString(score),
                    (double) -score.getScore());
            tuples.add(tuple);
        }
        // 批量插入 tuple 到 redis 的 zset 进行排序
        stringRedisTemplate.opsForZSet().add(LEADER_BOARD_REDIS_KEY, tuples);
    }

    private List<Score> getOrderedScore() throws JsonProcessingException {
        // 从 redis 读取排行
        var rankedTuples = stringRedisTemplate.opsForZSet()
                .rangeWithScores(LEADER_BOARD_REDIS_KEY, 0, 100);
        if (rankedTuples == null) return Collections.emptyList();
        List<Score> scores = new ArrayList<>(rankedTuples.size());
        ObjectMapper objectMapper = new ObjectMapper();
        for (var tuple : rankedTuples) {
            scores.add(objectMapper.readValue(tuple.getValue(), Score.class));
        }
        return scores;
    }
}
