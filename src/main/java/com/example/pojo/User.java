package com.example.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.List;


@Data
@TableName(value = "zd_users")
public class User {
    @TableId(type = IdType.AUTO)
    private Integer id;

    @TableField(value = "user_name")
    private String username;


    private String password;



    @TableField(value = "is_admin")
    private Integer admin;

    @TableField(exist = false)
    private String token;

    @TableField(exist = false)
    List<CourseData> courses;






}
