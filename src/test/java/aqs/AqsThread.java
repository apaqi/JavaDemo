package aqs;

import java.util.concurrent.locks.LockSupport;

public class AqsThread implements Runnable {
    public void unpark(Thread thread){
        LockSupport.unpark(thread);
    }
    private int count = 0;

    public void run1() {
        long start = System.currentTimeMillis();
        long end = 0;
        while ((end - start) <= 1000) {
            count++;
            end = System.currentTimeMillis();
        }
        System.out.println("after 1 second.count=" + count);
        //等待或许许可
        LockSupport.park();
        System.out.println("thread over." + Thread.currentThread().isInterrupted());
    }


    public void run() {
        long start = System.currentTimeMillis();
        long end = 0;
       LockSupport.park();
        while ( !Thread.interrupted()) {
            count++;
           System.out.println(count);
        }

        //以下是退出线程前的收尾工作
        System.out.println("线程是否中断："+ Thread.interrupted());
        System.out.println("after 1 second.count=" + count);
        //等待或许许可
      //  LockSupport.park();
        System.out.println("thread over." + Thread.currentThread().isInterrupted());
    }
}
