package org.example;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Reactor 多线程模型：将读写交给线程池（Worker）
 */
public class NioServer_02_MultiThread {
    private static int port = 8209;

    public static void main(String[] args) throws IOException {
        ServerSocketChannel serverSocket = ServerSocketChannel.open();
        serverSocket.socket()
                .bind(new InetSocketAddress(port));
        // 非阻塞
        serverSocket.configureBlocking(Boolean.FALSE);

        Selector selector = Selector.open();
        // 服务端socket注册到selector上（对接收连接的事件感兴趣）
        serverSocket.register(selector, SelectionKey.OP_ACCEPT);
        while (true) {
            // 等待事件
            selector.select();

            Iterator<SelectionKey> keyIterator = selector.selectedKeys().iterator();
            while (keyIterator.hasNext()) {
                SelectionKey selectionKey = keyIterator.next();
                if (selectionKey.isAcceptable()) {
                    // 连接逻辑
                    ServerSocketChannel serverSocketChannel = (ServerSocketChannel) selectionKey.channel();
                    SocketChannel socketChannel = serverSocketChannel.accept();
                    socketChannel.configureBlocking(Boolean.FALSE);
                    socketChannel.register(selectionKey.selector(), SelectionKey.OP_READ);
                    System.out.println("客户端连接完成！");
                } else if (selectionKey.isReadable()) {
                    // 读、写、其他业务逻辑都在当前流程完成
//                    SocketChannel socketChannel = (SocketChannel) selectionKey.channel();
//                    ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
//                    int read = socketChannel.read(byteBuffer);
//                    System.out.println("客户端读取数据：" + new String(byteBuffer.array(), 0, read, StandardCharsets.UTF_8));
//                    socketChannel.write(ByteBuffer.wrap("over!".getBytes(StandardCharsets.UTF_8)));
//                    // 继续注册读取事件
//                    socketChannel.register(selectionKey.selector(), SelectionKey.OP_READ);
                    // 交给线程池处理
                    executor.execute(new Worker(selectionKey));
                }
                // 事件处理完成，移除，防止重复处理
                keyIterator.remove();
            }
        }
    }

    private static Executor executor = Executors.newFixedThreadPool(3);

    private static class Worker implements Runnable {
        private SelectionKey selectionKey;

        public Worker(SelectionKey selectionKey) {
            this.selectionKey = selectionKey;
        }

        @Override
        public void run() {
            // 读、写、其他业务逻辑都在当前流程完成
            SocketChannel socketChannel = (SocketChannel) selectionKey.channel();
            ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
            int read = 0;
            try {
                read = socketChannel.read(byteBuffer);
                System.out.println("客户端读取数据：" + new String(byteBuffer.array(), 0, read, StandardCharsets.UTF_8));
                socketChannel.write(ByteBuffer.wrap("over!".getBytes(StandardCharsets.UTF_8)));
                // 继续注册读取事件
                socketChannel.register(selectionKey.selector(), SelectionKey.OP_READ);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
