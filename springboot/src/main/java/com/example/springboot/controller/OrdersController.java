package com.example.springboot.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.springboot.common.Result;
import com.example.springboot.entity.Orders;
import com.example.springboot.mapper.OrdersMapper;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
@RestController
@RequestMapping("/orders")

public class OrdersController {
    @Resource
    OrdersMapper OrdersMapper;

    @PostMapping
    public Result<?> save(@RequestBody Orders Orders){
        OrdersMapper.insert(Orders);
        return Result.success();
    }
    @PutMapping
    public Result<?> update(@RequestBody Orders Orders){
        OrdersMapper.updateById(Orders);
        return Result.success();
    }
    @DeleteMapping("/{id}")
    public Result<?> update(@PathVariable Long id){
        OrdersMapper.deleteById(id);
        return Result.success();
    }
    @GetMapping
    public Result<?> findPage(@RequestParam(defaultValue = "1") Integer pageNum,
                              @RequestParam(defaultValue = "10") Integer pageSize,
                              @RequestParam(defaultValue = "") String search){
        //最后一个参数是输入的关键字
        Page<Orders> OrdersPage=OrdersMapper.selectPage(new Page<>(pageNum,pageSize), Wrappers.<Orders>lambdaQuery().like(Orders::getName,search));
        return Result.success(OrdersPage);
    }
}
