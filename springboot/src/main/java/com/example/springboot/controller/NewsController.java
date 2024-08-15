package com.example.springboot.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.springboot.common.Result;
import com.example.springboot.entity.News;
import com.example.springboot.mapper.NewsMapper;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;

@RestController
@RequestMapping("/news")
public class NewsController {
    @Resource
    NewsMapper NewsMapper;


    @PostMapping
    public Result<?> save(@RequestBody News News){
        News.setTime(new Date());
        NewsMapper.insert(News);
        return Result.success();
    }
    @PutMapping
    public Result<?> update(@RequestBody News News){
        NewsMapper.updateById(News);
        return Result.success();
    }
    @DeleteMapping("/{id}")
    public Result<?> update(@PathVariable Long id){
        NewsMapper.deleteById(id);
        return Result.success();
    }
    @GetMapping
    public Result<?> findPage(@RequestParam(defaultValue = "1") Integer pageNum,
                              @RequestParam(defaultValue = "10") Integer pageSize,
                              @RequestParam(defaultValue = "") String search){
        //最后一个参数是输入的关键字
        Page<News> NewsPage=NewsMapper.selectPage(new Page<>(pageNum,pageSize), Wrappers.<News>lambdaQuery().like(News::getTitle,search));
        return Result.success(NewsPage);
    }
}