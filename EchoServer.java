/**
 * Server "Echo".
 * @author Cabbia René
 * @version 1.0.1
 */

import java.io.*;
import java.net.*;

public class EchoServer {

    public static void main(String args[]) {

        // Creo socket server e socket client.
        ServerSocket server = null;
        Socket client = null;

        // Creo flussi di info.
        String linea;
        BufferedReader reader;
        PrintStream writer;

        int porta = 9999;

        String stop = "PASSO E CHIUDO";

        try {

            server = new ServerSocket(porta);
            System.out.println("Creato socket server in localhost:" + porta);

            client = server.accept(); // Attendo la connessione di un client.
            reader = new BufferedReader(new InputStreamReader(client.getInputStream()));
            writer = new PrintStream(client.getOutputStream());

            while (true) {

                linea = reader.readLine();                  // Legge il messaggio dal client.
                Thread.sleep(100);

                // Controllo se l'utente vuole chiudere la connessione.
                if (linea.toUpperCase().equals(stop)) {
                    writer.println("#STOP");                // Se sì, invio il comando
                    server.close();                         // Chiude la connessione.
                    break;
                }

                writer.println("Server> " + linea);         // Invio al client la risposta.
            }

        } catch (IOException e) {
            System.out.println(e);
        } catch (InterruptedException ie) {
            System.out.println(ie);
        }
    }
}
