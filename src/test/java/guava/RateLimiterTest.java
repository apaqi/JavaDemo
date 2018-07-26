package guava;

import com.google.common.util.concurrent.RateLimiter;

public class RateLimiterTest {
    public static void main(String[] args) {
        Long start = System.currentTimeMillis();
        RateLimiter limiter = RateLimiter.create(2.0);
        for (int i = 0;i < 10;i ++) {
            double acquire = limiter.acquire();//返回等待的时间
            System.out.println("call execute.." + i+":"+acquire);
        }
        Long end = System.currentTimeMillis();

        System.out.println(end - start);

    }
}
