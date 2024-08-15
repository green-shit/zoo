package com.example.springboot.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.springboot.common.Result;
import com.example.springboot.entity.Question;
import com.example.springboot.mapper.QuestionMapper;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;

@RestController
@RequestMapping("/question")
public class QuestionController {
    @Resource
    QuestionMapper QuestionMapper;


    @PostMapping
    public Result<?> save(@RequestBody Question Question){
        QuestionMapper.insert(Question);
        return Result.success();
    }
    @PutMapping
    public Result<?> update(@RequestBody Question Question){
        QuestionMapper.updateById(Question);
        return Result.success();
    }
    @DeleteMapping("/{id}")
    public Result<?> update(@PathVariable Long id){
        QuestionMapper.deleteById(id);
        return Result.success();
    }
    @GetMapping
    public Result<?> findPage(@RequestParam(defaultValue = "1") Integer pageNum,
                              @RequestParam(defaultValue = "10") Integer pageSize,
                              @RequestParam(defaultValue = "") String search){
        //最后一个参数是输入的关键字
        Page<Question> QuestionPage=QuestionMapper.selectPage(new Page<>(pageNum,pageSize), Wrappers.<Question>lambdaQuery().like(Question::getContent,search));
        return Result.success(QuestionPage);
    }
}