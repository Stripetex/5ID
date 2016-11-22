package robotclient;

import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.PathTransition;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * FXML Controller class
 *
 * @author Andrea
 */
public class ConnessioneFXMLController implements Initializable {

    @FXML
    private AnchorPane pane;
    @FXML
    private JFXTextField hostname;
    @FXML
    private JFXTextField port;
   

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    pane.setStyle( "-fx-background-image: url('file:vegeta.gif'); -fx-background-size: 700 , 500 ;");
    }

    public void change() throws IOException {

        if ((!hostname.getText().isEmpty()) && (!port.getText().isEmpty())) {

            String address = hostname.getText();
            int porta = Integer.parseInt(port.getText());
            communication.getInstance().create(address, porta);

            System.out.println("Comunicazione con il server alla porta " + porta);
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("RobotClientFXML.fxml"));
            Parent root = loader.load();

            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();

        } else {
            //Metti un toast che lo dice
            System.out.println("Non è stato possibile connettersi");
        }

    }
}
