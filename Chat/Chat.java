/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientudp;

import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;

import javafx.stage.Stage;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

import javafx.fxml.FXML;

/**
 *
 * @author Andrea
 */
public class Chat implements Initializable {

    @FXML
    private JFXTextField username;
    @FXML
    private JFXTextField password;

    @FXML
    public void login() throws IOException, NoSuchAlgorithmException {
        communication.getInstance().create("127.0.0.1", 9999);

        //chiedo al server se il login è giusto, se lo è passo al prossimo
        if (communication.getInstance().askLogin(username.getText(), password.getText())) {
            try {
                communication.getInstance().setUsername(username.getText());
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ChatFXML.fxml"));
                Parent root1 = (Parent) fxmlLoader.load();
                Stage stage = new Stage();
                stage.setTitle("Chat");
                stage.setScene(new Scene(root1));
                stage.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }
}
