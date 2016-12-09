package chatclient;

import java.io.*;
import java.net.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ChatClient {

    public static void main(String[] args) {

        try {
            
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            DatagramSocket client = new DatagramSocket(6767);
            
            InetAddress ipAddress = InetAddress.getByName("127.0.0.1");
            
            byte[] receiveData = new byte[1024];
            byte[] sendData = new byte[1024];
            
            DatagramPacket send;
            DatagramPacket receive;
            
            boolean stop = false;
            while(!stop) {
                
                receiveData = new byte[1024];
                sendData = new byte[1024];
                
                String message = reader.readLine();
                sendData = message.getBytes();
                send = new DatagramPacket(sendData, sendData.length, ipAddress, 8888);
                client.send(send);
                
                receive = new DatagramPacket(receiveData, receiveData.length);
                client.receive(receive);
                String data = new String(receive.getData());
                System.out.println("RECEIVED: " + data);
            }           
            
            client.close();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
