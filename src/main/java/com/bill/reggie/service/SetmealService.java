package com.bill.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.bill.reggie.dto.SetmealDto;
import com.bill.reggie.entity.Setmeal;

import java.util.List;

/**
 * @author 李建彤
 */
public interface SetmealService extends IService<Setmeal> {
    SetmealDto getByIdWithDish(Long id);

    void saveWithDish(SetmealDto setmealDto);

    void removeWithDish(List<Long> ids);

    void updateWithDish(SetmealDto setmealDto);
}
