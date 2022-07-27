package com.bill.reggie.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bill.reggie.entity.ShoppingCart;
import com.fasterxml.jackson.databind.ser.Serializers;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ShoppingCartMapper extends BaseMapper<ShoppingCart> {
}
