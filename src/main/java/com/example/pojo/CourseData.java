package com.example.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.List;

@Data
@TableName("zdsc_course")
public class CourseData {
    @TableId(type = IdType.AUTO)
    private Integer id;

    @TableField(value = "crname")
    private String crname;

    private Integer score;

    private Integer time;


    @TableField(value = "teacher_id")
    private Integer teacherId;

    @TableField(exist = false)
    private String teacher;

    @TableField(exist = false)
    private List<User> students;



}
