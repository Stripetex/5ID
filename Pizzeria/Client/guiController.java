package client;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import javafx.scene.input.KeyEvent;
import javafx.fxml.FXML;
import javafx.scene.input.KeyCode;

/**
 * @author Nico
 */
public class guiController {

    @FXML
    private JFXTextArea chatText;

    @FXML
    private JFXButton btnSend;

    @FXML
    private JFXTextField userText;

    ClientController cc = new ClientController();

    @FXML
    void onEnter(KeyEvent e) {  // Premo invio sulla tastiera ed invia il testo scritto
        if (e.getCode() == KeyCode.ENTER) {
            send();
        }
    }

    @FXML
    void send() {
        if (!userText.getText().equals("")) {
            chatText.appendText("\nIO> " + userText.getText());
            cc.sendData(userText.getText());
            userText.setText("");
            chatText.appendText("\nPIZZERIA> " + cc.getData());
        }
    }

    @FXML
    void initialize() {
    }

}
