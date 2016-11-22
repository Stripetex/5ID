package robotclient;

import com.jfoenix.controls.*;
import com.jfoenix.controls.JFXPopup.PopupHPosition;
import com.jfoenix.controls.JFXPopup.PopupVPosition;
import java.awt.Font;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
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
 *
 * @author Andrea
 */
public class RobotClientFXMLController {

    public int xServer = 0;
    public int Hscroll = 440;
    public int x = 330;
    public int y = 0;

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
    private JFXRadioButton radio;
    @FXML
    private VBox vbox;
    @FXML
    private Label exit;
    @FXML
    private Label save;

    private int CountRadio;

    @FXML
    protected void initialize() throws InterruptedException, IOException {

        //   scroll.vvalueProperty().bind(vbox.heightProperty());
        exit.setOnMouseClicked((MouseEvent m) -> {
            ((Node) (m.getSource())).getScene().getWindow().hide();
            try {
                communication.getInstance().close();
            } catch (IOException ex) {
                Logger.getLogger(RobotClientFXMLController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        vbox.setSpacing(10);

        vbox.heightProperty().addListener(new ChangeListener() {

            @Override
            public void changed(ObservableValue observable, Object oldvalue, Object newValue) {
                scroll.setVvalue((Double) newValue+20.0);
            }
        });

        CountRadio = 0;

        arrow.getAnimation().setRate(arrow.getAnimation().getRate() * -1);
        arrow.getAnimation().play();

        toolbarPopup.setPopupContainer(AnchorPane);
        toolbarPopup.setSource(optionsRippler);
        AnchorPane.getChildren().remove(toolbarPopup);

        optionsBurger.setOnMouseClicked((e) -> {
            toolbarPopup.show(PopupVPosition.TOP, PopupHPosition.RIGHT, -12, 15);
        });

        txt.setOnKeyTyped((KeyEvent e) -> {
            char a = e.getCharacter().charAt(0);

            boolean bool = (int) a == 13 || (int) a == 10;

            if (bool) {

                communication.getInstance().Scrivi(txt.getText());//out.println(txt.getText());
                //invia al server
                creaLabel(true, txt.getText());
                txt.setText("");

                Thread RadioToggler = new Thread(() -> {
                    try {
                        radio.fire();
                        Thread.sleep(600);
                        radio.fire();
                    } catch (Exception ex) {
                        Logger.getLogger(RobotClientFXMLController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                });
                RadioToggler.start();

                try {
                    //Ricevo dal server
                    String messaggio = communication.getInstance().leggi();//in.readLine();
                    creaLabel(false, messaggio);
                } catch (IOException ex) {
                    Logger.getLogger(RobotClientFXMLController.class.getName()).log(Level.SEVERE, null, ex);
                }

            }

        });

        /*TO-DO LIST 
                
		 Mettere opzioni nel menu a tre punti. popup si chiude quando si clicca su qualcosa
                
         */
    }

    public void creaLabel(boolean client, String text) {

        JFXDrawer dr = new JFXDrawer();
        for (int i = 0; i < text.length(); i++) {
            if (text.charAt(i) == '¶') {
                String a = text.substring(0, i) + " " + '\n' + " " + text.substring(i + 1);
                text = a;
            }
        }

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

        //          System.out.println("label: "+l.getHeight());
        if (client) {
            //	dr.setLayoutY(y);
            //	dr.setLayoutX(lung);
        } else {
            //	dr.setLayoutY(y+5);
            //	dr.setLayoutX(10);
        }
        y += 50;
        if (y > 300) {
            Hscroll += 50;
            vbox.setPrefHeight(Hscroll);
            scroll.setVvalue(scroll.getHmax());
        }
        
    }

    @FXML
    public void RadioPressed() {
        CountRadio++;
        if (CountRadio > 1) {
            CountRadio = 0;
            //Invia il testo al server
            txt.setText("");
            Thread RadioToggler = new Thread(() -> {
                try {
                    Thread.sleep(600);
                    radio.fire();
                } catch (InterruptedException ex) {
                    Logger.getLogger(RobotClientFXMLController.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
            RadioToggler.start();

        }

    }

}
