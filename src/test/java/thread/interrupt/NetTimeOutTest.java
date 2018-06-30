package thread.interrupt;

import net.socket.SocketClient;

import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.TimeoutException;

/**
 * User: yangkuan@jd.com
 * Date: 18-6-28
 * Time: 上午11:25
 */
public class NetTimeOutTest {
    public static void main(String[] args) {
       SocketTask task = new SocketTask() {
           public void doit() throws InterruptedException {
            /*   try {
                   Thread.sleep(300l);//sock建立是io操作，会阻塞。这里sleep模拟io阻塞。
               } catch (InterruptedException e) {
                   System.out.println("线程中断"+e.getMessage());
                  throw e;
               }*/
               try {
                  // Socket socket = new Socket("127.0.0.1",5209);
                //   setSocket(socket);
                   SocketClient.writeToServer("127.0.0.1", 5209,1);
                   System.out.println("完成和服务的响应");
               } catch (Exception e) {
                   System.out.println("建立socket失败:" + e.getMessage());
               }
           }
       };
       try {
           TimeoutController.execute(task, 2000L);
       } catch (TimeoutException e) {
           System.out.println("超时：" + e.getMessage());
       }
       Socket socket = task.getSocket();
    System.out.println("创建sock完毕");
        try {
            Thread.sleep(200000);
        } catch (InterruptedException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }
}
