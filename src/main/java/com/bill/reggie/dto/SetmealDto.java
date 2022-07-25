package com.bill.reggie.dto;

import com.bill.reggie.entity.Setmeal;
import com.bill.reggie.entity.SetmealDish;
import lombok.Data;
import java.util.List;

@Data
public class SetmealDto extends Setmeal {

    private List<SetmealDish> setmealDishes;

    private String categoryName;
}
