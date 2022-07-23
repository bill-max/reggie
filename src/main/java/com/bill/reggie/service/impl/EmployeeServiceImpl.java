package com.bill.reggie.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bill.reggie.entity.Employee;
import com.bill.reggie.mapper.EmployeeMapper;
import com.bill.reggie.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmployeeServiceImpl  extends ServiceImpl<EmployeeMapper,Employee> implements EmployeeService {

}
