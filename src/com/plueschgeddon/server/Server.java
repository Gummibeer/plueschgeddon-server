package com.plueschgeddon.server;

import java.net.DatagramSocket;
import java.net.InetAddress;

public class Server {
    
    public static String[] config;
    private static DatagramSocket serverSocket;

    public static void main(String args[]) throws Exception
    {
        Helper.println("Plüschgeddon-Multiplayer-Server", Helper.ANSI_CYAN);
        Helper.println("load Server-Config", Helper.ANSI_BLUE);
        ConfigLoader configLoader = new ConfigLoader();
        config = configLoader.getAll();
        Helper.println("Server-Config loaded", Helper.ANSI_GREEN);

        Helper.println("start Socket-Listener on " + config[0] + ":" + config[1], Helper.ANSI_BLUE);
        serverSocket = new DatagramSocket(Integer.parseInt(config[1]), InetAddress.getByName(config[0]));
        Helper.println("Socket-Listener started", Helper.ANSI_GREEN);
    }
}
