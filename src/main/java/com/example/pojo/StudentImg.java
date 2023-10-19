package com.example.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("student")
public class StudentImg {

    @TableId(type = IdType.AUTO)
    private Integer id;

    @TableField(value = "stu_id")
    private Integer stuId;

    @TableField(value = "img_data")
    private String imgData;
}
