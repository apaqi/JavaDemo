package threadpool;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * User: yangkuan@jd.com
 * Date: 18-4-27
 * Time: 下午5:50
 */
public class ThreadPoolTest {
    @Test
    public void testFuture() throws TimeoutException, ExecutionException, InterruptedException {
        ExecutorService threadPool = Executors.newScheduledThreadPool(1);//表示消费者线程只有一个
        List<Future<Integer>> futureList = new ArrayList<Future<Integer>>();

        final int taskID = 0;
        Future<Integer> future = threadPool.submit(new Callable<Integer>() {
            public Integer call() throws Exception {
                Thread.sleep(15000);
                System.out.println("Callable call=" + taskID);
                return taskID;
            }
        });
        futureList.add(future);
        try {
            Object object = future.get(500, TimeUnit.MILLISECONDS);
            System.out.println(object);
        } catch (Exception e) {

        }

        Future<Integer> future2 = threadPool.submit(new Callable<Integer>() {
            public Integer call() throws Exception {

                Thread.sleep(4000);
                System.out.println("Callable call=2");
                return taskID;
            }
        });
        try {
            future2.get(500, TimeUnit.MILLISECONDS);
        } catch (Exception e) {

        }
        threadPool.shutdown();
        System.out.println("线程池关闭");//线程池关闭后，里面的消费者线程不会立即停止，因为执行的call 被sleep阻塞了
        Thread.sleep(200000);
    }

    @Test
    public void threadPoolTest() throws InterruptedException {
        //设置以下参数为验证所想:①阻塞队列长度1，且核心线程和最大线程为1，添加到第三个任务，如果前两个没有执行，那么会抛出异常。
        //②核心线程和最大线程为1，表示消费线程只有1，当一个任务实行完毕，才能执行第二个任务。
        //③当shutdown时，并不会实时中断线程。在某些高并发场景下，使用非线程池进行中断线程的时候，可能会有内存溢出。
        //④如果要验证最大线程是否起作用，比如核心线程是1，最大线程是2。那么需要execute放入线程池时，错开放入，通过sleep错开并发
        //⑤最大线程超时过期时间，没有模拟出来。
        final ThreadPoolExecutor executor = new ThreadPoolExecutor(1, 2, 2, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(1));
        Runnable myRunnable1 = new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(6000);
                    System.out.println(Thread.currentThread().getName() + " run1");
                    System.out.println("run1执行，仍然活动的线程数量:" + executor.getActiveCount());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        };
        Runnable myRunnable2 = new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(4000);
                    System.out.println(Thread.currentThread().getName() + " run2");
                    System.out.println("run2执行，仍然活动的线程数量:" + executor.getActiveCount());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        };
        Runnable myRunnable3 = new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(3000);
                    System.out.println(Thread.currentThread().getName() + " run3");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        };
        Runnable myRunnable4 = new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                    System.out.println(Thread.currentThread().getName() + " run4");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        };
        executor.execute(myRunnable1);
        Thread.sleep(1000);
        executor.execute(myRunnable2);
        try {
            executor.execute(myRunnable3);
        } catch (Exception e) {
            System.out.println("阻塞队列溢出3:" + e.getMessage());
        }
        try {
            executor.execute(myRunnable4);
        } catch (Exception e) {
            System.out.println("阻塞队列溢出4:" + e.getMessage());
        }

        Thread.sleep(10000);
        Runnable myRunnable5 = new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                    System.out.println(Thread.currentThread().getName() + " run5");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        };

        System.out.println("run5执行，仍然活动的线程数量:" + executor.getActiveCount());
        executor.execute(myRunnable5);
        executor.shutdown();//不能实时中断线程，如果不是使用线程池，会造成可能的线程积压，jvm内存溢出
        System.out.println("线程池关闭");
        Thread.sleep(200000);
    }
}
