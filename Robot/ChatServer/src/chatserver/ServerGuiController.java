package chatserver;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class ServerGuiController {

    @FXML
    private TextField tfPort;

    @FXML
    private Button btStart;
    
    @FXML
    private AnchorPane pane;

    @FXML
    void startServer(ActionEvent event) throws IOException {
        try{
            porta.getInstance().setPort(Integer.parseInt(tfPort.getText()));
            FXMLLoader load = new FXMLLoader(getClass().getResource("serverClose.fxml"));
            Stage secondaryStage = new Stage();
            AnchorPane layout = (AnchorPane)load.load();


            Scene scene2 = new Scene(layout);
            secondaryStage.setTitle("Server");
            //secondaryStage.getIcons().add(new Image(Main.class.getResourceAsStream("/images/icon.png")));
            secondaryStage.setResizable(false);
            secondaryStage.setScene(scene2);

            secondaryStage.show();
        }catch(Exception e){
            
        }
        ((Stage)pane.getScene().getWindow()).close();
        
    }

}

