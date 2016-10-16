/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package echoclient;

/**
 *
 * @author fabio
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
            Client = new Socket(address, port); //Connetto client al server all'indirizzo e alla porta indicata
            out = new PrintWriter(Client.getOutputStream(), true); //Codice inviato al server
            in = new BufferedReader(new InputStreamReader(Client.getInputStream())); //Codice ricevuto dal server
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
            
            if(serverAnswer.equals("*[]STOP{}/")){ //Se ricevo come risposta *[]STOP{}/ dal server chiudo la connessione
                Client.close();
                System.out.println("Disconnesso");
                break;
            }
            System.out.println(serverAnswer);
        }
    }
}
