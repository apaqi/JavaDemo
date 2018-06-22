package cpu;

import java.util.concurrent.ConcurrentHashMap;

/**
 *判断cpu是否对线程进行公平调度
 * 参考对比其他语言
 * 什么才是真正的Actor模型
 * https://mp.weixin.qq.com/s?__biz=MzIxMjAzMDA1MQ==&mid=2648945467&idx=1&sn=bc28e770ecce7e02511124c2830230d2&scene=4#wechat_redirect
 * User: yangkuan@jd.com
 * Date: 18-6-5
 * Time: 下午2:52
 */
public class SpinLockTest1 {
    static ConcurrentHashMap<String,Integer> spinCounter = new ConcurrentHashMap<String, Integer>();
    public static void main(String[] args) throws InterruptedException {
        Thread1 thread1 = new Thread1();
        thread1.start();
        Thread1 thread2 = new Thread1();
        Thread1 thread3 = new Thread1();
        Thread1 thread4 = new Thread1();
        Thread1 thread5 = new Thread1();
        Thread1 thread6 = new Thread1();
        thread2.start();
        thread3.start();
        thread4.start();
        thread5.start();
        thread6.start();
        PrintThread printThread = new PrintThread();
        printThread.start();
    }

    static class Thread1 extends Thread{

        @Override
        public void run() {
            int  index = 0;
           // synchronized (object1){
            spinCounter.put(Thread.currentThread().getName(),0);
            for (;;){
                //System.out.println("线程"+Thread.currentThread().getName()+"自旋打印。"+index);
                spinCounter.put(Thread.currentThread().getName(),index);
                index++;
            }
           // }
            }
        }

    static class PrintThread extends Thread{

        @Override
        public void run() {
            int  index = 0;

            for (;;){
                try {
                    Thread.sleep(5000L);
                    System.out.println(spinCounter);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }
    }
    }




