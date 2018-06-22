package lock;

/**
 * User: yangkuan@jd.com
 * Date: 18-6-5
 * Time: 下午4:24
 */
public class NoReentrantLock  implements Lock{
    private boolean isLocked = false;
    public synchronized void lock() throws InterruptedException{
        while(isLocked){
            wait();
        }
        isLocked = true;
    }
    public synchronized void unlock(){
        isLocked = false;
        notify();
    }
}
