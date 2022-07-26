package com.bill.reggie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bill.reggie.common.CustomException;
import com.bill.reggie.dto.SetmealDto;
import com.bill.reggie.entity.Setmeal;
import com.bill.reggie.entity.SetmealDish;
import com.bill.reggie.mapper.SetmealMapper;
import com.bill.reggie.service.SetmealDishService;
import com.bill.reggie.service.SetmealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SetmealServiceImpl extends ServiceImpl<SetmealMapper, Setmeal> implements SetmealService {

    @Autowired
    private SetmealDishService setmealDishService;


    /**
     *  保存套餐信息
     * @param setmealDto 套餐
     */
    @Override
    @Transactional
    public void saveWithDish(SetmealDto setmealDto) {
        this.save(setmealDto);
        List<SetmealDish> setmealDishes = setmealDto.getSetmealDishes();
        setmealDishes.stream().map((item)->{
            item.setSetmealId(setmealDto.getId());
            return item;
        }).collect(Collectors.toList());
        setmealDishService.saveBatch(setmealDishes);
    }

    @Override
    public void removeWithDish(List<Long> ids) {
        //查询套餐状态，判断是否可以删除
        LambdaQueryWrapper<Setmeal> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.in(Setmeal::getId, ids).eq(Setmeal::getStatus, 1);
        int count = this.count(lambdaQueryWrapper);
        //不能删除、抛异常
        if (count > 0) {
            throw new CustomException("套餐正在售卖中，不能删除");
        }
        //删除   先删除setmeal
        this.removeByIds(ids);
        //删除关联表   setmealDish
        //delete from setmeal_dish where setmeal_id in (1,2,3,4...)
        LambdaQueryWrapper<SetmealDish> setmealDishLambdaQueryWrapper = new LambdaQueryWrapper<>();
        setmealDishLambdaQueryWrapper.in(SetmealDish::getSetmealId, ids);
        setmealDishService.remove(setmealDishLambdaQueryWrapper);
    }
}
