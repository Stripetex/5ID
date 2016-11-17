/**
 * GUI Controller.
 * @author Lesti | Studio.
 * @version 1.0.1
 */
package retardedrobotclient;

import com.jfoenix.controls.JFXDrawer;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class Controller {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button connectBTN;

    @FXML
    private TextField usernameTF;

    @FXML
    private TextField messageTF;

    @FXML
    private TextField portTF;

    @FXML
    private Button sendBTN;

    @FXML
    private VBox vboxLY;

    @FXML
    private TextField ipTF;

    @FXML
    private ScrollPane contentSP;

    @FXML
    private Pane connectPane;

    @FXML
    private AnchorPane contentPane;

    private Socket socket;
    private String username;
    private String ip;
    private int port;

    private BufferedReader in;
    private PrintWriter out;

    @FXML
    void initialize() {

        sendBTN.setOnAction((event) -> {
            String message = messageTF.getText();
            messageTF.setText("");
            if (!message.equals("")) {
                show(message);
            }
        });

        connectBTN.setOnAction((event) -> {

            String user = usernameTF.getText();
            String address = ipTF.getText();
            int portS = Integer.parseInt(portTF.getText());

            if (!user.equals("") && !address.equals("")) {
                connect(user, address, portS);
            }
        });
    }

    private void show(String message) {

        try {

            draw(message, true);
            out.println(message);
            String reply = in.readLine();
            draw(reply, false);

        } catch (Exception e) {
            System.out.println("Error during sending message: \"" + message + "\".");
        }
    }

    private void draw(String message, boolean client) {

        JFXDrawer drawer = new JFXDrawer();

        Label label = new Label(message);
        label.setWrapText(true);
        label.getStyleClass().add("message");

        HBox hb = new HBox();

        if (client) {
            hb.setAlignment(Pos.BOTTOM_RIGHT);
            drawer.getStyleClass().add("ClientDrawer");
            hb.getChildren().add(drawer);
        } else {
            hb.setAlignment(Pos.BOTTOM_LEFT);
            drawer.getStyleClass().add("ServerDrawer");
            hb.getChildren().add(drawer);
        }
        
        double height = vboxLY.getHeight() + 50;
        
        drawer.getChildren().add(label);
        contentPane.setPrefHeight(height);
        vboxLY.setPrefHeight(height);
        vboxLY.getChildren().add(hb);
    }

    private void connect(String user, String address, int portS) {

        try {

            if (socket != null) {
                disconnect();
            }

            username = user;
            ip = address;
            port = portS;
            socket = new Socket(ip, port);

            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);

            messageTF.setDisable(false);
            sendBTN.setDisable(false);
            vboxLY.setDisable(false);

            out.println(username);

        } catch (Exception e) {
            System.out.println("Cannot connect to " + ip + ":" + port + ".");
        }
    }

    private void disconnect() {

        username = "";
        ip = "";
        port = 0;
        socket = null;

        in = null;
        out = null;

        vboxLY.getChildren().clear();
        
        messageTF.setDisable(true);
        sendBTN.setDisable(true);
        vboxLY.setDisable(true);
    }
}
