package com.example.test01.nio;

import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class NioServerTest01 {
    public static void main(String[] args) throws Exception {
        Selector selector = Selector.open();

        ServerSocketChannel ssc = ServerSocketChannel.open();
        ssc.configureBlocking(false);
        ssc.register(selector, SelectionKey.OP_ACCEPT);


        ServerSocketChannel ssc2 = ServerSocketChannel.open();
        ssc2.configureBlocking(false);
        ssc2.register(selector, SelectionKey.OP_ACCEPT);

        ServerSocket ss = ssc.socket();
        ss.bind(new InetSocketAddress("127.0.0.1",8888));

        ServerSocket ss2 = ssc2.socket();
        ss2.bind(new InetSocketAddress("127.0.0.1",8899));

        while(true){
            selector.select();
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            Iterator<SelectionKey> it = selectionKeys.iterator();
            while(it.hasNext()){
                SelectionKey sk = it.next();
                if(sk.isAcceptable()) {
                    ServerSocketChannel ssc1 = (ServerSocketChannel) sk.channel();
                    SocketChannel sc1 = ssc1.accept();
                    sc1.configureBlocking(false);
                    sc1.register(selector, SelectionKey.OP_READ);
                    it.remove();
                    //System.out.println(readDataFromSocketChannel(sc1));
                }else if(sk.isReadable()){
                    SocketChannel sc1 = (SocketChannel)sk.channel();
                    System.out.println(readDataFromSocketChannel(sc1));
                    sc1.close();
                    it.remove();
                }
            }
        }

    }
    private static String readDataFromSocketChannel(SocketChannel sChannel) throws Exception {
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        StringBuilder data = new StringBuilder();
        while (true) {
            buffer.clear();
            int n = sChannel.read(buffer);
            if (n == -1) {
                break;
            }
            buffer.flip();
            int limit = buffer.limit();
            char[] dst = new char[limit];
            for (int i = 0; i < limit; i++) {
                dst[i] = (char) buffer.get(i);
            }
            data.append(dst);
            buffer.clear();
        }
        return data.toString();
    }
}
