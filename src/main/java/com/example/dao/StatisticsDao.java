package com.example.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.pojo.SignInStatistics;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface StatisticsDao extends BaseMapper<SignInStatistics> {
    List<SignInStatistics> selectByCourseId(@Param("course_id") Integer id);
}
