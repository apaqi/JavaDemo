package thread.object;

import java.util.PriorityQueue;

/**
 * User: yangkuan@jd.com
 * Date: 18-6-5
 * Time: 下午3:03
 */
public class PAndC {
    private static PriorityQueue<Integer> queue=new PriorityQueue<Integer>(10);
    public static void main(String[] args) {
        PAndC object=new PAndC();
        Producer producer=object.new Producer();
        Consumer consumer=object.new Consumer();
        producer.start();
        consumer.start();
        Consumer1 consumer1= new Consumer1();
        consumer1.start();
    }
    //对象的wait方法会阻塞当前线程(释放cpu时间片)，且释放了加载对象身上的锁synchronized。
    //个人觉得wait主要用于释放synchronized锁。

    class Consumer extends Thread
    {
        @Override
        public void run()
        {
            consume();
        }
        private void consume()
        {
            // synchronized (queue) {放在这里更便于测试
            while(true)
            {
                synchronized (queue) {
                    while(queue.size()==0)
                    {
                        try {
                            System.out.println("队列为空");
                            queue.wait();

                        } catch (Exception e) {
                            // TODO: handle exception
                            e.printStackTrace();
                            queue.notify();
                        }
                    }
                  Integer count =  queue.poll();
                    System.out.println("取的元素值："+count);
                    queue.notify();//执行完毕，会释放synchronized的锁，所以生产者线程可以拿到

                }
            }
        }
    }

    class Producer extends Thread
    {
        @Override
        public void run()
        {
            produce();
        }
        private void produce()
        {

            Integer index =0;
           // synchronized (queue) {放在这里更便于测试
            while(true)
            {
                synchronized (queue) {
                    while(queue.size()==10)
                    {

                        try {
                            System.out.println("队列已经满了");
                            queue.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                   // queue.offer(index++);
                    System.out.println("向队列中插入一个元素，长度为"+queue.size());
                    queue.notify();//执行完毕，会释放synchronized的锁，所以消费者线程可以拿到
                }
            }
        }
    }
      static class Consumer1 extends Thread
    {
        @Override
        public void run()
        {
            consume();
        }
        private void consume()
        {
            // synchronized (queue) {放在这里更便于测试
            while(true)
            {
                synchronized (queue) {
                    while(queue.size()==0)
                    {
                        try {
                            System.out.println("队列为空");
                            queue.wait();

                        } catch (Exception e) {
                            // TODO: handle exception
                            e.printStackTrace();
                            queue.notify();
                        }
                    }
                    Integer count =  queue.poll();
                    System.out.println("取的元素值："+count);
                    queue.notify();//执行完毕，会释放synchronized的锁，所以生产者线程可以拿到

                }
            }
        }
    }
}
