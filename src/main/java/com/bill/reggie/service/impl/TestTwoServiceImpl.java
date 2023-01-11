package com.bill.reggie.service.impl;

import com.bill.reggie.service.TestService;
import org.springframework.stereotype.Service;

@Service()
public class TestTwoServiceImpl implements TestService {
    @Override
    public String getTask() {
        return "test2";
    }
}
