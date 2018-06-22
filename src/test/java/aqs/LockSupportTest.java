package aqs;

import org.junit.Test;

import java.util.concurrent.locks.LockSupport;

/**
 * User: yangkuan@jd.com
 * Date: 18-5-22
 * Time: 下午2:19
 */
public class LockSupportTest {
    public static void main(String[] args) {
        Thread thread = Thread.currentThread();
        LockSupport.unpark(thread);//释放许可
        LockSupport.park();// 获取许可
        System.out.println("b");
    }

    @Test
    public void interruptThread() throws Exception {
        Thread t = new Thread(new AqsThread());
        t.start();
        Thread.sleep(2000);
        // 中断线程
       t.interrupt();
       t.join();
        System.out.println("main over");
    }

    @Test
    public void unparkThread() throws Exception {
        AqsThread aqsThread = new AqsThread();
        Thread t = new Thread(aqsThread);
        t.start();
       Thread.sleep(2000);
        // 中断线程
        LockSupport.unpark(t);
        LockSupport.park(t);
      // t.join();
        System.out.println("main over");
    }
}
