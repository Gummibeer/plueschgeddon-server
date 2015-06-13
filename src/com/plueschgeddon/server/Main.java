/**
 * UDp-Server: https://systembash.com/a-simple-java-udp-server-and-udp-client/
 * socket server & client: http://de.wikibooks.org/wiki/Java_Standard:_Socket_ServerSocket_(java.net)_UDP_und_TCP_IP
 * details for java socket: http://openbook.rheinwerk-verlag.de/javainsel9/javainsel_21_006.htm
 * colorized console: http://stackoverflow.com/a/5762502/4907524
 * windows cmd socket: http://stackoverflow.com/a/20346530/4907524
 * broadcast to multiple users: http://cs.lmu.edu/~ray/notes/javanetexamples
 * thread sync array: http://stackoverflow.com/questions/21917111/chat-server-how-all-threads-to-send-an-incoming-message
 * database connection: http://www.vogella.com/tutorials/MySQLJava/article.html
 * QuickDB: https://code.google.com/p/quickdb/
 */

package com.plueschgeddon.server;

import java.net.*;
import java.util.HashSet;

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

    public static HashSet<String> connections = new HashSet<>();
    public static String[] config;

    public static void main(String args[]) throws Exception {
        println("Plüschgeddon-Multiplayer-Server", ANSI_CYAN);
        println("load Server-Config", ANSI_BLUE);
        ConfigLoader configLoader = new ConfigLoader();
        config = configLoader.getAll();
        println("Server-Config loaded", ANSI_GREEN);

        println("start Socket-Listener on " + config[0] + ":" + config[1], ANSI_BLUE);
        DatagramSocket serverSocket = new DatagramSocket(Integer.parseInt(config[1]), InetAddress.getByName(config[0]));
        println("Socket-Listener started", ANSI_GREEN);

        while (true) {
            byte[] receiveData = new byte[1024];
            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
            serverSocket.receive(receivePacket);
            String sentence = new String(receivePacket.getData(), 0, receivePacket.getLength());
            InetAddress IPAddress = receivePacket.getAddress();
            int port = receivePacket.getPort();
            String connection = IPAddress.toString().replace("/", "") + ":" + port;

            println("received data from " + IPAddress.toString().replace("/", "") + ":" + port + " -> " + sentence);
            String capitalizedSentence = sentence.toUpperCase();
            String returnData;
            String broadcastData = "";
            byte[] bytes;

            if (capitalizedSentence.startsWith("ENTER")) {
                // TODO: auth user
                connections.add(connection);
                returnData = "true";
            } else if (capitalizedSentence.equals("EXIT")) {
                connections.remove(connection);
                returnData = "true";
            } else {
                if (connections.contains(connection)) {
                    broadcastData = capitalizedSentence;
                    returnData = "true";
                } else {
                    returnData = "false";
                }
            }
            // TODO: the other ingame commands

            if (!returnData.equals("")) {
                bytes = returnData.getBytes();
                DatagramPacket sendPacket = new DatagramPacket(bytes, bytes.length, IPAddress, port);
                serverSocket.send(sendPacket);
                println("sent data to " + connection + " -> " + returnData);
            }

            if (!broadcastData.equals("") && connections.contains(connection)) {
                for (String recipient : connections) {
                    bytes = broadcastData.getBytes();
                    try {
                        String[] datas = recipient.split(":");
                        DatagramPacket sendPacket = new DatagramPacket(bytes, bytes.length, InetAddress.getByName(datas[0]), Integer.parseInt(datas[1]));
                        serverSocket.send(sendPacket);
                        println("sent data to " + datas[0].replace("/", "") + ":" + datas[1] + " -> " + broadcastData);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public static void println(String text) {
        System.out.println(ANSI_WHITE + text + ANSI_RESET);
    }

    public static void println(String text, String color) {
        System.out.println(color + text + ANSI_RESET);
    }
}
