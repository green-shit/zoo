package com.example.springboot.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@TableName("question")
@Data
public class Question {
    @TableId(value="id",type= IdType.AUTO)
    private Integer id;
    private String content;
    private String a;
    private String b;
    private String c;
    private String d;
    private String answer;
    private Integer quizId;

    @TableField(exist = false)
    private String quizname;
}
