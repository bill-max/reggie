package com.bill.reggie.service.impl;

import com.bill.reggie.service.TestService;
import org.springframework.stereotype.Service;

@Service()
public class TestOneServiceImpl implements TestService {
    @Override
    public String getTask() {
        return "test1";
    }
}
