package com.example.springboot.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.springboot.common.Result;
import com.example.springboot.entity.Goods;
import com.example.springboot.mapper.GoodsMapper;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/goods")
public class GoodsController {
    @Resource
    GoodsMapper GoodsMapper;


    @PostMapping
    public Result<?> save(@RequestBody Goods Goods){
        GoodsMapper.insert(Goods);
        return Result.success();
    }
    @PutMapping
    public Result<?> update(@RequestBody Goods Goods){
        GoodsMapper.updateById(Goods);
        return Result.success();
    }
    @DeleteMapping("/{id}")
    public Result<?> update(@PathVariable Long id){
        GoodsMapper.deleteById(id);
        return Result.success();
    }
    @GetMapping
    public Result<?> findPage(@RequestParam(defaultValue = "1") Integer pageNum,
                              @RequestParam(defaultValue = "10") Integer pageSize,
                              @RequestParam(defaultValue = "") String search){
        //最后一个参数是输入的关键字
        Page<Goods> GoodsPage=GoodsMapper.selectPage(new Page<>(pageNum,pageSize), Wrappers.<Goods>lambdaQuery().like(Goods::getName,search));
        return Result.success(GoodsPage);
    }
}
