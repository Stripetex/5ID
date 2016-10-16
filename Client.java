
import java.io.*;
import java.net.*;

public class Client {

    public static void main(String[] args) throws IOException {

        Socket Client = null;
        PrintWriter output = null;
        BufferedReader input = null;

        String indirizzo = "localhost";
        int port = 9999;

        try {
            Client = new Socket(indirizzo, port); //Creo il socket 
            output = new PrintWriter(Client.getOutputStream(), true); //messaggio da spedire
            input = new BufferedReader(new InputStreamReader(Client.getInputStream()));//messaggio ricevuto 
        } catch (Exception e) {
            System.err.println("Errore");
            System.exit(1);
        }

        BufferedReader lettore = new BufferedReader(new InputStreamReader(System.in));

        String scrivi;
        System.out.println("Comunicazione con il server alla porta " + port);
        
        while (true) {

            scrivi = lettore.readLine();

            output.println(scrivi);
            String risposta = input.readLine();

            if (risposta.equals("$chiudi$")) {//Se quello che ricevo dal server è il messaggio di chiusura, lo arresto 
                Client.close();
                System.out.println("chiusura");
                break;
            }
            System.out.println(risposta);
        }
    }
}