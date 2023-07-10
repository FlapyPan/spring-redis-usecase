package com.example.visitcount.dao;

import com.example.visitcount.entity.VisitCount;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface VisitCountMapper {

    @Select("""
            select * from t_visit_count where id = 1 limit 1
            """)
    VisitCount loadVisitCount();

    @Update("""
            update t_visit_count set count = #{count} where id = 1
            """)
    void storeVisitCount(Long count);
}
