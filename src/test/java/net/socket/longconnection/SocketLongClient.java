package net.socket.longconnection;

import java.io.*;
import java.net.Socket;
import java.util.Random;

/**
 * 长连接client(阻塞io)
 * User: yangkuan@jd.com
 * Date: 18-6-28
 * Time: 下午1:45
 */
public class SocketLongClient extends Thread {
    public static void main(String[] args) throws InterruptedException {
        int index = 1;
        for (int i = 0; i < index; i++) {
            SocketLongClient socketClient = new SocketLongClient();
            socketClient.start();
        }
        sleep(1000000L);
    }

    @Override
    public void run() {
        Random rand = new Random();
        Integer time = rand.nextInt(3);
        //  System.out.println(time);
        writeToServer("localhost", 5209, 1);
    }

    /**
     * 向服务器写数据
     *
     * @param serverHost socket服务器地址
     * @param port       端口
     * @author zzj
     */
    public static void writeToServer(String serverHost, int port, int time) {
        Socket socket = null;
        try {
            //1.建立客户端socket连接，指定服务器位置及端口
            socket = new Socket(serverHost, port);
            socket.setSoTimeout(1000000);
            OutputStream os = socket.getOutputStream();
            //接收服务器的相应输入流
            InputStream readServerIs = socket.getInputStream();

            PrintWriter outPw = new PrintWriter(os);
            ObjectOutputStream serverOutput = writeToServer(null, os, outPw, time);

            //  socket.shutdownOutput();// 关闭该套接字的输入流
            BufferedReader serverReader = readFromServer(readServerIs, null);
            sleep(1000l);
            serverOutput = writeToServer(serverOutput, os, outPw, time);

            //  socket.shutdownOutput();
            readFromServer(readServerIs, serverReader);

            //4.关闭资源

            serverReader.close();
            readServerIs.close();
            outPw.close();
            serverOutput.close();
            os.close();
            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (socket != null) {
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static ObjectOutputStream writeToServer(ObjectOutputStream oos, OutputStream os, PrintWriter outPw, int time) throws IOException {
        String info = "client---------------@" + time;

/*
        //3.利用流按照一定的操作，对socket进行读写操作
        // System.out.println(info);
        outPw.write(info);
        outPw.flush();
                    os.flush();
*/
        if (oos == null)
            oos = new ObjectOutputStream(os);
        oos.writeObject(info);
        oos.flush();
        return oos;

    }

    public static BufferedReader readFromServer(InputStream readServerIs, BufferedReader br) throws IOException {
        if (br == null)
            br = new BufferedReader(new InputStreamReader(readServerIs));/*
        String reply=null;
        while(!((reply=br.readLine())==null)){
            System.out.println("接收服务器的信息："+reply);
        }*/

        byte[] data = new byte[128];
        int len = readServerIs.read(data);
        if (len > 0) {
            String clientSendInfo = new String(data, 0, len);
            if ("shutdown".equals(clientSendInfo)) {

            } else {
                System.out.println("readMessageFromServer:" + clientSendInfo);
            }
        }
        return br;

    }

}
