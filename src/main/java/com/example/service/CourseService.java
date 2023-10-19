package com.example.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.exception.ServiceException;
import com.example.mapper.CourseMapper;
import com.example.pojo.CourseData;
import com.example.pojo.User;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;
import java.util.List;

@Configuration
public class
CourseService {
    @Resource
    CourseMapper courseMapper;

    //分页查询
    public IPage Page(Integer CurrentPage, Integer PageSize, CourseData course)
    {

        IPage p = new Page(CurrentPage,PageSize);

        LambdaQueryWrapper<CourseData> wr = this.selectByCondition(course);
        p = courseMapper.selectPage(p,wr);
        System.out.println("当前页数据"+p.getRecords());
        return p;
    }

    public LambdaQueryWrapper<CourseData> selectByCondition(CourseData course)
    {
        Integer id = course.getId();
        String name = course.getCrname();
        Integer score = course.getScore();
        Integer time = course.getTime();
        Integer teacherId = course.getTeacherId();
        LambdaQueryWrapper<CourseData> wr = new LambdaQueryWrapper<>();
        wr.eq(id!=null,CourseData::getId,id)
                .eq(name!=null,CourseData::getCrname,name)
                .eq(score!=null,CourseData::getScore,score)
                .eq(time!=null,CourseData::getTime,time)
                .eq(teacherId!=null,CourseData::getTeacherId,teacherId);
        return wr;

    }



    public int insertOne(CourseData courseData)
    {

        int insert = 0;
        try {
            insert = courseMapper.insert(courseData);
        } catch (Exception e) {
            System.out.println("异常信息："+e);
            throw new ServiceException(500,"课程insertOne函数出现错误");
        }
        return insert;
    }

    public int updateOne(CourseData courseData)
    {

        int i = courseMapper.updateById(courseData);
        return i;
    }


    public  IPage<CourseData> test(Integer currentPage,Integer pageSize,String name){
        IPage<CourseData> page = new Page<>(currentPage,pageSize);
        return courseMapper.findPage(page, name);

    }

    public IPage lookStudents(Integer id)
    {
        IPage<CourseData> page = new Page<>();
        return courseMapper.lookStudents(page,id);
    }

}
