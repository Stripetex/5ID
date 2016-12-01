package client;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Circle;


public class FXMLDocumentController  { //public class FXMLDocumentController  {


    String fromUser;
    String fromServer;

    

    @FXML
    private TextArea textChat;

    PrintWriter fr;
    boolean primo = true;

    @FXML
    private TextField message;
    
    @FXML
    private ImageView img;
   

    

    public void addTextCl(String out) {
        textChat.setText(textChat.getText() + "\n" + "Cl> " + out);
        message.setText("");
    }

    public void addTextSe(String out) {
        textChat.setText(textChat.getText() + "\n" + "Fede> " + out);
    }

    @FXML
    protected void initialize() throws InterruptedException, IOException {
        
        message.setOnKeyTyped((KeyEvent e) -> {
            char a = e.getCharacter().charAt(0);

            boolean bool = (int) a == 13 || (int) a == 10;

            if (bool) {
                fromUser = message.getText();
                ClientContr.startInit().prnt(fromUser);
                addTextCl(fromUser);

                try {
                    fromServer = ClientContr.startInit().read();
                    addTextSe(fromServer);
                } catch (IOException ex) {
                    Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        });
    }

    

}
