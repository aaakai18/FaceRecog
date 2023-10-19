package com.example.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.List;

@Data
@TableName("teacher")
public class Teacher {

    @TableId(type = IdType.AUTO)
    private Integer id;

    private String name;

    @TableField(value = "is_admin")
    private Integer Admin;
    private Integer status;
    private String phone;
    private String password;

    @TableField(exist = false)
    List<CourseData> courses;

    @TableField(exist = false)
    private String token;
}
