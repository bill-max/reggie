package com.bill.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bill.reggie.common.R;
import com.bill.reggie.entity.Employee;
import com.bill.reggie.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;


@RestController
@RequestMapping("/employee")
@Slf4j
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    /**
     * 员工登录
     *
     * @param request  请求
     * @param employee 前端传回实体类
     * @return
     */
    @PostMapping("/login")
    public R<Employee> login(HttpServletRequest request, @RequestBody Employee employee) {
        // 根据username 查询是否存在
        // 没有返回失败
        // 判断密码是否正确
        // 判断员工状态   状态锁定返回失败
        //1 对页面提交的密码进行加密处理
        String password = DigestUtils.md5DigestAsHex(employee.getPassword().getBytes());
        //2. 查数据库
        LambdaQueryWrapper<Employee> lambdaQueryWrapper = new LambdaQueryWrapper<>();

        lambdaQueryWrapper.eq(Employee::getUsername, employee.getUsername());
        Employee one = employeeService.getOne(lambdaQueryWrapper);
        //登录失败
        if (null == one || !one.getPassword().equals(password)) {
            return R.error("登录失败！用户名不存在或密码错误！");
        }
        //判断员工状态
        if (one.getStatus() == 0) {
            return R.error("登录失败，员工状态已锁定");
        }


        // 登录成功，将员工id存入session 并返回登录成功结果
        request.getSession().setAttribute("employee", one.getId());
        return R.success(one);
    }

    /**
     * 退出
     *
     * @param request 删除session
     * @return 成功
     */
    @PostMapping("/logout")
    public R<String> logout(HttpServletRequest request) {
        //1. 清理session中的用户id
        request.getSession().removeAttribute("employee");
        //2. 返回结果
        return R.success("退出成功");
    }

    /**
     * 新增员工
     *
     * @param employee 员工
     * @return 添加成功
     */
    @PostMapping
    public R<String> save(HttpServletRequest request, @RequestBody Employee employee) {
        //设置默认密码
        employee.setPassword(DigestUtils.md5DigestAsHex("123456".getBytes()));
        //设置默认属性
//        employee.setCreateTime(LocalDateTime.now());
//        employee.setUpdateTime(LocalDateTime.now());

        //获得用户id
//        Long id = (Long) request.getSession().getAttribute("employee");
//        employee.setCreateUser(id);
//        employee.setUpdateUser(id);

        employeeService.save(employee);

        return R.success("success");
    }

    /**
     * 员工信息分页查询
     *
     * @param page
     * @param pageSize
     * @param name
     * @return
     */
    @GetMapping("/page")
    public R<Page<Employee>> page(int page, int pageSize, String name) {
        log.info("page={},pageSize={},name={}", page, pageSize, name);
        Page<Employee> pageInfo = new Page<Employee>(page, pageSize);
        //条件构造器
        LambdaQueryWrapper<Employee> lambdaQueryWrapper = new LambdaQueryWrapper<Employee>();
        lambdaQueryWrapper.like(StringUtils.isNotEmpty(name), Employee::getName, name);
        //排序
        lambdaQueryWrapper.orderByDesc(Employee::getUpdateTime);

        employeeService.page(pageInfo, lambdaQueryWrapper);
        return R.success(pageInfo);
    }

    /**
     * 根据id修改员工信息
     *
     * @param employee 员工
     * @return
     */
    @PutMapping
    public R<String> update(@RequestBody Employee employee) {
        log.info(employee.toString());
        employeeService.updateById(employee);
        return R.success("success");
    }

    /**
     * 根据id查员工
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public R<Employee> getById(@PathVariable Long id) {
        log.info("id==>" + id.toString());
        Employee employee = employeeService.getById(id);
        if (null == employee) {
            return R.error("未查询到");
        }
        return R.success(employee);
    }

}
