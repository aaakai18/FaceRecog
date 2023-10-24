package com.example.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@TableName("sign_in_statistics")
@Data
public class SignInStatistics {
    @TableId(type = IdType.AUTO)
    private Integer id;
    @TableField(value = "stu_id")
    private Integer stuId;
    @TableField(value = "attendance")
    private Integer attendance;
    @TableField(value = "absenteeism")
    private Integer absenteeism;
    @TableField(value = "course_id")
    private Integer courseId;

    @TableField(exist = false)
    private String stuName;
    @TableField(exist = false)
    private String courseName;
}
