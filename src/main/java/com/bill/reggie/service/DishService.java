package com.bill.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.bill.reggie.dto.DishDto;
import com.bill.reggie.entity.Dish;

public interface DishService extends IService<Dish> {
    //新增菜品，同时插入口味数据
    public void saveWithFlavor(DishDto dishDto);

    //查菜品
    public DishDto getByIdWithFlavor(Long id);

    //改
    void updateWithFlavor(DishDto dishDto);
}
