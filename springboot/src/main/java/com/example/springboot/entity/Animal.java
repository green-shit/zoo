package com.example.springboot.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.List;

@TableName("animal")
@Data
public class Animal {
    @TableId(value="id",type= IdType.AUTO)
    private Integer id;
    private String name;
    private String species;
    private String sex;
    private Integer age;
    private Integer keeperId;

    @TableField(exist = false)
    private String managername;
}
