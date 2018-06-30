package net.nio3;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * User: yangkuan@jd.com
 * Date: 18-6-30
 * Time: 下午5:29
 */
public class Reactor implements Runnable {
    public static void main(String[] args) throws IOException {
        Reactor server = new Reactor(5209);
     //   server.start();
    }

    final Selector selector;
    final ServerSocketChannel serverSocketChannel;

    Reactor(int port) throws IOException {
        selector = Selector.open();
        serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.socket().bind(new InetSocketAddress(port));
        serverSocketChannel.configureBlocking(false);
        SelectionKey selectionKey0 = serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        selectionKey0.attach(new Acceptor());
    }

    public void run() {
        try {
            while (!Thread.interrupted()) {
                selector.select();
                Set selected = selector.selectedKeys();
                Iterator it = selected.iterator();
                while (it.hasNext()) {
                    dispatch((SelectionKey) (it.next()));
                }
                selected.clear();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    void dispatch(SelectionKey k) {
        Runnable r = (Runnable) (k.attachment());
        if (r != null) {
            r.run();
        }
    }

    class Acceptor implements Runnable {
        public void run() {
            try {
                SocketChannel socketChannel = serverSocketChannel.accept();
                if (socketChannel != null) {
                    new Handler(selector, socketChannel);
                }
                System.out.println("Connection Accepted by Reactor");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

    }
}



      class Handler implements Runnable {
        final SocketChannel socketChannel;
        final SelectionKey selectionKey;
        ByteBuffer input = ByteBuffer.allocate(1024);
        static final int READING = 0, SENDING = 1;
        int state = READING;
        String clientName = "";

        Handler(Selector selector, SocketChannel c) throws IOException {
            socketChannel = c;
            c.configureBlocking(false);
            selectionKey = socketChannel.register(selector, 0);
            selectionKey.attach(this);
            selectionKey.interestOps(SelectionKey.OP_READ);
            selector.wakeup();
        }

        public void run() {
            try {
                if (state == READING) {
                    ServerHandler serverHandler = new ServerHandler();
                    serverHandler.handleRead(selectionKey);
                    //read();

                } else if (state == SENDING) {
                  //  send();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

    }

class ServerHandler   {


    public void handleAccept(SelectionKey key) throws IOException {
        ServerSocketChannel serverSocketChannel = (ServerSocketChannel)key.channel();
        SocketChannel socketChannel = serverSocketChannel.accept();
        //  log.info("Server: accept client socket " + socketChannel);
        socketChannel.configureBlocking(false);
        socketChannel.register(key.selector(), SelectionKey.OP_READ);
    }

    public void handleRead(SelectionKey key) throws Exception {
        ByteBuffer byteBuffer = ByteBuffer.allocate(512);
        SocketChannel socketChannel = (SocketChannel)key.channel();
        socketChannel.configureBlocking(false);
        while(true) {
            int readBytes = socketChannel.read(byteBuffer);
            if(readBytes>0) {
                //   log.info("Server: readBytes = " + readBytes);
                String data =  new String(byteBuffer.array(), 0, readBytes);
                String time = data.split("@")[1];
                Thread.sleep(new Integer(time).intValue()*1000);
                //  System.out.println("Server: data = " +data);
                System.out.println(data);
                byteBuffer.flip();
                socketChannel.write(byteBuffer);//TODO 需要加一场捕捉，不然客户端超时，会异常
                break;
            }
        }
        socketChannel.close();
    }

    public void handleWrite(SelectionKey key) throws IOException {
        ByteBuffer byteBuffer = (ByteBuffer) key.attachment();
        byteBuffer.flip();
        SocketChannel socketChannel = (SocketChannel)key.channel();
        socketChannel.write(byteBuffer);
        if(byteBuffer.hasRemaining()) {
            key.interestOps(SelectionKey.OP_READ);
        }
        byteBuffer.compact();
    }
}