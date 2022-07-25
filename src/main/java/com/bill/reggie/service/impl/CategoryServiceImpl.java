package com.bill.reggie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bill.reggie.common.CustomException;
import com.bill.reggie.entity.Category;
import com.bill.reggie.entity.Dish;
import com.bill.reggie.entity.Setmeal;
import com.bill.reggie.mapper.CategoryMapper;
import com.bill.reggie.service.CategoryService;
import com.bill.reggie.service.DishService;
import com.bill.reggie.service.SetmealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper,Category> implements CategoryService {

    @Autowired
    private DishService dishService;

    @Autowired
    private SetmealService setmealService;
    /**
     * 根据id删除
     * @param id
     */
    @Override
    public void remove(Long id) {
        //查看是否关联  已经关联的话，抛出一个业务异常
        //查菜品表
        LambdaQueryWrapper<Dish> dishLambdaQueryWrapper = new LambdaQueryWrapper<>();
        dishLambdaQueryWrapper.eq(Dish::getCategoryId, id);
        int count = dishService.count(dishLambdaQueryWrapper);
        if (count > 0) {
            //抛出一个业务异常
            throw new CustomException("分类下关联了菜品，不能删除");
        }
        LambdaQueryWrapper<Setmeal> setmealLambdaQueryWrapper = new LambdaQueryWrapper<>();
        setmealLambdaQueryWrapper.eq(Setmeal::getCategoryId, id);
        int count1 = setmealService.count(setmealLambdaQueryWrapper);
        if (count1 > 0) {
            //抛出一个业务异常
            throw new CustomException("分类下关联了菜品，不能删除");
        }

        //正常删除
        super.removeById(id);
    }
}
