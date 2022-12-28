package com.bill.reggie.transaction;

import com.bill.reggie.dto.DishDto;
import com.bill.reggie.entity.User;
import com.bill.reggie.mapper.DishMapper;
import com.bill.reggie.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationAdapter;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.util.ArrayList;
import java.util.List;
@Component
@Slf4j
public class TransactionTest {
    @Autowired
    EventListener listener;

    @Autowired
    UserMapper userMapper;


    @Transactional(rollbackFor = Exception.class)
    public void save() {
        User user = new User();
        user.setId(1L);
        user.setName("hx2");
        userMapper.updateById(user);

        TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronizationAdapter() {
            @Override
            public void beforeCommit(boolean readOnly) {
                int i = 1 / 0;
                System.out.println("100");
                log.info("====end====");
            }

/*            @Override
            public void afterCommit() {
                int i = 1 / 0;
                System.out.println("100");
                log.info("====end====");
            }*/

/*            @Override
            public void afterCompletion(int status) {
                if (status == TransactionSynchronization.STATUS_COMMITTED) {
                    int i = 1 / 0;
                    System.out.println("100");
                    log.info("====end====");
                }
            }*/
        });
    }


    @Transactional
    public void service(int index) {
        User user = new User();
        user.setId(1L);
        user.setName("hx");
        userMapper.updateById(user);

        System.out.println("====start event====");
        listener.doEvent(new MyEvent("event done ====" + user.getId()));
    }
}

