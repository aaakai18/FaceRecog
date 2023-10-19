package com.example.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.pojo.CourseData;
import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface CourseMapper extends BaseMapper<CourseData> {
    IPage<CourseData> findPage(IPage<CourseData> page,String name);

    IPage<CourseData> lookStudents(IPage<CourseData> page,Integer id);

}
