package net.socket;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 *  长连接server(阻塞io)
 * User: yangkuan@jd.com
 * Date: 18-6-28
 * Time: 下午12:42
 */
public class SocketServer {
    public static void main(String[] args) throws IOException {
        SocketServer socketService = new SocketServer();
        //1、a)创建一个服务器端Socket，即SocketService
        socketService.oneServer();
    }
    ThreadPoolExecutor executor = new ThreadPoolExecutor(1, 2, 2, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(1));
    public  void oneServer(){
        try{
            ServerSocket server=null;
            try{
                server=new ServerSocket(5209);
                //b)指定绑定的端口，并监听此端口。
                System.out.println("服务器启动成功");
                //创建一个ServerSocket在端口5209监听客户请求
            }catch(Exception e) {
                System.out.println("没有启动监听："+e);
                //出错，打印出错信息
            }
            Socket socket=null;
            try{
                while (true){
                    socket=server.accept();
                    //2、调用accept()方法开始监听，等待客户端的连接
                    //使用accept()阻塞等待客户请求，有客户
                    //请求到来则产生一个Socket对象，并继续执行
                    System.out.println("****received message from client******");
                    IOAsyThread ioAsyThread =   new IOAsyThread(socket);
                    try{
                        executor.execute(ioAsyThread);
                    }catch (Exception e){
                        try{
                            socket.close();
                            ioAsyThread.writeMsgToClient(socket.getOutputStream(), "服务端拒绝，请重试");
                        }catch (Exception e1){
                            System.out.println("异步线程池塞满,回写客户端异常:" + e1.getMessage());
                        }
                        System.out.println("异步线程池塞满:" + e.getMessage());
                    }

                }
            }catch(Exception e) {
                System.out.println("Error."+e);
                //出错，打印出错信息
            }

        }catch(Exception e) {//出错，打印出错信息
            System.out.println("Error."+e);
        }
    }
}

class IOAsyThread extends Thread{
    private Socket connection;
    public IOAsyThread(Socket conSocket){
        this.connection=conSocket;
    }

    public void run(){
        try {

            System.out.println("****received message from client******");

            //读取客户端传过来的数据
            readMessageFromClient(connection.getInputStream());

            // Thread.sleep(4000);
            System.out.println("****received message from client end******");
            //向客户端写入数据
            writeMsgToClient(connection.getOutputStream(),"I am server message!!!");

            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            if (connection!=null) {
                try {
                    connection .close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 读取客户端信息
     * @param inputStream
     */
    private   void readMessageFromClient(InputStream inputStream) throws IOException {
        Reader reader = new InputStreamReader(inputStream);
        BufferedReader br=new BufferedReader(reader);
        String a = null;
        while((a=br.readLine())!=null){
            System.out.println(a);
        }
    }

    /**
     * 响应客户端信息
     * @param outputStream
     * @param string
     */
    public void writeMsgToClient(OutputStream outputStream, String string) throws IOException {
        System.out.println(string);
        Writer writer = new OutputStreamWriter(outputStream);
        writer.append("I am server message!!!");
        writer.flush();
        writer.close();
    }
}