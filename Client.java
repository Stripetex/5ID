
/**
 *
 * @author Bove Nicola
 *  
 */

import java.io.*;
import java.net.*;
import java.util.*;

public class Client {

    public static void main(String[] args) throws IOException {
        // Assumo a 3 variabili gli argomenti relativi all'indirizzo, utente e porta
        String url = args[1];                       
        String ut = args[0];
        int prt = Integer.parseInt(args[2]);

        
        Socket conn = null;
        BufferedReader read = null;
        PrintWriter wrt = null;

        try {
            conn = new Socket(url, prt);
            read = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            wrt = new PrintWriter(conn.getOutputStream(), true);
        } catch (Exception e) {
            System.exit(1);
        } 

                
        String ln;
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));

        while (true) {

            System.out.print(ut + "> ");            // 'Utente>'
            ln = bf.readLine();                   // Lettura messaggio utente
            wrt.println(ln);                  
            String ris = read.readLine();    

            if (ris.equals("#CLOSE")) {
                conn.close();
                break;
            }
            System.out.println(ris);        // Risposta 
        }
    }
}