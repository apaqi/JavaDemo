package thread.call;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * User: yangkuan@jd.com
 * Date: 18-4-27
 * Time: 下午5:50
 */
public class CallableAndFuture {
    public static void main(String[] args) throws TimeoutException, ExecutionException, InterruptedException {
        ExecutorService threadPool = Executors.newCachedThreadPool();
        CompletionService<Integer> cs = new ExecutorCompletionService<Integer>(threadPool);
        List< Future<Integer>> futureList= new ArrayList<Future<Integer>>();
        for(int i = 1; i < 10; i++) {
            final int taskID = i;
           Future<Integer> future =  cs.submit(new Callable<Integer>() {
                public Integer call() throws Exception {
                    Thread.sleep(8000);
                    return taskID;
                }
            });
            futureList.add(future);
        }
        // 可能做一些事情
        for(int i = 1; i < 10; i++) {
            try {
              System.out.println("index=" + i);
             System.out.println(cs.take().get(50, TimeUnit.MILLISECONDS));
               // System.out.println(cs.poll(5000, TimeUnit.MILLISECONDS).get(50, TimeUnit.MILLISECONDS));
            } catch ( Exception e) {
                e.printStackTrace();
            }

        }

    }
}
