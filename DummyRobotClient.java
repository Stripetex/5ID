
package dummyrobotclient;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class DummyRobotClient extends Application {

    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage stage) {

        stage.setTitle("Retarded Robot Client");
        stage.setResizable(false);

        try {

            stage.getIcons().add(new Image(getClass().getResourceAsStream("LogoLestiN.png")));
            FXMLLoader loader = new FXMLLoader(getClass().getResource("RetardedClient.fxml"));
            AnchorPane layout = (AnchorPane) loader.load();
            Scene scene = new Scene(layout);
            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
