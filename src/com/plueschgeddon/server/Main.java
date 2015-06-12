/**
 * socket server & client: http://de.wikibooks.org/wiki/Java_Standard:_Socket_ServerSocket_(java.net)_UDP_und_TCP_IP
 * details for java socket: http://openbook.rheinwerk-verlag.de/javainsel9/javainsel_21_006.htm
 * colorized console: http://stackoverflow.com/a/5762502/4907524
 * windows cmd socket: http://stackoverflow.com/a/20346530/4907524
 * broadcast to multiple users: http://cs.lmu.edu/~ray/notes/javanetexamples
 * thread sync array: http://stackoverflow.com/questions/21917111/chat-server-how-all-threads-to-send-an-incoming-message
 */

package com.plueschgeddon.server;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.io.*;
import java.net.*;

import com.plueschgeddon.server.Sockets.Service;

public class Main {

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";

    public static void main(String[] args) throws IOException {
        println("Plueschgeddon-Multiplayer-Server", ANSI_CYAN);
        println("");
        println("initialize Server-Config", ANSI_BLUE);
        ConfigLoader configLoader = new ConfigLoader();
        String[] config = configLoader.getAll();
        println("Server-Config loaded", ANSI_GREEN);
        println("# IP: " + config[0]);
        println("# Port: " + config[1]);
        println("");

        final ExecutorService pool;
        final ServerSocket serverSocket;
        int port = Integer.parseInt(config[1]);
        String var = "C";
        String zusatz;
        if (args.length > 0)
            var = args[0].toUpperCase();
        if (var.equals("C")) {
            pool = Executors.newCachedThreadPool();
            zusatz = "CachedThreadPool";
        } else {
            int poolSize = 20;
            pool = Executors.newFixedThreadPool(poolSize);
            zusatz = "poolsize=" + poolSize;
        }
        serverSocket = new ServerSocket(port, 100, InetAddress.getByName(config[0]));
        Thread t1 = new Thread(new Service(pool, serverSocket));
        println("start Socket-Service: " + zusatz, ANSI_BLUE);
        println("Thread: " + Thread.currentThread());
        t1.start();
        Runtime.getRuntime().addShutdownHook(
                new Thread() {
                    public void run() {
                        println("Strg+C, pool.shutdown");
                        pool.shutdown();
                        try {
                            pool.awaitTermination(4L, TimeUnit.SECONDS);
                            if (!serverSocket.isClosed()) {
                                println("close Socket-Service", ANSI_RED);
                                serverSocket.close();
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (InterruptedException ei) {
                            ei.printStackTrace();
                        }
                    }
                }
        );
    }

    public static void println(String text) {
        System.out.println(ANSI_WHITE + text + ANSI_RESET);
    }

    public static void println(String text, String color) {
        System.out.println(color + text + ANSI_RESET);
    }
}
