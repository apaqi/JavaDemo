package lock;

/**
 * User: yangkuan@jd.com
 * Date: 18-6-5
 * Time: 下午4:23
 */
public class CountTest {
    Lock lock = new ReentrantLock();
    public void print() throws InterruptedException {
        lock.lock();
        doAdd();
        lock.unlock();
    }
    public void doAdd() throws InterruptedException {
        lock.lock();
       System.out.println("do Add");
        lock.unlock();
    }

    public static void main(String[] args) throws InterruptedException {

        CountTest countTest = new CountTest();
        countTest.lock = new NoReentrantLock();
        //countTest.lock = new ReentrantLock();
        countTest.print();
    }
}
