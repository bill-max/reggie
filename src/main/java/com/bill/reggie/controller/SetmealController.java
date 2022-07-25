package com.bill.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bill.reggie.common.R;
import com.bill.reggie.dto.DishDto;
import com.bill.reggie.dto.SetmealDto;
import com.bill.reggie.entity.Category;
import com.bill.reggie.entity.Dish;
import com.bill.reggie.entity.Setmeal;
import com.bill.reggie.service.CategoryService;
import com.bill.reggie.service.SetmealService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/setmeal")
public class SetmealController {
    @Autowired
    private SetmealService setmealService;

    @Autowired
    private CategoryService categoryService;

    /**
     * 分页查询
     *
     * @param page
     * @param pageSize
     * @return
     */
    @GetMapping("/page")
    public R<Page<SetmealDto>> page(int page, int pageSize, String name) {
        Page<Setmeal> setmealPage = new Page<>(page, pageSize);
        LambdaQueryWrapper<Setmeal> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.like(name != null, Setmeal::getName, name);
        lambdaQueryWrapper.orderByAsc(Setmeal::getPrice);
        setmealService.page(setmealPage, lambdaQueryWrapper);

        //查询分类名称
        Page<SetmealDto> SetmealDtoPage = new Page<>();
        BeanUtils.copyProperties(setmealPage, SetmealDtoPage, "records");
        List<Setmeal> setmealPageRecords = setmealPage.getRecords();

        List<SetmealDto> list = setmealPageRecords.stream().map((item) -> {
            Long categoryId = item.getCategoryId();
            Category category = categoryService.getById(categoryId);

            SetmealDto SetmealDto = new SetmealDto();
            BeanUtils.copyProperties(item, SetmealDto);

            if (category != null) {
                String categoryName = category.getName();
                SetmealDto.setCategoryName(categoryName);
            }

            return SetmealDto;
        }).collect(Collectors.toList());

        SetmealDtoPage.setRecords(list);
        return R.success(SetmealDtoPage);
    }

    // TODO 新增菜品套餐


}
