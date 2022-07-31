package com.bill.reggie.controller;


import com.bill.reggie.common.R;
import com.bill.reggie.dto.OrdersDto;
import com.bill.reggie.entity.Orders;
import com.bill.reggie.service.OrderDetailService;
import com.bill.reggie.service.OrdersService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// todo 添加订单相关操作
@Slf4j
@RestController
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private OrdersService ordersService;

    @Autowired
    private OrderDetailService orderDetailService;

    @PostMapping("/submit")
    public R<String> submit(@RequestBody Orders orders) {
        ordersService.submit(orders);
        return R.success("success");
    }
}
