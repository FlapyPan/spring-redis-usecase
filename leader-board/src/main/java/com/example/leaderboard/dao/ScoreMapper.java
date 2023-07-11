package com.example.leaderboard.dao;

import com.example.leaderboard.entity.Score;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ScoreMapper {

    @Select("select * from t_score")
    List<Score> findALL();

    @Select("select * from t_score order by score desc limit #{limit}")
    List<Score> findByScoreDesc(long limit);

}
