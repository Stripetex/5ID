package chatclient;

import java.net.*;
import javafx.scene.layout.*;

public class Listener implements Runnable {

    private DatagramSocket client;
    private VBox vbox;
    
    public Listener(DatagramSocket socket, VBox vbox) {
        this.client = socket;
        this.vbox = vbox;
    }
    
    @Override
    public void run() {
        
        try {
            
            while(true) {
            
                byte[] receive = new byte[1024];
                DatagramPacket toReceive = new DatagramPacket(receive, receive.length);
                client.receive(toReceive);

                String message = new String(toReceive.getData());
                String[] data = message.split("###");

                ChatController.show(data[0], data[1], vbox, data[0].equals("ALERT") ? "" : "server");
            }
            
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}
