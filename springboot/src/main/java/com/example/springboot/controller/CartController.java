package com.example.springboot.controller;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.example.springboot.mapper.GoodsMapper;
import com.example.springboot.mapper.OrdersMapper;
import com.example.springboot.entity.Goods;
import com.example.springboot.entity.Orders;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
@RequestMapping("/cart")
public class CartController {
    @Resource
    private GoodsMapper goodsMapper;

    @Resource
    private OrdersMapper ordersMapper;

    @Transactional
    @PostMapping("/buy")
    public boolean buy(@RequestParam Integer goodsId) {
        Goods goods = goodsMapper.selectById(goodsId);
        Date date = new Date();
        Orders orders = new Orders();
        orders.setGoodsId(goodsId);
        orders.setCreateTime(date);
        orders.setName("购买" + goods.getName() + "订单");
        orders.setOrderId(new SimpleDateFormat("yyyyMMdd").format(date) + System.currentTimeMillis());
        orders.setTotal(goods.getPrice().multiply(BigDecimal.ONE));

        return ordersMapper.insert(orders) > 0 &&  goodsMapper.updateById(goods) > 0;
    }
}
