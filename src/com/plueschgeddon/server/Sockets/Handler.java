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
        }
    }

    // TODO: confirm message receive
    // TODO: send message to specific clients
    // TODO: send message to all clients
    private void sendMessage(String message) {
        for(Socket receiver : Service.clients) {
            try {
                if(!receiver.isClosed()) {
                    PrintWriter printWriter = new PrintWriter(new OutputStreamWriter(receiver.getOutputStream()));
                    printWriter.print(message);
                    printWriter.flush();
                    Main.println("sent Message: " + message, Main.ANSI_GREEN);
                } else {
                    Service.clients.remove(receiver);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
