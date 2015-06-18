package com.plueschgeddon.server;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.HashSet;

public class UDPSocket implements Runnable {

    private static UDPSocket instance;
    private static DatagramSocket serverSocket;
    private static HashSet<String> connections = new HashSet<>();

    private UDPSocket() {}

    public static UDPSocket getInstance() {
        if (UDPSocket.instance == null) {
            UDPSocket.instance = new UDPSocket();
        }
        return UDPSocket.instance;
    }

    public void run() {
        Helper.println("create UDP-Socket-Listener on " + Server.host + " ...", Helper.ANSI_BLUE);
        try {
            serverSocket = new DatagramSocket(Integer.parseInt(Server.config.get("port")), InetAddress.getByName(Server.config.get("host")));
        } catch (Exception e) {
            e.printStackTrace();
        }
        Helper.println("UDP-Socket-Listener ready", Helper.ANSI_GREEN);

        while (true) {
            byte[] receiveData = new byte[1024];
            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
            try {
                serverSocket.receive(receivePacket);
            } catch (Exception e) {
                e.printStackTrace();
            }
            String sentence = new String(receivePacket.getData(), 0, receivePacket.getLength());
            InetAddress IPAddress = receivePacket.getAddress();
            int port = receivePacket.getPort();
            String connection = IPAddress.toString().replace("/", "") + ":" + port;

            Helper.println("#UDP-Data received from " + connection + " : " + sentence);
            String capitalizedSentence = sentence.toUpperCase();
            String broadcastData = "";
            byte[] bytes;

            if (!broadcastData.equals("") && connections.contains(connection)) {
                for (String recipient : connections) {
                    bytes = broadcastData.getBytes();
                    try {
                        String[] datas = recipient.split(":");
                        DatagramPacket sendPacket = new DatagramPacket(bytes, bytes.length, InetAddress.getByName(datas[0]), Integer.parseInt(datas[1]));
                        serverSocket.send(sendPacket);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                Helper.println("#UDP-Data broadcasted to " + connections.size() + " Clients : " + broadcastData);
            }
        }
    }

}
