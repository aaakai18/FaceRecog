package com.example.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.pojo.Teacher;
import com.example.pojo.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TeaDao extends BaseMapper<Teacher> {
    IPage<Teacher> findPage(IPage<Teacher>page, Integer id);
}
