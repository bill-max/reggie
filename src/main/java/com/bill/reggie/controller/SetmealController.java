package com.bill.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bill.reggie.common.R;
import com.bill.reggie.dto.DishDto;
import com.bill.reggie.dto.SetmealDto;
import com.bill.reggie.entity.Category;
import com.bill.reggie.entity.Dish;
import com.bill.reggie.entity.Setmeal;
import com.bill.reggie.entity.SetmealDish;
import com.bill.reggie.service.CategoryService;
import com.bill.reggie.service.SetmealDishService;
import com.bill.reggie.service.SetmealService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/setmeal")
@Slf4j
public class SetmealController {
    @Autowired
    private SetmealService setmealService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private SetmealDishService setmealDishService;

    /**
     * 根据id查套餐
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public R<SetmealDto> get(@PathVariable Long id) {
        SetmealDto setmealDto = setmealService.getByIdWithDish(id);
        return R.success(setmealDto);
    }


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
        lambdaQueryWrapper.like(name != null, Setmeal::getName, name).eq(Setmeal::getIsDeleted, 0);
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

    /**
     * 修改
     *
     * @param setmealDto
     * @return
     */
    @PutMapping
    public R<String> update(@RequestBody SetmealDto setmealDto) {
        setmealService.updateWithDish(setmealDto);
        return R.success("success");
    }

    /**
     * 菜品套餐保存
     *
     * @param setmealDto
     * @return
     */
    @PostMapping
    public R<String> save(@RequestBody SetmealDto setmealDto) {
        setmealService.saveWithDish(setmealDto);
        return R.success("success");
    }

    /**
     * 删除套餐    一个
     *
     * @param ids
     * @return
     */
    @DeleteMapping
    public R<String> delete(@RequestParam List<Long> ids) {
        log.info("ids:{}", ids);
        setmealService.removeWithDish(ids);
        return R.success("success");
    }

    /**
     * 套餐分类查询
     *
     * @param setmeal
     * @return
     */
    @GetMapping("/list")
    public R<List<SetmealDto>> list(Setmeal setmeal) {
//        log.info("setmeal" + setmeal.toString());
        LambdaQueryWrapper<Setmeal> setmealLambdaQueryWrapper = new LambdaQueryWrapper<>();
        setmealLambdaQueryWrapper.in(setmeal.getCategoryId() != null, Setmeal::getCategoryId, setmeal.getCategoryId())
                .eq(Setmeal::getStatus, 1)
                .orderByDesc(Setmeal::getUpdateTime);
        List<Setmeal> setmeals = setmealService.list(setmealLambdaQueryWrapper);

        List<SetmealDto> setmealDtoList = setmeals.stream().map((item) -> {
            SetmealDto setmealDto = new SetmealDto();
            BeanUtils.copyProperties(item, setmealDto);

            Long setmealId = item.getId();
            LambdaQueryWrapper<SetmealDish> setmealDishLambdaQueryWrapper = new LambdaQueryWrapper<>();
            setmealDishLambdaQueryWrapper.eq(SetmealDish::getSetmealId, setmealId);
            List<SetmealDish> setmealDishes = setmealDishService.list(setmealDishLambdaQueryWrapper);

            setmealDto.setSetmealDishes(setmealDishes);
            return setmealDto;
        }).collect(Collectors.toList());

        return R.success(setmealDtoList);
    }

    /**
     * 设置状态  包括批量功能
     * @param status
     * @param ids
     * @return
     */
    @PostMapping("/status/{status}")
    public R<String> setStatus(@PathVariable int status, @RequestParam List<Long> ids) {
        log.info("ids:{}", ids);
        log.info("status==" + status );
        LambdaUpdateWrapper<Setmeal> lambdaUpdateWrapper = new LambdaUpdateWrapper<>();
        lambdaUpdateWrapper.set(Setmeal::getStatus, status).in(Setmeal::getId, ids);
        setmealService.update(lambdaUpdateWrapper);
        return R.success("success");
    }
}
