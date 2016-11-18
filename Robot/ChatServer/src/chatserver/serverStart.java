/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatserver;

import java.io.IOException;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 *
 * @author fabio
 */
public class serverStart extends Application {
    
     @Override
    public void start(Stage primaryStage) throws Exception{
        primaryStage.setTitle("Server");

        try {
            // Load layout from fxml file.
            FXMLLoader loader = new FXMLLoader(getClass().getResource("serverGui.fxml"));

            AnchorPane layout = (AnchorPane) loader.load();
            // show the scene containing the root layout.
            Scene scene = new Scene(layout);
            //primaryStage.getIcons().add(new Image(ChatBot.class.getResourceAsStream("/images/icon.png")));
            primaryStage.setResizable(false);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        launch(args);
    }
}
