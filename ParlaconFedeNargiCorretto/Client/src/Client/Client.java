/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.Scene;
import javafx.stage.Stage;
/**
 *
 * @author Andrea
 */
public class Client extends Application {
   @Override
    public void start(Stage stage) throws IOException {
        ClientContr.startInit().create("127.0.0.1", 9999);

        stage.setTitle("Parla con FedeNargi");
        try {
            // Load layout from fxml file.
            FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLDocument.fxml"));
            // FXMLLoader loader = new FXMLLoader(SceneBuilderHelloWorld.class.getResource("GUI1.fxml"));

            AnchorPane layout = (AnchorPane) loader.load();
            // show the scene containing the root layout.
            Scene scene = new Scene(layout);
            stage.setScene(scene);
            stage.setResizable(false);
            
            System.out.println("|| Grafica inizializzata ||");

            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Application.launch(args);
    }

}
