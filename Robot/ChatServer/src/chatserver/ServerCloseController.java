package chatserver;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

public class ServerCloseController implements Initializable{
    private int port;
    private final ChatServer server = new ChatServer();
    
    @FXML
    private Button btClose;

    @FXML
    void close(ActionEvent event) throws IOException {
        System.out.println(server);
        server.close();
        System.exit(0);
    }
    

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        port = porta.getInstance().getPort();
        Runnable start = () -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                Logger.getLogger(ServerCloseController.class.getName()).log(Level.SEVERE, null, ex);
            }
                server.setChatServer(port);
            };
        new Thread(start).start();
          
    }

}

