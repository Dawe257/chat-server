package com.dzhenetl;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client {

    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("127.0.0.1", 23444);

        try (BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);
             Scanner scanner = new Scanner(System.in)) {

            System.out.println(in.readLine());
            String name = scanner.nextLine();
            out.println(name);
            System.out.println(in.readLine());

            while (true) {
                String msg = scanner.nextLine();
                out.println(msg);
                System.out.println(in.readLine());
            }
        } catch (IOException e) {

        }
    }
}
