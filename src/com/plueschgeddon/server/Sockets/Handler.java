package com.plueschgeddon.server.Sockets;

import java.io.*;
import java.net.*;
import java.util.concurrent.ExecutorService;

import com.plueschgeddon.server.Main;

public class Handler implements Runnable {
    private final Socket client;
    private final ServerSocket serverSocket;

    Handler(ServerSocket serverSocket, Socket client) {
        this.client = client;
        this.serverSocket = serverSocket;
    }

    public void run() {
        try {
            Main.println("run Handler", Main.ANSI_BLUE);
            Main.println("Thread: " + Thread.currentThread());
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(client.getInputStream()));
            char[] buffer = new char[100];
            int characterCount = bufferedReader.read(buffer, 0, 100);
            String message = new String(buffer, 0, characterCount);
            Main.println("received Message: " + message, Main.ANSI_GREEN);
            sendMessage(message);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (!client.isClosed()) {
                Main.println("close Handler", Main.ANSI_RED);
                try {
                    client.close();
                    Service.clients.remove(client);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void sendMessage(String message) {
        System.out.println(Service.clients);
        for(Socket receiver : Service.clients) {
            try {
                PrintWriter printWriter = new PrintWriter(new OutputStreamWriter(receiver.getOutputStream()));
                printWriter.print(message);
                printWriter.flush();
                Main.println("sent Message: " + message, Main.ANSI_GREEN);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
