package chatclient;

import java.net.*;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class Controller {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField usernameTF;

    @FXML
    private PasswordField passwordPF;

    @FXML
    private TextField ipTF;

    @FXML
    private TextField portTF;

    @FXML
    private Button signupBTN;

    @FXML
    private Button signinBTN;
    
    @FXML
    private Label consoleL;

    private Socket socket;
    private String ip;
    private int port;

    DatagramSocket client;
    DatagramPacket received;
    DatagramPacket sent;
    
    InetAddress ipAddress;
    
    byte[] receive;
    byte[] send;
    
    @FXML
    void initialize() {
        
        receive = new byte[1024];
        send = new byte[1024];
        
        signupBTN.setOnAction((event) -> {
            
            if(!usernameTF.getText().equals("") && !ipTF.getText().equals("") && !portTF.getText().equals("")) {
                
                String username = usernameTF.getText();
                String password = passwordPF.getText();
                ip = ipTF.getText();
                port = Integer.parseInt(portTF.getText());
                
                try {
                    
                    client = new DatagramSocket(8888);
                    ipAddress = InetAddress.getByName(ip);
                    
                    send = ("SIGNUP:::" + username + "&&&" + password).getBytes();
                    sent = new DatagramPacket(send, send.length, ipAddress, port);
                    client.send(sent);
                    
                    receive = new byte[1024];
                    received = new DatagramPacket(receive, receive.length);
                    client.receive(received);
                    
                    String logged = new String(received.getData());
                    consoleL.setText(logged.charAt(0) != '#' ? "Successfully signed up!" : logged);
                    
                } catch(Exception e) {
                    System.out.println("Error estabilishing connection to socket.");
                    e.printStackTrace();
                }
                
            } else {
                consoleL.setText("Error input data.");
            }
        });
        
        signinBTN.setOnAction((event) -> {
            
            if(!usernameTF.getText().equals("") && !ipTF.getText().equals("") && !portTF.getText().equals("")) {
                
                String username = usernameTF.getText();
                String password = passwordPF.getText();
                ip = ipTF.getText();
                port = Integer.parseInt(portTF.getText());
                
                try {
                    
                    client = new DatagramSocket(8888);
                    ipAddress = InetAddress.getByName(ip);
                    
                    send = ("SIGNIN:::" + username + "&&&" + password).getBytes();
                    sent = new DatagramPacket(send, send.length, ipAddress, port);
                    client.send(sent);
                    
                    receive = new byte[1024];
                    received = new DatagramPacket(receive, receive.length);
                    client.receive(received);
                    
                    String logged = new String(received.getData());
                    if(logged.charAt(0) != '#') {
                        
                        ChatController controller = new ChatController(username, ip, port);
                        ChatClient.setScene("ChatGUI.fxml");
                        
                    } else {
                        consoleL.setText("Error during login: " + logged);
                    }
                    
                } catch(Exception e) {
                    System.out.println("Error estabilishing connection to socket.");
                    e.printStackTrace();
                }
                
            } else {
                consoleL.setText("Error input data.");
            }
        });
    }
}
