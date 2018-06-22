package cpu;

/**
 * Linux内核的三种调度策略：
 　　1，SCHED_OTHER 分时调度策略，
 　　2，SCHED_FIFO实时调度策略，先到先服务。一旦占用cpu则一直运行。一直运行直到有更高优先级任务到达或自己放弃
 　　3，SCHED_RR实时调度策略，时间片轮转。当进程的时间片用完，系统将重新分配时间片，并置于就绪队列尾。放在队列尾保证了所有具有相同优先级的RR任务的调度公平
 一般使用使用第三种。
 会主动识别nice值
 Nice值低(被剥夺)，说明是计算密集，CPU时间片要长，但是频率就低了。
 * User: yangkuan@jd.com
 * Date: 18-6-5
 * Time: 下午2:52
 */
public class SpinLockTest {
    public static Integer object1 = new Integer(0);
    public static Integer object2 = new Integer(0);
    public static Integer object3 = new Integer(0);
    public static Integer object4 = new Integer(0);
    public static Integer object5 = new Integer(0);
    public static Integer object6 = new Integer(0);
    public static void main(String[] args) throws InterruptedException {
        Thread1 thread1 = new Thread1();
        thread1.start();
        Thread2 thread2 = new Thread2();
        Thread3 thread3 = new Thread3();
        Thread4 thread4 = new Thread4();
        Thread5 thread5 = new Thread5();
        Thread1 thread6 = new Thread1();
        thread2.start();
        thread3.start();
        thread4.start();
        Thread.sleep(8000l);
        thread5.start();
        thread6.start();

    }

    static class Thread1 extends Thread{
        @Override
        public void run() {
            int  index = 0;
           // synchronized (object1){
            for (;;){
                if(index==0){
                System.out.println("线程"+Thread.currentThread().getName()+"自旋打印。"+index);
                }
                index++;
            }
           // }
            }
        }
    static class Thread2 extends Thread{
        @Override
        public void run() {
            int  index = 0;
         //   synchronized (object2){
                for (;;){
                    if(index==0){
                        System.out.println("线程"+Thread.currentThread().getName()+"自旋打印。"+index);
                    }
                    index++;
                }
          //  }
        }
    }
    static class Thread3 extends Thread{
        @Override
        public void run() {
            int  index = 0;
        //    synchronized (object3){
                for (;;){
                    if(index==0){
                        System.out.println("线程"+Thread.currentThread().getName()+"自旋打印。"+index);
                    }
                    index++;
                }
          //  }
        }
    }
    static class Thread4 extends Thread{
        @Override
        public void run() {
            int  index = 0;
          //  synchronized (object4){
                for (;;){
                    if(index==0){
                        System.out.println("线程"+Thread.currentThread().getName()+"自旋打印。"+index);
                    }
                    index++;
                }
           // }
        }
    }

    static class Thread5 extends Thread{
        @Override
        public void run() {
            int  index = 0;
            //synchronized (object5){
                for (;;){
                    if(index==0){
                        System.out.println("线程"+Thread.currentThread().getName()+"自旋打印。"+index);
                    }
                    index++;
                }
         //   }
        }
    }
    }




