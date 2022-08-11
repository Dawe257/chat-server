package com.dzhenetl;

import com.dzhenetl.util.Logger;

import java.io.*;
import java.net.Socket;
import java.util.List;

public class ServerThread extends Thread {

    private Socket socket;
    private List<ServerThread> threadList;
    private BufferedReader reader;
    private PrintWriter writer;

    public ServerThread(Socket socket, List<ServerThread> threadList) {
        this.socket = socket;
        this.threadList = threadList;
    }

    private void sendMessageToAllClients(String name, String msg) {
        threadList.forEach(x -> x.writer.println(name + " " + msg));
    }

    @Override
    public void run() {
        try {
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);

            writer.println("Привет. Введи свое имя: ");
            String name = reader.readLine();
            writer.println("Добро пожаловать " + name + "!");

            String line;
            while (true) {
                line = reader.readLine();
                Logger.getInstance().log(name, line);
                sendMessageToAllClients(name, line);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
