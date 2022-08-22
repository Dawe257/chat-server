package com.dzhenetl.server;

import com.dzhenetl.util.PropertiesUtil;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.List;

public class Server {

    private static final int PORT = Integer.parseInt(PropertiesUtil.get("port"));
    private final List<ServerThread> threadList = new ArrayList<>();

    public void start() throws IOException {

        final ServerSocketChannel serverChannel = ServerSocketChannel.open();
        serverChannel.bind(new InetSocketAddress("localhost", PORT));

        while (true) {
            SocketChannel socketChannel = serverChannel.accept();
            ServerThread serverThread = new ServerThread(socketChannel, threadList);
            threadList.add(serverThread);
            serverThread.start();
        }
    }
}
