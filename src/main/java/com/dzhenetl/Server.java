package com.dzhenetl;

import com.dzhenetl.util.PropertiesUtil;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server {

    private static final int PORT = Integer.parseInt(PropertiesUtil.get("port"));

    public static void main(String[] args) throws IOException {

        List<ServerThread> threadList = new ArrayList<>();
        ServerSocket serverSocket = new ServerSocket(PORT);

        while (true) {
            Socket clientSocket = serverSocket.accept();
            ServerThread serverThread = new ServerThread(clientSocket, threadList);
            threadList.add(serverThread);
            serverThread.start();
        }
    }
}
