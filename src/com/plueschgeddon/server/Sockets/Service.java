package com.plueschgeddon.server.Sockets;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;
import java.io.*;
import java.net.*;

import com.plueschgeddon.server.Main;

public class Service implements Runnable {
    private final ServerSocket serverSocket;
    private final ExecutorService pool;
    public static ArrayList<Socket> clients = new ArrayList<Socket>();

    public Service(ExecutorService pool, ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
        this.pool = pool;
    }

    public void run() {
        try {
            while (true) {
                Socket cs = serverSocket.accept();
                Main.println("new Connection", Main.ANSI_GREEN);
                clients.add(cs);
                pool.execute(new Handler(serverSocket, cs));
            }
        } catch (IOException ex) {
            Main.println("interrupt Socket-Service", Main.ANSI_RED);
        } finally {
            Main.println("end Socket-Service (pool.shutdown)", Main.ANSI_RED);
            pool.shutdown();
            try {
                pool.awaitTermination(4L, TimeUnit.SECONDS);
                if (!serverSocket.isClosed()) {
                    Main.println("end Socket-Service: close Server-Socket", Main.ANSI_RED);
                    serverSocket.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException ei) {
                ei.printStackTrace();
            }
        }
    }
}
