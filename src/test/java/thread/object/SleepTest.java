package thread.object;

/**
 * User: yangkuan@jd.com
 * Date: 18-6-5
 * Time: 下午3:58
 */
public class SleepTest {
    public static Integer object = new Integer(0);
    public static void main(String[] args) {
        ThreadSleep threadSleep = new ThreadSleep();
        threadSleep.start();
        ThreadWithLock threadWithLock = new ThreadWithLock();
        threadWithLock.start();

        ThreadNoLock threadNoLock = new ThreadNoLock();
        threadNoLock.start();
    }

    static class ThreadSleep extends Thread{
        @Override
        public void run() {
            synchronized (object) {
                System.out.println("线程"+Thread.currentThread().getName()+"锁定了对象，然后sleep，只释放cpu，不释放锁。");
                try {
                   // object.wait();//添加了wait，直接释放了cpu也是释放了锁。
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("线程"+Thread.currentThread().getName()+" sleep完毕，run结束，释放了cpu也释放了锁。");
            }
        }
    }

    static class ThreadNoLock extends Thread{
        @Override
        public void run() {

            System.out.println("线程"+Thread.currentThread().getName()+"获得了cpu时间片");
        }
    }

    static class ThreadWithLock extends Thread{
        @Override
        public void run() {
            synchronized (object) {
                System.out.println("线程"+Thread.currentThread().getName()+"，执行run，进行notify");
                object.notify();
                System.out.println("线程"+Thread.currentThread().getName()+"调用了object.notify()");
            }
            System.out.println("线程"+Thread.currentThread().getName()+"释放了锁");
        }
    }
}
