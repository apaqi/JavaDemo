package thread.join;

import org.junit.Test;

/**
 * User: yangkuan@jd.com
 * Date: 18-6-28
 * Time: 上午11:31
 */
public class JoinTest {
    @Test
    public void join0Test() throws InterruptedException {
        ThreadJoin t1 = new ThreadJoin("小明");
        ThreadJoin t2 = new ThreadJoin("小东");
        t1.start();
        /**join的意思是使得放弃当前线程的执行，并返回对应的线程，例如下面代码的意思就是：
         程序在main线程中调用t1线程的join方法，则main线程放弃cpu控制权，并返回t1线程继续执行直到线程t1执行完毕
         所以结果是t1线程执行完后，才到主线程执行，相当于在main线程中同步t1线程，t1执行完了，main线程才有执行的机会
         */
        t1.join();
        System.out.println("t1运行end");
        t2.start();
        System.out.println("t2运行end");
    }
    @Test
    public void joinTimeOutTest() throws InterruptedException {
        ThreadJoin t1 = new ThreadJoin("小明");
        ThreadJoin t2 = new ThreadJoin("小东");
        t1.start();
        /**join方法可以传递参数，join(timeout)表示main线程会等待t1线程timeout毫秒，timeout毫秒过去后，
         * main线程和t1线程之间执行顺序由串行执行变为普通的并行执行
         */
     //   t1.join(10000);//不join等待的表现
        t2.start();
        t1.join(10);
        System.out.println("t1运行end");
        System.out.println("t2运行end");
    }
}
