package com.example.someMain;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author 程刘德
 * @version 1.0
 * @Description TODO
 * @date 2019/12/11
 */
public class RunnableDemo implements Runnable{

    public static void main(String[] args)  throws Exception{
        //创建线程池
        ExecutorService exe = Executors.newCachedThreadPool();
        //从线程池中开启五个线程
        for (int i = 0; i < 5; i++) {
            exe.execute(new RunnableDemo());
        }
        exe.shutdown();

        //轮询线程结束
        while (true) {
            if (exe.isTerminated()) {
                System.out.println("结束了！");
                break;
            }
            Thread.sleep(200);
        }

        //线程结束后运行
        System.out.println("所有线程结束");


    }



    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName());
    }
}
