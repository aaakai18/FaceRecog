package com.example.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.pojo.StudentImg;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface StuDao extends BaseMapper<StudentImg> {


}
