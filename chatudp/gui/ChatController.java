/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatudp.gui;

import com.jfoenix.controls.JFXSnackbar;
import java.io.IOException;
import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextFlow;

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
        vbChat.heightProperty().addListener(new ChangeListener() {

            @Override
            public void changed(ObservableValue observable, Object oldvalue, Object newValue) {
                spChatBox.setVvalue((Double) newValue+20.0);
            }
        });
        /*new Thread(() -> {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    while(true){
                        try {
                            String mex = datiServer.getDatiServer().leggi();
                            addMessServer(mex);
                        } catch (Exception ex) {
                        }
                    }
                }
            });
        }).start();*/

        /*Task read = new Task<Void>(){
            @Override public Void call() {
                while(true){
                    Platform.runLater(() -> {
                        try {
                            String mex = datiServer.getDatiServer().leggi();
                            addMessServer(mex.trim());
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    });
                }
            }
        };*/
        
        Task read = new Task<Void>(){
            @Override public Void call() {
                while(true){
                    try {
                        String mex = datiServer.getDatiServer().leggi();
                        addMessServer(mex.trim());
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }
        };
        
        new Thread(read).start();
    }
	
    private void addMessServer(String text) throws IOException{
        String nome="", mex="";
        boolean pos = false;
        for (int i = 0; i < text.length(); i++) {
            if(pos==true)mex+=text.charAt(i);
            else{
                if (text.charAt(i) == '#') {
                    pos = true;
                }else{
                    nome+=text.charAt(i);
                }
            }
            
        }
        HBox or = new HBox();
        Random rand = new Random();
        float r = rand.nextFloat();
        float g = rand.nextFloat();
        float b = rand.nextFloat();
        Color randomColor = new Color(r, g, b, .99);
        Text name = new Text(nome);
        name.setFill(randomColor);
        name.setStyle("-fx-font-weight: bold");
        Text mess = new Text("\n"+mex);
        TextFlow t = new TextFlow();
        t.getChildren().addAll(name, mess);
        t.setTextAlignment(TextAlignment.LEFT);
        t.setStyle(this.CSS_SERVER_LABEL);
        or.setMaxSize(spChatBox.getPrefWidth(), vbChat.getMaxHeight());
        or.setStyle("-fx-padding: 5 20 5 7");
        or.setAlignment(Pos.CENTER_LEFT);
        Platform.runLater(() -> {
            or.getChildren().add(t);
            vbChat.getChildren().add(or);
        });
                    
    } 
    
    public void openError(){
        snackbar.show("La connessione con il server e' stata interrotta", 2500);
    }
    
    public void addMessClient() throws InterruptedException, IOException{
        if(!tfText.getText().equals("")){
            HBox or = new HBox();
            Random rand = new Random();
            float r = rand.nextFloat();
            float g = rand.nextFloat();
            float b = rand.nextFloat();
            Color randomColor = new Color(r, g, b, .99);
            Text io = new Text("IO");
            io.setFill(randomColor);
            io.setStyle("-fx-font-weight: bold");
            Text text = new Text("\n"+tfText.getText());
            TextFlow t = new TextFlow();
            t.getChildren().addAll(io, text);
            String mex = tfText.getText();
            tfText.setText("");
            t.setTextAlignment(TextAlignment.RIGHT);
            t.setStyle(this.CSS_CLIENT_LABEL);
            or.setMaxSize(spChatBox.getPrefWidth(), vbChat.getMaxHeight());
            or.setStyle("-fx-padding: 5 7 5 20");
            or.setAlignment(Pos.CENTER_RIGHT);
            or.getChildren().add(t);
            vbChat.getChildren().add(or);
            datiServer.getDatiServer().scrivi("[]"+datiServer.getDatiServer().getNome()+"|"+mex);
            
        }
    } 
    
}
