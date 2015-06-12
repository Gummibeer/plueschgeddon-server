/**
 * UDp-Server: https://systembash.com/a-simple-java-udp-server-and-udp-client/
 * socket server & client: http://de.wikibooks.org/wiki/Java_Standard:_Socket_ServerSocket_(java.net)_UDP_und_TCP_IP
 * details for java socket: http://openbook.rheinwerk-verlag.de/javainsel9/javainsel_21_006.htm
 * colorized console: http://stackoverflow.com/a/5762502/4907524
 * windows cmd socket: http://stackoverflow.com/a/20346530/4907524
 * broadcast to multiple users: http://cs.lmu.edu/~ray/notes/javanetexamples
 * thread sync array: http://stackoverflow.com/questions/21917111/chat-server-how-all-threads-to-send-an-incoming-message
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

    public static void main(String args[]) throws Exception {
        println("Plüschgeddon-Multiplayer-Server", ANSI_CYAN);
        ConfigLoader configLoader = new ConfigLoader();
        String[] config = configLoader.getAll();
        println("loaded Server-Config", ANSI_GREEN);

        println("start Socket-Listener on " + config[0] + ":" + config[1], ANSI_BLUE);
        DatagramSocket serverSocket = new DatagramSocket(Integer.parseInt(config[1]), InetAddress.getByName(config[0]));
        byte[] receiveData = new byte[1024];
        byte[] sendData = new byte[1024];
        while (true) {
            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
            serverSocket.receive(receivePacket);
            String sentence = new String(receivePacket.getData(), 0, receivePacket.getLength());
            InetAddress IPAddress = receivePacket.getAddress();
            int port = receivePacket.getPort();
            println("received data from " + IPAddress.toString().replace("/", "") + ":" + port + " -> " + sentence);
            String capitalizedSentence = sentence.toUpperCase();
            sendData = capitalizedSentence.getBytes();
            connections.add(IPAddress.toString().replace("/", "") + ":" + port);
            for(String recipient : connections) {
                try {
                    String[] datas = recipient.split(":");
                    DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, InetAddress.getByName(datas[0]), Integer.parseInt(datas[1]));
                    serverSocket.send(sendPacket);
                    println("sent data to " + datas[0].replace("/", "") + ":" + datas[1] + " -> " + capitalizedSentence);
                } catch(Exception e) {
                    e.printStackTrace();
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
