package com.example.springboot.entity;
import lombok.Data;
@Data
public class LiPay {
    private String subject;
    private String traceNo;//订单编号
    private String totalAmount;
}
