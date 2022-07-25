package com.bill.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.bill.reggie.entity.Category;

/**
 * @author 李建彤
 */
public interface CategoryService extends IService<Category> {
    public void remove(Long id);
}
