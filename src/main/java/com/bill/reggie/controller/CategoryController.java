package com.bill.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bill.reggie.common.R;
import com.bill.reggie.entity.Category;
import com.bill.reggie.entity.Employee;
import com.bill.reggie.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * @author 李建彤
 */
@RestController
@RequestMapping("/category")
@Slf4j
public class CategoryController {
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
    public R<Page<Category>> page(int page, int pageSize) {
        log.info("page={},pageSize={}", page, pageSize);
        Page<Category> pageInfo = new Page<Category>(page, pageSize);
        categoryService.page(pageInfo);
        return R.success(pageInfo);
    }
}
