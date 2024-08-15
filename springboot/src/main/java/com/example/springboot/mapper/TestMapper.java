package com.example.springboot.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.springboot.entity.Test;

public interface TestMapper extends BaseMapper<Test> {
    Page<Test> findPage(Page<Test> page);
}