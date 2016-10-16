
/**
 * 
 * @author Bove Nicola
 * 
 */

import java.io.*;
import java.net.*;
import java.util.*;

public class Server {

    public static void main(String args[]) {

        BufferedReader reader;
        PrintStream writer;
        String ln;
        
        ServerSocket srin = null;
        Socket cliout = null;
        
        String stop = "FINE DELLA CONNESSIONE";


        int prt = 9999;
        try {

            srin = new ServerSocket(prt);

            cliout = srin.accept(); 
            reader = new BufferedReader(new InputStreamReader(cliout.getInputStream()));
            writer = new PrintStream(cliout.getOutputStream());

            while (true) {

                ln = reader.readLine();                 // Prende testo dell'utente
                Thread.sleep(1000);

                if (ln.equals(stop)) {	\				//Se l'utente scrive: FINE DELLA CONNESSIONE
                    writer.println("CLOSE");                
                    srin.close();                       // Chiudo la connessione
                    break;
                }

                writer.println(ln);         // Risposta del server
            }

        } catch (IOException e) {
            System.out.println(e);
        } catch (InterruptedException ie) {
            System.out.println(ie);
        }
    }
}
