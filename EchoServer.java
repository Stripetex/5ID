/**
 * Server "Echo".
 * @author Cabbia René
 * @version 1.0.1
 */

import java.io.*;
import java.net.*;

public class EchoServer {

    public static void main(String args[]) {

		if(args.length != 1) {
			System.out.println("Uso: EchoServer <porta>");
			System.exit(0);
		}
		
        // Creo socket server e socket client.
        ServerSocket server = null;
        Socket client = null;
		int porta = Integer.parseInt(args[0]);
		
        // Creo flussi di info.
        String linea;
        BufferedReader reader;
        PrintStream writer;

        String[] stop = {"PASSO", "E", "CHIUDO"};
		int count = 0;

        try {

            server = new ServerSocket(porta);
            System.out.println("Creato socket server in localhost:" + porta);

            client = server.accept(); // Attendo la connessione di un client.
            reader = new BufferedReader(new InputStreamReader(client.getInputStream()));
            writer = new PrintStream(client.getOutputStream());

            while (true) {

                linea = reader.readLine();						// Legge il messaggio dal client.
                Thread.sleep(100);

                // Controllo se l'utente ha inserito una delle parole chiave.
                if (linea.toUpperCase().equals(stop[count])) {
                    count++;									// Passo alla parola successiva da considerare.
					if(count == stop.length) {					// Controllo se le parole sono state tutte inserite.
						writer.println("#STOP#");                // Se sì, invio il comando
						server.close();                         // Chiude la connessione.
						break;
					}
                } else {
					count = 0;									// La parola non coincide, porto il contatore a 0, considero la prima parola.
				}

                writer.println("Server> " + linea);				// Invio al client la risposta.
            }

        } catch (IOException e) {
            System.out.println(e);
        } catch (InterruptedException ie) {
            System.out.println(ie);
        }
    }
}