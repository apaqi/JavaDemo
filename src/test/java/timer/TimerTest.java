package timer;

import org.junit.Test;

import java.util.Timer;
import java.util.TimerTask;

/**
 * User: yangkuan@jd.com
 * Date: 18-6-28
 * Time: 下午3:58
 */
public class TimerTest {
    @Test
    public void timerTest() throws InterruptedException {
        Timer timer =   new Timer();
        timer.schedule(tt1, 40000);
        timer.schedule(tt2, 30000);//加入进去会根据延时时间排序(fixUp)，延时最短的，优先放在队首。通过wait  notity进行信号通知

        Thread.sleep(1000000);
        System.out.println("timerTest");
    }

    TimerTask tt1 = new TimerTask()
    {
        Integer index = 1;
        @Override
        public void run()
        {
             System.out.println("执行timerTast tt1");
        }

    };

    TimerTask tt2 = new TimerTask()
    {

        Integer index = 2;
        @Override
        public void run()
        {
            System.out.println("执行timerTast tt2");
        }

    };
}
