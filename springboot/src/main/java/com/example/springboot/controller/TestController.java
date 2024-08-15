package com.example.springboot.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.springboot.common.Result;
import com.example.springboot.entity.Test;
import com.example.springboot.entity.User;
import com.example.springboot.mapper.TestMapper;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/test")
public class TestController {
    @Resource
    TestMapper TestMapper;


    @PostMapping
    public Result<?> save(@RequestBody Test Test){
        TestMapper.insert(Test);
        return Result.success();
    }
    @PutMapping
    public Result<?> update(@RequestBody Test Test){
        TestMapper.updateById(Test);
        return Result.success();
    }
    @DeleteMapping("/{id}")
    public Result<?> update(@PathVariable Long id){
        TestMapper.deleteById(id);
        return Result.success();
    }
    @GetMapping("/{id}")
    public Result<?> getById(@PathVariable Long id){
        return Result.success(TestMapper.selectById(id));
    }
    @GetMapping("/all")
    public Result<?> findAll(){
        return Result.success(TestMapper.selectList(null));
    }
    @GetMapping
    public Result<?> findPage(@RequestParam(defaultValue = "1") Integer pageNum,
                              @RequestParam(defaultValue = "10") Integer pageSize,
                              @RequestParam(defaultValue = "") String search){
        //最后一个参数是输入的关键字
        LambdaQueryWrapper<Test> wrapper=Wrappers.<Test>lambdaQuery();
        if(StrUtil.isNotBlank(search)){
            wrapper.like(Test::getName,search);
        }//避免nickname为空就无法查出
        Page<Test> TestPage=TestMapper.findPage(new Page<>(pageNum,pageSize));
        return Result.success(TestPage);
    }
}