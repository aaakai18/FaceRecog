package com.example.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@TableName("sign_in")
@Data
public class SignIn {
    @TableId(type = IdType.AUTO)
    private Integer id;
    @TableField("stu_id")
    private Integer stuId;
    @TableField("course_id")
    private Integer courseId;
    @TableField("attendance_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date attendanceTime;
    @TableField("is_attendance")
    private Integer isAttendance;
    @TableField(exist = false)
    private String stuName;
}
