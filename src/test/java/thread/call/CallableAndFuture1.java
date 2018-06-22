package thread.call;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * User: yangkuan@jd.com
 * Date: 18-4-27
 * Time: 下午5:50
 */
public class CallableAndFuture1 {
    public static void main(String[] args) throws TimeoutException, ExecutionException, InterruptedException {
        ExecutorService threadPool = Executors.newScheduledThreadPool(1);
        List< Future<Integer>> futureList= new ArrayList<Future<Integer>>();
        for(int i = 1; i < 10; i++) {
            final int taskID = i;
           Future<Integer> future =  threadPool.submit(new Callable<Integer>() {
                public Integer call() throws Exception {
                    Thread.sleep(2000000);
                    return taskID;
                }
            });
            futureList.add(future);
        }

        int i = 0;
        for(Future<Integer> future:futureList){
            System.out.println("index=" + i);
            System.out.println(future.get(150000, TimeUnit.MILLISECONDS));
            i++;
        }
    }
}
