package com.example.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.pojo.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserDao extends BaseMapper<User> {
  IPage<User> findPage(IPage<User>page,Integer id);

  IPage<User> selectCourse(IPage<User>page,Integer id);
}
