package com.bill.reggie.transaction;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
public class EventListener {

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
//    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void doEvent(MyEvent a) {
        try {
            int i = 1 / 0;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        System.out.println(a.getSource());
        System.out.println(Thread.currentThread().getName());
    }
}

