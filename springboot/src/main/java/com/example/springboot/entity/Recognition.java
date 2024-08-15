package com.example.springboot.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;


@TableName("recognition")
@Data
public class Recognition {
    @TableId(value="id",type= IdType.AUTO)
    private Integer id;
    private String picture;
}