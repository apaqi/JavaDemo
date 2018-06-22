package jdk.lock;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * User: yangkuan@jd.com
 * Date: 18-6-8
 * Time: 上午9:50
 */
public class ConditonTest {
    final Lock lock = new ReentrantLock();
    final Condition condition = lock.newCondition();

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        ConditonTest test = new ConditonTest();
        Producer producer = test.new Producer();
        Consumer consumer = test.new Consumer();


        consumer.start();
       // producer.start();
    }

    class Consumer extends Thread{

        @Override
        public void run() {
            consume();
        }

        private void consume() {

            try {
             lock.lock();
                System.out.println("准备await，"+this.currentThread().getName());
                condition.await();
                System.out.println("我在等一个新信号"+this.currentThread().getName());
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } finally{
                System.out.println("拿到一个信号"+this.currentThread().getName());
                lock.unlock();
            }

        }
    }

    class Producer extends Thread{

        @Override
        public void run() {
            produce();
        }

        private void produce() {
            try {
                lock.lock();
                System.out.println("我拿到锁"+this.currentThread().getName());
                condition.signalAll();
                System.out.println("我发出了一个信号："+this.currentThread().getName());
            } finally{
                lock.unlock();
            }
        }
    }

}
