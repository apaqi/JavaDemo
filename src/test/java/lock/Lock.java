package lock;

/**
 * 和并发包的lock实现机制是两回事
 * User: yangkuan@jd.com
 * Date: 18-6-5
 * Time: 下午4:15
 */
public interface Lock{

    public   void lock() throws InterruptedException;
    public   void unlock();
}
