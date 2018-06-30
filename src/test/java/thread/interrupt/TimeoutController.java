package thread.interrupt;

import java.util.concurrent.TimeoutException;

/**
 * User: yangkuan@jd.com
 * Date: 18-6-28
 * Time: 上午11:24
 */
public class TimeoutController {
    public static void execute(Thread task, long timeout) throws TimeoutException {
        task.start();
        try {
            task.join(timeout);//参见同一目录的join文件见演示
        } catch (InterruptedException e) {
            /* if somebody interrupts us he knows what he is doing */
            System.out.println("线程join等待期间异常:"+e.getMessage());
        }
        if (task.isAlive()) {
            //task.interrupt();
            task.stop();
            throw new TimeoutException("线程执行超时");
        }
    }


    public static void execute(Runnable task, long timeout) throws TimeoutException {
        Thread t = new Thread(task, "Timeout guard");
        t.setDaemon(true);
        execute(t, timeout);

    }
}
