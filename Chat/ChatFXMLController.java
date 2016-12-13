/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientudp;

import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.controls.JFXPopup;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXRippler;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author Andrea
 */
public class ChatFXMLController implements Initializable {

    @FXML
    private ScrollPane scroll;

    @FXML
    private JFXHamburger arrow;
    @FXML
    private AnchorPane AnchorPane;
    @FXML
    private JFXPopup toolbarPopup;
    @FXML
    private JFXRippler optionsRippler;
    @FXML
    private StackPane optionsBurger;
    @FXML
    private JFXTextField txt;
    @FXML
    private VBox vbox;
    @FXML
    private Label exit;
    @FXML
    private Label Username;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Username.setText(communication.getInstance().getUsername());

        Thread RadioToggler = new Thread(() -> {
            try {
                while (true) {
                    String com = communication.getInstance().leggi();
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            //Code to be executed on JavaFX App Thread
                            creaLabel(false, com);
                        }
                    });

                }
            } catch (Exception ex) {

            }
        });
        RadioToggler.start();

        //Listener.start();
        exit.setOnMouseClicked((MouseEvent m) -> {
            ((Node) (m.getSource())).getScene().getWindow().hide();
        });
        vbox.setSpacing(10);
        vbox.heightProperty().addListener(new ChangeListener() {

            @Override
            public void changed(ObservableValue observable, Object oldvalue, Object newValue) {
                scroll.setVvalue((Double) newValue + 20.0);
            }
        });
        arrow.getAnimation().setRate(arrow.getAnimation().getRate() * -1);
        arrow.getAnimation().play();
        toolbarPopup.setPopupContainer(AnchorPane);
        toolbarPopup.setSource(optionsRippler);
        AnchorPane.getChildren().remove(toolbarPopup);
        optionsBurger.setOnMouseClicked((e) -> {
            toolbarPopup.show(JFXPopup.PopupVPosition.TOP, JFXPopup.PopupHPosition.RIGHT, -12, 15);
        });
        
        txt.setOnKeyTyped((KeyEvent e) -> {
            char a = e.getCharacter().charAt(0);
            boolean bool = (int) a == 13 || (int) a == 10;
            if (bool) {
                try {
                    communication.getInstance().Scrivi(txt.getText());//out.println(txt.getText());
                } catch (IOException ex) {
                    Logger.getLogger(ChatFXMLController.class.getName()).log(Level.SEVERE, null, ex);
                }
                //invia al server

                creaLabel(true, txt.getText());
                txt.setText("");

            }

        });
    }

    public void creaLabel(boolean client, String text) {

        JFXDrawer dr = new JFXDrawer();

        Label l = new Label(text);
        dr.getChildren().add(l);
        l.getStyleClass().add("mioMessaggio");

        if (client) {
            HBox b = new HBox();
            b.getChildren().add(dr);
            b.setAlignment(Pos.BOTTOM_RIGHT);
            vbox.getChildren().add(b);
            dr.getStyleClass().add("mioDrawer");
        } else {
            HBox b = new HBox();
            b.getChildren().add(dr);
            b.setAlignment(Pos.BOTTOM_LEFT);
            vbox.getChildren().add(b);
            dr.getStyleClass().add("ServerDrawer");
        } // 

    }
}
