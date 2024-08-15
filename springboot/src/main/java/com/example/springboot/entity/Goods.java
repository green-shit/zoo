package com.example.springboot.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@TableName("goods")
@Data
public class Goods {
    @TableId(value="id",type= IdType.AUTO)
    private Integer id;
    private String name;
    private BigDecimal price;
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    private Date putawayTime;
    private String picture;
}