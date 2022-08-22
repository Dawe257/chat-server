package com.dzhenetl.server;

import com.dzhenetl.util.Logger;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class ServerThread extends Thread {

    private final SocketChannel socket;
    private final ByteBuffer inputBuffer = ByteBuffer.allocate(2 << 10);

    // храним список всех клиентов, чтобы была возможность отправить сообщение всем клиентам
    private final List<ServerThread> threadList;

    private final Logger logger = Logger.getInstance();

    public ServerThread(SocketChannel socket, List<ServerThread> threadList) {
        this.socket = socket;
        this.threadList = threadList;
    }

    @Override
    public void run() {
        try {
            String name = setName();

            socket.write(ByteBuffer.wrap(("Добро пожаловать " + name + "!").getBytes(StandardCharsets.UTF_8)));

            while (socket.isConnected()) {
                int bytesCount = socket.read(inputBuffer);

                if (bytesCount == -1) break;

                final String msg = new String(inputBuffer.array(), 0, bytesCount, StandardCharsets.UTF_8);
                inputBuffer.clear();
                logger.log(name, msg);
                // После получения сообщения отправляем его всем клиентам
                sendMessageToAllClients(name, msg);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String setName() throws IOException {
        socket.write(ByteBuffer.wrap("Привет. Введи свое имя: ".getBytes(StandardCharsets.UTF_8)));
        int bytesCount = socket.read(inputBuffer);
        String name = new String(inputBuffer.array(), 0, bytesCount, StandardCharsets.UTF_8);
        inputBuffer.clear();

        return name;
    }

    private void sendMessageToAllClients(String name, String msg) {
        threadList.forEach(x -> {
            try {
                x.socket.write(ByteBuffer.wrap((name + ": " + msg).getBytes(StandardCharsets.UTF_8)));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}
