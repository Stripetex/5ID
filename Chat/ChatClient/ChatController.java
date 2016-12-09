/**
 * Chat Controller.
 * @author Lesti | Studio.
 * @version 1.0.1
 */
package chatclient;

import com.jfoenix.controls.JFXDrawer;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class ChatController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField messageTF;

    @FXML
    private Button sendBTN;

    @FXML
    private VBox vboxLY;

    @FXML
    private ScrollPane contentSP;

    @FXML
    private AnchorPane contentPane;

    private String username;
    private String ipAddress;
    private int port;
    
    private DatagramSocket socket;
    private Listener listener;
    private InetAddress ip;
    
    
    public ChatController(String username, String ipAddress, int port) {
        this.username = username;
        this.ipAddress = ipAddress;
        this.port = port;
    }
    
    @FXML
    void initialize() {
        
        try {
            
            socket = new DatagramSocket(8484);
            ip = InetAddress.getByName(ipAddress);
            Thread listenerT = new Thread(new Listener(socket, vboxLY));
            listenerT.start();
            
        } catch(Exception e) {
            e.printStackTrace();
        }
        
        sendBTN.setOnAction((event) -> {
            
            String message = username + "###" + messageTF.getText();
            
            if(!message.equals("")) {
                
                try {
                    
                    byte[] send = message.getBytes();
                    DatagramPacket packet = new DatagramPacket(send, send.length, ip, port);
                    socket.send(packet);
                    
                    show(username, message, vboxLY, "client");
                    
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
    
    public static void show(String username, String message, VBox output, String mode) {
        
        JFXDrawer drawer = new JFXDrawer();

        Label label = new Label(message);
        label.setWrapText(true);
        label.getStyleClass().add("message");

        HBox hb = new HBox();

        if (mode.equals("client")) {
            hb.setAlignment(Pos.BOTTOM_RIGHT);
            drawer.getStyleClass().add("ClientDrawer");
            hb.getChildren().add(drawer);
        } else if(mode.equals("server")) {
            hb.setAlignment(Pos.BOTTOM_LEFT);
            drawer.getStyleClass().add("ServerDrawer");
            hb.getChildren().add(drawer);
        } else {
            hb.setAlignment(Pos.BOTTOM_CENTER);
            drawer.getStyleClass().add("Center");
            hb.getChildren().add(drawer);
        }
        
        double height = output.getHeight() + 50;
        
        drawer.getChildren().add(label);
        output.setPrefHeight(height);
        output.getChildren().add(hb);
    }
}

