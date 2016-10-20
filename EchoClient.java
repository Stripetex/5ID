/**
 * Client "Echo".
 * @author Cabbia René
 * @version 1.0.1
 */

import java.io.*;
import java.net.*;

public class EchoClient {

    public static void main(String[] args) throws IOException {

        // Controllo che il numero degli argomenti sia corretto.
        if (args.length != 3) {
            System.out.println("Uso: java EchoClient.java <url> <porta> <nome utente>");
            System.exit(1);
        }

        String utente = args[0];
        String url = args[1];
        int porta = Integer.parseInt(args[2]);

        // Creo il socket per la connessione e i due stream necessari per lo scambio di info.
        Socket connessione = null;
        BufferedReader reader = null;
        PrintWriter writer = null;

        try {
            connessione = new Socket(url, porta);
            reader = new BufferedReader(new InputStreamReader(connessione.getInputStream()));
            writer = new PrintWriter(connessione.getOutputStream(), true);
        } catch (Exception e) {
            System.exit(1);
        }

        System.out.println("Connessione server " + url + ":" + porta);

        // Input dell'utente.
        BufferedReader k = new BufferedReader(new InputStreamReader(System.in));
        String linea;

        while (true) {

            System.out.print(utente + "> ");
            linea = k.readLine();                   // Leggo il messaggio dell'utente.

            writer.println(linea);                  // Lo invio al server.
            String risposta = reader.readLine();    // Leggo la risposta del server.

            // Controllo se il server termina la comunicazione.
            if (risposta.equals("#STOP#")) {
                System.out.println("Connessione chiusa con successo.");
                connessione.close();
                break;
            }
            
            // Scrivo la risposta del server a video.
            System.out.println(risposta);
        }
    }
}