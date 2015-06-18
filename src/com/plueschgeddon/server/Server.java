package com.plueschgeddon.server;

import java.util.HashMap;
import java.util.Map;

public class Server {

    public static Map<String, String> config = new HashMap<>();
    public static String host;

    public static void main(String args[]) {
        try {
            Helper.println("Plüschgeddon-Multiplayer-Server", Helper.ANSI_CYAN);

            setConfig();

            Thread udpThread = new Thread(UDPSocket.getInstance());
            udpThread.start();
            Helper.println("test 1 2 3");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void setConfig() throws Exception {
        Helper.println("load Server-Config", Helper.ANSI_BLUE);
        ConfigLoader configLoader = ConfigLoader.getInstance();
        config = configLoader.getAll();
        Helper.println("Server-Config loaded", Helper.ANSI_GREEN);
        host = config.get("host") + ":" + config.get("port");
    }
}
