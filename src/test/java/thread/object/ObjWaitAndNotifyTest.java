package thread.object;

/**
 * User: yangkuan@jd.com
 * Date: 18-6-5
 * Time: 下午2:52
 */
public class ObjWaitAndNotifyTest {
    public static Integer object = new Integer(0);
    public static void main(String[] args) {
        Thread1 thread1 = new Thread1();
        thread1.start();

        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Thread2 thread2 = new Thread2();
        thread2.start();
    }

    static class Thread1 extends Thread{
        @Override
        public void run() {
            synchronized (object) {
                try {
                    System.out.println("线程"+Thread.currentThread().getName()+"，执行run，进行wait");
                    object.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("线程"+Thread.currentThread().getName()+"获取到了锁");
            }
        }
    }

    static class Thread2 extends Thread{
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

