package com.bill.reggie.threadPool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadPoolTest {
    public static void main(String[] args) {
        //1.创建一个大小为5的线程池
        ExecutorService threadPool = Executors.newFixedThreadPool(5);
        //2.使用线程池执行任务一
        for (int i = 0; i < 5; i++) {
            //给线程池添加任务
            threadPool.submit(new Runnable() {
                @Override
                public void run() {
                    System.out.println("线程名" + Thread.currentThread().getName() + "在执行任务1");
                }
            });
        }
        //2.使用线程池执行任务二
        for (int i = 0; i < 8; i++) {
            //给线程池添加任务
            threadPool.submit(new Runnable() {
                @Override
                public void run() {
                    System.out.println("线程名" + Thread.currentThread().getName() + "在执行任务2");
                }
            });
        }

    }

}
