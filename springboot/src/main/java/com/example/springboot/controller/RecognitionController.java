package com.example.springboot.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.springboot.common.Result;
import com.example.springboot.entity.Recognition;
import com.example.springboot.mapper.RecognitionMapper;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/recognition")
public class RecognitionController {
    @Resource
    RecognitionMapper RecognitionMapper;


    @PostMapping
    public Result<?> save(@RequestBody Recognition Recognition){
        RecognitionMapper.insert(Recognition);
        return Result.success();
    }
    @PutMapping
    public Result<?> update(@RequestBody Recognition Recognition){
        RecognitionMapper.updateById(Recognition);
        return Result.success();
    }
    @DeleteMapping("/{id}")
    public Result<?> update(@PathVariable Long id){
        RecognitionMapper.deleteById(id);
        return Result.success();
    }
    @GetMapping
    public Result<?> findPage(@RequestParam(defaultValue = "1") Integer pageNum,
                              @RequestParam(defaultValue = "10") Integer pageSize,
                              @RequestParam(defaultValue = "") String search){
        Page<Recognition> RecognitionPage=RecognitionMapper.selectPage(new Page<>(pageNum,pageSize), Wrappers.<Recognition>lambdaQuery().like(Recognition::getPicture,search));
        return Result.success(RecognitionPage);
    }
}
