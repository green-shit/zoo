package com.example.springboot.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.springboot.entity.User;
public interface UserMapper extends BaseMapper<User> {
    //一对多查询
    Page<User> findPage(Page<User> page);
}