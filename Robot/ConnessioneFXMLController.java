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
    @FXML
    private Line giu;
    @FXML
    private Line su;
    @FXML
    private Rectangle birra;

    @FXML
    private Circle foam0, foam1, foam2, foam3, foam4, foam5, foam6, foam7, foam8;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Circle[] bolle=new Circle[15]; 
        Random r = new Random();
        for (int i = 0; i < bolle.length; i++) {
            bolle[i]=new Circle(15, Color.rgb(255, 255,255, 0.2));
            bolle[i].setLayoutX(r.nextInt(450));
            bolle[i].setLayoutY(480);
            pane.getChildren().add(10,bolle[i]);
            
        }
        
        
        Circle[] c = {foam0, foam1, foam2, foam3, foam4, foam5, foam6, foam7, foam8};

        Rectangle Versamento = new Rectangle(20, 500, Color.web("#edaf32"));

        pane.getChildren().add(5, Versamento);
        Versamento.setArcHeight(15);
        Versamento.setArcWidth(15);

        PathTransition pathTransitionGiu = new PathTransition();//Versamento giu
        pathTransitionGiu.setDuration(Duration.millis(2500));
        pathTransitionGiu.setPath(giu);
        pathTransitionGiu.setNode(Versamento);
        pathTransitionGiu.setCycleCount(1);
        pathTransitionGiu.setAutoReverse(true);
        pathTransitionGiu.play();

        PathTransition pathTransitionSu = new PathTransition(); //versamento su
        pathTransitionSu.setDuration(Duration.millis(4000));
        pathTransitionSu.setPath(su);
        pathTransitionSu.setNode(Versamento);
        pathTransitionSu.setCycleCount(1);
        pathTransitionSu.setAutoReverse(true);

        final Timeline timeline = new Timeline();   //ingrandisce
        timeline.setCycleCount(1);
        timeline.setAutoReverse(true);
        final KeyValue kv = new KeyValue(birra.heightProperty(), 500,
                Interpolator.EASE_BOTH);
        final KeyFrame kf = new KeyFrame(Duration.millis(3000), kv);
        timeline.getKeyFrames().add(kf);

        final Timeline timelinea = new Timeline();  //sale
        timelinea.setCycleCount(1);
        timelinea.setAutoReverse(true);
        final KeyValue kva = new KeyValue(birra.yProperty(), -380,
                Interpolator.EASE_BOTH);
        final KeyFrame kfa = new KeyFrame(Duration.millis(3000), kva);
        timelinea.getKeyFrames().add(kfa);

        pathTransitionGiu.onFinishedProperty().set((EventHandler<ActionEvent>) (ActionEvent event) -> {
            timeline.play();
            timelinea.play();

            for (int i = 0; i < c.length; i++) {

                final Timeline tm = new Timeline();  //sale
                tm.setCycleCount(1);
                tm.setAutoReverse(true);

                final KeyValue kvFoam = new KeyValue(c[i].centerYProperty(), -380,
                        Interpolator.EASE_BOTH);
                final KeyFrame kfFoam = new KeyFrame(Duration.millis(3000), kvFoam);
                tm.getKeyFrames().add(kfFoam);
                tm.play();

            }
            
            
            
            
            for (int i = 0; i < bolle.length; i++) {    //bolle
                
                final Timeline tm = new Timeline();  
                tm.setCycleCount(Timeline.INDEFINITE);
                tm.setAutoReverse(false);

                final KeyValue kvFoam = new KeyValue(bolle[i].centerYProperty(), -380,
                        Interpolator.EASE_BOTH);
                final KeyFrame kfFoam = new KeyFrame(Duration.millis(r.nextInt(2000)+3000), kvFoam);
                tm.getKeyFrames().add(kfFoam);
                tm.play();
                
            }
            pathTransitionSu.play();

        });
        // TODO
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
