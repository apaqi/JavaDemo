package net.nio2;


import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;
import java.util.logging.Logger;

/**
 * NIO服务端
 *
 * @author shirdrn
 */
public class NioTcpServer extends Thread {

    private static final Logger log = Logger.getLogger(NioTcpServer.class.getName());
    private InetSocketAddress inetSocketAddress;
    private Handler handler = new ServerHandler();

    public NioTcpServer(String hostname, int port) {
        inetSocketAddress = new InetSocketAddress(hostname, port);
    }

    /**
     * 个人看法，1、如果是短连接，完全可以使用阻塞的socket server(SocketServer.java)，只要将异步处理的线程池阻塞队列设为无界，
     * 客户端后来的socket连接请求，放入队列排队就好，客户端用完即可关闭，
     * 但是不适用于长连接，因为客户端可能通过长连接发送数据，服务端及时处理，如果这样，那么服务端只能使用一个线程长时间阻塞。
     * 所以阻塞io适用于通常的web开发(web集群可以应对高并发，但是单台web服务器应对高并发使用nio较好)，NIO适用于长连接的游戏类。
     * 2、redis使用的是nio，但客户端都是短连接，所以如果客户端和redis server能相互专属，那么使用阻塞io也可以。
     * redis server通过给很多业务使用，不像数据库可能给固定业务系统使用，所以redis server使用nio比较好；
     * 不过redis client仍然可以建立阻塞的长连接池，避免短连接建立的开销。
     * 3、如果mysql server使用nio模型，那么业务系统集群有n个实例，也不怕长连接超过了。现阶段mysql server服务器连接数是4000，举例:
     * 如果集群有200个实例，每个实例的数据库连接池连接个数就不能超过20个。
     *
     * 以上说的是epoll模型的nio。select的fds集合最多1024不适合高并发，暴力轮询io事件，性能低
     */
    @Override
    public void run() {
        try {
            Selector selector = Selector.open(); // 打开选择器
            ServerSocketChannel serverSocketChannel = ServerSocketChannel.open(); // 打开通道
            serverSocketChannel.configureBlocking(false); // 非阻塞
            serverSocketChannel.socket().bind(inetSocketAddress);
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT); // 向通道注册选择器和对应事件标识
            log.info("Server: socket server started.");
            while(true) { // 轮询
                int nKeys = selector.select();
                if(nKeys>0) {
                    Set<SelectionKey> selectedKeys = selector.selectedKeys();
                    Iterator<SelectionKey> it = selectedKeys.iterator();
                    while(it.hasNext()) {
                        SelectionKey key = it.next();
                        if(key.isAcceptable()) {//TODO 可以将handler做成线程，放入线程池
                           // log.info("Server: SelectionKey is acceptable.");
                            handler.handleAccept(key);
                        } else if(key.isReadable()) {
                           // log.info("Server: SelectionKey is readable.");
                            handler.handleRead(key);
                        } else if(key.isWritable()) {
                           // log.info("Server: SelectionKey is writable.");
                            handler.handleWrite(key);
                        }
                        it.remove();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 简单处理器接口
     *
     * @author shirdrn
     */
    interface Handler {
        /**
         * 处理{@link SelectionKey#OP_ACCEPT}事件
         * @param key
         * @throws IOException
         */
        void handleAccept(SelectionKey key) throws Exception;
        /**
         * 处理{@link SelectionKey#OP_READ}事件
         * @param key
         * @throws IOException
         */
        void handleRead(SelectionKey key) throws Exception;
        /**
         * 处理{@link SelectionKey#OP_WRITE}事件
         * @param key
         * @throws IOException
         */
        void handleWrite(SelectionKey key) throws IOException;
    }

    /**
     * 服务端事件处理实现类
     *
     * @author shirdrn
     */
    class ServerHandler implements Handler {

        @Override
        public void handleAccept(SelectionKey key) throws Exception {
            ServerSocketChannel serverSocketChannel = (ServerSocketChannel)key.channel();
            SocketChannel socketChannel = serverSocketChannel.accept();
          //  log.info("Server: accept client socket " + socketChannel);
            socketChannel.configureBlocking(false);
            socketChannel.register(key.selector(), SelectionKey.OP_READ);
            Thread.sleep(1000);
            System.out.println("Server: accept client socket " + socketChannel);
        }

        @Override
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
                    System.out.println(data);
                    Thread.sleep(new Integer(time).intValue()*1000);
                  //  System.out.println("Server: data = " +data);
                    byteBuffer.flip();
                    try{
                    socketChannel.write(byteBuffer);
                    }catch (Exception e){

                    }
                    break;
                }
            }
            socketChannel.close();
        }

        @Override
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

    public static void main(String[] args) {
        NioTcpServer server = new NioTcpServer("localhost", 5209);
        server.start();
    }
}
