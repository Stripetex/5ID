package chatserver;

import java.io.*;
import java.net.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ChatServer {

    private static DatagramSocket server;
    private static InetAddress ipAddress;
    private static String ip;
    private static int port;
    
    public static void main(String[] args) {
    
        if(args.length != 1) {
            System.out.println("Usage: ChatServer <port>");
            System.exit(0);
        }
        
        port = Integer.parseInt(args[0]);
        
        DatagramPacket receivePacket;
        DatagramPacket sendPacket;
        
        try {
            
            server = new DatagramSocket(port);
            
            boolean stop = false;
            while(!stop) {
                
                byte[] receiveData = new byte[1024];
                byte[] sendData = new byte[1024];
                
                receivePacket = new DatagramPacket(receiveData, receiveData.length);
                server.receive(receivePacket);
                String message = new String(receivePacket.getData());
                
                System.out.println("RECEIVED: " + message);
                
                ipAddress = receivePacket.getAddress();
                int port = receivePacket.getPort();
                
                sendData = message.getBytes();
                sendPacket = new DatagramPacket(sendData, sendData.length, ipAddress, port);
                server.send(sendPacket);
            }
            
            server.close();
                        
        } catch (Exception e) {
            System.out.println("Error creating the socket.");
            e.printStackTrace();
        }        
    }
}
