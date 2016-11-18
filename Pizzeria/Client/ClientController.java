package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Nico
 */
public class ClientController {

    private final String serverAddress = "127.0.0.1";
    private final static int PORT = 9999;
    private Socket socket;

    private BufferedReader input;
    private PrintStream output;
    private boolean ACTIVE = true;

    public ClientController() {
        setConnection();
    }

    public void setConnection() {
        try {
            socket = new Socket(serverAddress, PORT);   //Crea la connessione con il server
            output = new PrintStream(socket.getOutputStream());
            input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String getData() {
        String data = "";
        try {
            String s = input.readLine();
            data = s.replaceAll("-l", "\n");
            while (ACTIVE) {
                if (data.equalsIgnoreCase("COMMAND x0 - QUIT")) {
                    ACTIVE = false;
                    return ("- Fine Collegamento -");
                } else {
                    return (data);
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
        return data;
    }

    public void sendData(String data) {
        if (ACTIVE) {
            output.println(data);
        }
    }

}
