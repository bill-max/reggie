package com.bill.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.bill.reggie.entity.Orders;


public interface OrdersService extends IService<Orders> {
    /**
     * 用户下单
     * @param orders
     */
    void submit(Orders orders);
}
