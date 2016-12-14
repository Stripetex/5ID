/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatbot.gui;

import com.jfoenix.controls.JFXSnackbar;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.Socket;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author fabio
 */
public class ConnectionController implements Initializable {

    @FXML
    AnchorPane rootPane;
    
    @FXML
    private JFXTextField serverDoor;

    @FXML
    private JFXTextField name;

    @FXML
    private JFXTextField serverIp;
    
    @FXML
    private JFXSnackbar snackbar;
    
    private IPAddressValidator ipValidator;
    
    
    @FXML
    void connect(ActionEvent event) throws IOException {
        String ipServer = serverIp.getText();
        String door = serverDoor.getText();
        if(!name.getText().equals("")){
            if(ipValidator.validate(ipServer)){
                try{
                    int serverDoor = Integer.parseInt(door);
                    if(serverDoor<=65535){
                        if(makeConnection(ipServer, serverDoor, name.getText())){
                            Stage s = (Stage)rootPane.getScene().getWindow();
                            s.close();
                        }else{
                           snackbar.show("Non esiste il server "+ipServer+" alla porta "+serverDoor, 1500); 
                        }
                    }else{
                        snackbar.show("La porta deve essere al massimo 65535", 1500);
                    }
                }catch(NumberFormatException ex){
                    snackbar.show("Porta non corretta", 1500);
                }
            }else{
                snackbar.show("IP non corretto", 1500);
            }
        }else{
            snackbar.show("Inserire un nome", 1500);
        }
        
    }  
    
    boolean makeConnection(String ipServer, int port, String name) {
        if(datiServer.getDatiServer().creaConnessione(port, ipServer)){
            try{
                
                FXMLLoader load = new FXMLLoader(getClass().getResource("chat.fxml"));
                Stage secondaryStage = new Stage();
                AnchorPane layout2 = (AnchorPane)load.load();


                Scene scene2 = new Scene(layout2);
                secondaryStage.setTitle("ChatBot");
                scene2.getStylesheets().add(ConnectionController.class.getResource("snackbar.css").toExternalForm());
                //secondaryStage.getIcons().add(new Image(Main.class.getResourceAsStream("/images/icon.png")));
                secondaryStage.setResizable(false);
                secondaryStage.setScene(scene2);

                secondaryStage.show();
            }catch(Exception e){System.out.println(e.getMessage());}
            return true;
        }else{
            return false;
        }    
    }
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        snackbar = new JFXSnackbar(rootPane);
        ipValidator = new IPAddressValidator();
    }
        
    class IPAddressValidator{

        private Pattern pattern;
        private Matcher matcher;

        private static final String IPADDRESS_PATTERN =
                    "^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
                    "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
                    "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
                    "([01]?\\d\\d?|2[0-4]\\d|25[0-5])$";

        public IPAddressValidator(){
              pattern = Pattern.compile(IPADDRESS_PATTERN);
        }

        public boolean validate(final String ip){
              matcher = pattern.matcher(ip);
              return matcher.matches();
        }
    }
}

