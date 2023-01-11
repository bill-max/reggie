package com.bill.reggie;

import com.bill.reggie.download.BatchDownload;
import com.bill.reggie.download.Download;
import com.bill.reggie.service.EmployeeService;
import com.bill.reggie.service.impl.TestContext;
import com.bill.reggie.transaction.TransactionTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class ReggieApplicationTests {

    @Autowired
    private TestContext testContext;

    @Autowired
    private TransactionTest transactionTest;

    @Autowired
    private Download download;

    @Autowired
    private BatchDownload batchDownload;

    @Test
    void TestTransaction() {
        transactionTest.save();
//		transactionTest.service(1);
        System.out.println("end");
    }

    @Test
    void test() {
        System.out.println(testContext.get("testOneServiceImpl").getTask());
    }

    @Test
    void testSPEL() {
        List<Integer> list = new ArrayList<>() {{
            add(1);
            add(2);
            add(3);
        }};

        ExpressionParser parser = new SpelExpressionParser();
        Expression expression = parser.parseExpression("[1]");
        System.out.println(expression.getValue(list));

    }


    @Test
    void testDownload() {
        // 文件保存目录
        String fileFolder = "E:/downloadTest";
        // 文件名称
        String fileName = "test.png";
        // 文件远程URL地址
        String url = "https://crpt-cloud-gateway-dev.liuheco.com/crpt-file/file/download/1613010527917883392";
        try {
            // 从远程URL地址读取文件Bytes
            byte[] fileBytes = download.getFileBytesFromUrl(url);
            // 将获取的文件Bytes保存到本地磁盘
            download.saveBytesAsFile(fileBytes, fileFolder, fileName);
            System.out.println("下载文件完成, 文件保存在 " + fileFolder + fileName);
        } catch (Exception e) {
            System.out.println("发生异常： " + e.getMessage());
            e.printStackTrace();
        }
    }

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
