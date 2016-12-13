/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatudp.gui;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXSnackbar;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
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
    
    @FXML
    private JFXTextField txtPas;

    @FXML
    private JFXButton btReg;
    
    private IPAddressValidator ipValidator;
    
    
    @FXML
    void connect(ActionEvent event) throws IOException {
        String ipServer = serverIp.getText();
        String door = serverDoor.getText();
        if(!name.getText().equals("")){
            if(!txtPas.getText().equals("") || !checkPas(txtPas.getText())){
                if(ipValidator.validate(ipServer)){
                    try{
                        int serverDoor = Integer.parseInt(door);
                        if(serverDoor<=65535){
                            if(datiServer.getDatiServer().creaConnessione(serverDoor, serverIp.getText(),name.getText()) && datiServer.getDatiServer().checkConnection()){
                                if(datiServer.getDatiServer().checkRegistration(name.getText(), txtPas.getText())){
                                    createNewStage();
                                    Stage s = (Stage)rootPane.getScene().getWindow();
                                    s.close();
                                }else{
                                    snackbar.show("Non sei registrato", 1500);
                                }
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
                snackbar.show("Inserire una password corretta", 1500);
            }
        }else{
            snackbar.show("Inserire un nome", 1500);
        }
        
    }  
    
    @FXML
    void registrati(ActionEvent event) throws IOException{
        if(datiServer.getDatiServer().creaConnessione(Integer.parseInt(serverDoor.getText()), serverIp.getText(),name.getText())  && datiServer.getDatiServer().checkConnection()){
            if(datiServer.getDatiServer().setRegistration(name.getText(), txtPas.getText())){
                snackbar.show("Ti sei registrato", 1500);
            }else{
                snackbar.show("Qualcosa è andato storto", 1500);
            }
        }
    }
    
    boolean checkPas(String password){
        String validi = "abcdefghijklmnopqrstuvxywzABCDEFGHIJKLMNOPQRSTUVXYWZ1234567890@#";
        for (int i = 0; i < password.length(); i++) {
            for (int j = 0; j < validi.length(); j++) {
                if(password.charAt(i)==validi.charAt(j)) return false;
            }
        }
        return true;
    }
    
    void createNewStage() {
        try{

            FXMLLoader load = new FXMLLoader(getClass().getResource("chat.fxml"));
            Stage secondaryStage = new Stage();
            AnchorPane layout2 = (AnchorPane)load.load();


            Scene scene2 = new Scene(layout2);
            secondaryStage.setTitle("Chat UDP");
            scene2.getStylesheets().add(ConnectionController.class.getResource("snackbar.css").toExternalForm());
            secondaryStage.setResizable(false);
            secondaryStage.setScene(scene2);

            secondaryStage.show();
        }catch(Exception e){System.out.println(e.getMessage());}
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

