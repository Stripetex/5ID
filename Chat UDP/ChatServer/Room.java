package chatserver;

import java.net.*;

public class Room implements Runnable {

    private MulticastSocket server;
    private InetAddress group;
    private int port;
    
    public Room(int port) {
        this.port = port;
    }
    
    @Override
    public void run() {
        
        try {
            
            server = new MulticastSocket(port);
            group = InetAddress.getByName("224.0.0.1");
            server.joinGroup(group);
            
            byte[] receive = new byte[1024];
            byte[] send = new byte[1024];
            
            DatagramPacket toReceive;
            DatagramPacket toSend;
            
            boolean stop = false;
            while(!stop) {
                
                toReceive = new DatagramPacket(receive, receive.length);
                server.receive(toReceive);
                
                String data = new String(toReceive.getData());
                
                if(data.equals("###STOP###")) {
                    stop = true;
                    send = ("ALERT###Server disconnected").getBytes();
                } else {
                    send = data.getBytes();
                }
                
                toSend = new DatagramPacket(send, send.length, group, 8888);
                server.send(toSend);
                
            }
            
            server.close();
            
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}
