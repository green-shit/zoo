package com.example.springboot.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.springboot.common.Result;
import com.example.springboot.entity.Animal;
import com.example.springboot.mapper.AnimalMapper;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/animal")
public class AnimalController {
    @Resource
    AnimalMapper AnimalMapper;

    @PostMapping
    public Result<?> save(@RequestBody Animal Animal){
        AnimalMapper.insert(Animal);
        return Result.success();
    }
    @PutMapping
    public Result<?> update(@RequestBody Animal Animal){
        AnimalMapper.updateById(Animal);
        return Result.success();
    }
    @DeleteMapping("/{id}")
    public Result<?> update(@PathVariable Long id){
        AnimalMapper.deleteById(id);
        return Result.success();
    }
    @GetMapping
    public Result<?> findPage(@RequestParam(defaultValue = "1") Integer pageNum,
                              @RequestParam(defaultValue = "10") Integer pageSize,
                              @RequestParam(defaultValue = "") String search){//默认的三个参数，当前页、每页大小、关键词
        //最后一个参数是输入的关键字
        Page<Animal> AnimalPage=AnimalMapper.selectPage(new Page<>(pageNum,pageSize), Wrappers.<Animal>lambdaQuery().like(Animal::getName,search));
        return Result.success(AnimalPage);
    }
}