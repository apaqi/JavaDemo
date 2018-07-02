package net.socket;

import java.io.*;
import java.net.Socket;
import java.util.Random;

/**
 * 短连接client(阻塞io)
 * User: yangkuan@jd.com
 * Date: 18-6-28
 * Time: 下午1:45
 */
public class SocketClient extends Thread {
    public static void main(String[] args) throws InterruptedException {
        int index = 25;
        for(int i=0;i<index;i++){
            SocketClient socketClient = new SocketClient();
            socketClient.start();
        }
        sleep(1000000L);
    }

    @Override
    public void run(){
        Random rand =new Random();
        Integer time = rand.nextInt(25);
        System.out.println(time);
        writeToServer("localhost", 5209, time);
    }
    /**
     * 向服务器写数据
     * @param serverHost socket服务器地址
     * @param port 端口
     * @author zzj
     */
    public static void writeToServer(String serverHost,int port,int time){
        Socket socket = null;
        try {
            //1.建立客户端socket连接，指定服务器位置及端口
            socket =new Socket(serverHost,port);
            socket.setSoTimeout(1000000);
            //2.得到socket读写流
            OutputStream os=socket.getOutputStream();
            PrintWriter pw=new PrintWriter(os);

            //输入流
            InputStream is=socket.getInputStream();
            BufferedReader br=new BufferedReader(new InputStreamReader(is));

            //3.利用流按照一定的操作，对socket进行读写操作
            String info="client---------------@"+time;
            System.out.println(info);
            pw.write(info);
            pw.flush();
            socket.shutdownOutput();
            //接收服务器的相应
            String reply=null;
            while(!((reply=br.readLine())==null)){
                System.out.println("接收服务器的信息："+reply);
            }
            //4.关闭资源
            br.close();
            is.close();
            pw.close();
            os.close();
            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            if (socket!=null) {
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
