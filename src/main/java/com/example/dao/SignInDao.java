package com.example.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.pojo.SignIn;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

@Mapper
public interface SignInDao extends BaseMapper<SignIn> {
     List<SignIn> selectByCourseId(@Param("course_id")Integer courseId, @Param("attendance_time") String attendanceTime);
}
