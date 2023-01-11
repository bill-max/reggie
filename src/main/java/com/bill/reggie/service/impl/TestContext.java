package com.bill.reggie.service.impl;

import com.bill.reggie.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class TestContext {
    @Autowired
    Map<String, TestService> map;

    public TestService get(String param) {
        for (Map.Entry entry : map.entrySet()) {
            System.out.println(entry.getKey());
            System.out.println(entry.getValue());
        }
        return map.get(param);
    }
}
