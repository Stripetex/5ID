/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatbot.gui;

import chatbot.ChatBot;
import com.jfoenix.controls.JFXSnackbar;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

/**
 *
 * @author fabio
 */
public class ChatController implements Initializable{

    @FXML
    private AnchorPane chatPane;

    @FXML
    private TextField tfText;

    @FXML
    private Button btInvia;
	
    @FXML
    private ScrollPane spChatBox;
	
    @FXML
    private VBox vbChat;
    
    @FXML
    private JFXSnackbar snackbar;
    
    private final String CSS_CLIENT_LABEL = "-fx-padding: 10 20 10 20; -fx-background-radius: 15 0 15 15; -fx-background-color: #0B7B47;";
    private final String CSS_SERVER_LABEL = "-fx-padding: 10 20 10 20; -fx-background-color: #5294B1; -fx-background-radius: 0 15 15 15;";
    private final String CSS_ANCHOR = "-fx-background-color: #100f0f;";
    private final String CSS_TEXT = "-fx-text-fill:#FFFFFF; -fx-background-color:#454242;";
    private final String CSS_VBOX_SCROLL = "-fx-background-color: #2f2d2d;";
	
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        snackbar = new JFXSnackbar(chatPane);
        chatPane.setStyle(this.CSS_ANCHOR);
        tfText.setStyle(this.CSS_TEXT);
        btInvia.setStyle(this.CSS_TEXT);
        spChatBox.setStyle(this.CSS_VBOX_SCROLL);
        vbChat.setStyle(this.CSS_VBOX_SCROLL);
        spChatBox.setHbarPolicy(ScrollBarPolicy.NEVER);
        spChatBox.setVbarPolicy(ScrollBarPolicy.NEVER);
        vbChat.setAlignment(Pos.TOP_RIGHT);
        try {
            risposta();
        } catch (InterruptedException ex) {
            Logger.getLogger(ChatController.class.getName()).log(Level.SEVERE, null, ex);
        }
        vbChat.heightProperty().addListener(new ChangeListener() {

            @Override
            public void changed(ObservableValue observable, Object oldvalue, Object newValue) {
                spChatBox.setVvalue((Double) newValue+20.0);
            }
        });
        
    }
	
    private void addMessServer(String text) throws InterruptedException, IOException{
        for (int i = 0; i < text.length(); i++) {
            if (text.charAt(i) == '*') {
                    String a = text.substring(0, i) + " " + '\n' + " " + text.substring(i + 1);
                    text = a;
            }
        }
        HBox or = new HBox();
        Label l = new Label(text);
        l.setAlignment(Pos.CENTER_LEFT);   
        l.setWrapText(true);
        l.setTextAlignment(TextAlignment.LEFT);
        l.setStyle(this.CSS_SERVER_LABEL);
        or.setMaxSize(spChatBox.getPrefWidth(), vbChat.getMaxHeight());
        or.setStyle("-fx-padding: 5 20 5 7");
        or.setAlignment(Pos.CENTER_LEFT);
        or.getChildren().add(l);
        vbChat.getChildren().add(or);
                    
    } 
    
    public void openError(){
        snackbar.show("La connessione con il server e' stata interrotta", 2500);
    }
    
    public void addMessClient() throws InterruptedException{
        if(!tfText.getText().equals("")){
            HBox or = new HBox();
            String text = tfText.getText();
            tfText.setText("");
            Label l = new Label(text);
            l.setAlignment(Pos.CENTER_RIGHT);
            l.setWrapText(true);
            l.setTextAlignment(TextAlignment.RIGHT);
            l.setStyle(this.CSS_CLIENT_LABEL);
            or.setMaxSize(spChatBox.getPrefWidth(), vbChat.getMaxHeight());
            or.setStyle("-fx-padding: 5 7 5 20");
            or.setAlignment(Pos.CENTER_RIGHT);
            or.getChildren().add(l);
            vbChat.getChildren().add(or);
            datiServer.getDatiServer().scrivi(text);
            risposta();
            
        }
    } 
    
    private void risposta() throws InterruptedException {
        try {
            String fromServer;
            if ((fromServer = datiServer.getDatiServer().leggi())!=null){
                addMessServer(fromServer);
            }
        } catch (IOException ex) {
            openError();
        } 
    }
    
}
