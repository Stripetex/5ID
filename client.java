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
            System.exit(1);
        } catch (IOException e) {
            System.exit(1);
        }
        BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
        String userInput;
        System.out.println("Comunicazione con la porta " + port);
        while (true) { 
            userInput = stdIn.readLine();
            out.println(userInput); 
            String serverAnswer = in.readLine();            
            if(serverAnswer.equals("*[]CHIUDI{}/")){ 
                Client.close();
                break;
            }
            System.out.println(serverAnswer);
        }
    }
}