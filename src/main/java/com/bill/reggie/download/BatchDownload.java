package com.bill.reggie.download;

import net.sf.jsqlparser.statement.select.KSQLJoinWindow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Component
public class BatchDownload {
    @Autowired
    private Download download;

    public void testMethod(int count) {
        // 文件保存目录
        String fileFolder = "E:/test/";
        // 文件远程URL地址
        String url = "https://crpt-cloud-gateway-dev.liuheco.com/crpt-file/file/download/1613010527917883392";
        try {
            long start = System.currentTimeMillis();
            for (int i = 0; i < count; i++) {
                // 文件名称
                String fileName = "test_" + i + ".png";
                // 从远程URL地址读取文件Bytes
                byte[] fileBytes = download.getFileBytesFromUrl(url);
                // 将获取的文件Bytes保存到本地磁盘
                download.saveBytesAsFile(fileBytes, fileFolder, fileName);
                System.out.println("下载文件完成, 文件保存在 " + fileFolder + fileName);
            }
            long end = System.currentTimeMillis();
            System.out.println("消耗时间：" + (end - start));
        } catch (Exception e) {
            System.out.println("发生异常： " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void testTreadPool(int count) {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(
                5,
                5,
                0,
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(5),
                new ThreadPoolExecutor.CallerRunsPolicy()
        );
        for (int i = 0; i < count; i++) {
            int finalI = i;
            executor.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(1000L);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    System.out.println("成功执行！==>" + finalI + "<==" + Thread.currentThread().getName());
                }
            });
        }
        executor.shutdown();
    }

    public void testTreadPool2(int count) {
        for (int i = 0; i < count; i++) {
            System.out.println("成功执行！==>" + i + "<==");
            try {
                Thread.sleep(1000L);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
