package com.bill.reggie;

import com.bill.reggie.download.BatchDownload;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class TreadPoolTests {
    @Autowired
    private BatchDownload batchDownload;


    @Test
    void testBatchPoll() {
        long start = System.currentTimeMillis();
        batchDownload.testTreadPool(100);
        long end = System.currentTimeMillis();
        System.out.println("消耗时间：" + (end - start));
    }

    @Test
    void testBatch() {
        long start = System.currentTimeMillis();
        batchDownload.testTreadPool2(100);
        long end = System.currentTimeMillis();
        System.out.println("消耗时间：" + (end - start));
    }
}
