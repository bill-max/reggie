package com.bill.reggie.transaction;

import org.springframework.context.ApplicationEvent;

import java.time.Clock;

public class MyEvent extends ApplicationEvent {

    public MyEvent(Object source) {
        super(source);
    }

    public MyEvent(Object source, Clock clock) {
        super(source, clock);
    }
}
