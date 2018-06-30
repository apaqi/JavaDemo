package thread.interrupt;

import java.io.IOException;
import java.net.Socket;

/**
 * User: yangkuan@jd.com
 * Date: 18-6-28
 * Time: 上午11:09
 */
public     abstract class SocketTask implements Runnable {
    /** The socket */
    private Socket socket;

    /**
     * Set the socket.
     * @param newSocket The new socket.
     */
    protected void setSocket(final Socket newSocket) {
        socket = newSocket;
    }

    /**
     * Return the socket.
     * @return Socket The socket.
     */
    protected Socket getSocket() {
        return socket;
    }
    public abstract void doit() throws InterruptedException;

    /** Execute the logic in this object and keep track of any exceptions. */
    public void run() {

 /*       try {
            doit();

        } catch (InterruptedException e) {
            System.out.println("线程run中断："+e.getMessage());
        }*/
        int index =0;
        while (!Thread.interrupted()){

            if(index%10000==0){//通过此方式，可以验证interrupt命令退出。需要配合interrupted方法使用
                 System.out.println(index);
            }
            index++;
        }
    }
}
