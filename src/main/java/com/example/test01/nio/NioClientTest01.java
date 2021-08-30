package com.example.test01.nio;

import java.io.OutputStream;
import java.net.Socket;

public class NioClientTest01 {
    public static void main(String[] args) throws Exception {
        Socket sk = new Socket("127.0.0.1",8899);
        OutputStream op = sk.getOutputStream();
        op.write(("dhlsajdladlksajdlasjdl:"+sk.getPort()).getBytes());
        op.close();
        sk.close();
    }
}
