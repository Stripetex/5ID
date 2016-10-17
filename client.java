package echoclient;

/**
 *
 * @author kevin gemolo
 */
import java.io.*;
import java.net.*;

public class EchoClient{
    public static void main(String[] args) throws IOException {

        Socket Client = null;
        PrintWriter out = null;
        BufferedReader in = null;        
        String address = "localhost";
        int port = 8888;
        try {
            Client = new Socket(address, port); 
            out = new PrintWriter(Client.getOutputStream(), true); 
            in = new BufferedReader(new InputStreamReader(Client.getInputStream())); 
        } catch (UnknownHostException e) {
            System.err.println("Don't know about host: localhost.");
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for "
                               + "the connection to: localhost.");
            System.exit(1);
        }
        BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
        String userInput;
        System.out.println("Comunicazione aperta alla porta " + port);
        while (true) { 
            userInput = stdIn.readLine();
            out.println(userInput); 
            String serverAnswer = in.readLine();
            
            if(serverAnswer.equals("*[]STOP{}/")){ //chiudo la connessione
                Client.close();
                System.out.println("Disconnesso");
                break;
            }
            System.out.println(serverAnswer);
        }
    }
}