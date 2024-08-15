package com.example.springboot.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.List;

@TableName("user")
@Data
public class User {
    @TableId(value="id",type= IdType.AUTO)
    private Integer id;
    private String username;
    private String password;
    private String address;
    private String phone;
    private Integer role;
    @TableField(exist = false)
    private List<Animal> animalList;
}