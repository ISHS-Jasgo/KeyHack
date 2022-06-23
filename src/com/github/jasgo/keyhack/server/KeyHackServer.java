package com.github.jasgo.keyhack.server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class KeyHackServer extends Thread {

    public static List<Socket> socketList = new ArrayList<>();
    public static Socket socket = null;

    public KeyHackServer(Socket socket) {
        KeyHackServer.socket = socket;
        socketList.add(socket);
    }

    @Override
    public void run() {
        try {
            BufferedReader system = new BufferedReader(new InputStreamReader(System.in));
            PrintWriter writer = new PrintWriter(socket.getOutputStream(), true, StandardCharsets.UTF_8);
            String line;
            while ((line = system.readLine()) != null) {
                writer.println(line);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        try {
            ServerSocket server = new ServerSocket(3000);
            System.out.println("서버가 성공적으로 열렸습니다.");
            while (true) {
                Socket socket = server.accept();
                Thread thread = new KeyHackServer(socket);
                thread.start();
                new ListeningThread().start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static class ListeningThread extends Thread {
        @Override
        public void run() {
            try {
                BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
                String readValue;
                while ((readValue = reader.readLine()) != null) {
                    System.out.println(readValue);
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
