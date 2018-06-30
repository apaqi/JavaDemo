package thread.join;

/**
 * User: yangkuan@jd.com
 * Date: 18-6-28
 * Time: 上午11:30
 */
public class ThreadJoin  extends Thread{
    public ThreadJoin(String name){
        super(name);
    }
    @Override
    public void run(){
    /*    try {
            Thread.sleep(10l);//假如sleep马上释放cpu，那么下面程序还没有执行，main函数的主线程就可能执行完毕。
        } catch (InterruptedException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }*/
        for(int i=0;i<10000;i++){
            System.out.println(this.getName() + ":" + i);
        }
    }
}
