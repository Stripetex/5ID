/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package socketechoclient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author Giovanni
 */
public class SocketEchoClient {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        client();
    }

    public static void client() {
        try {
            Scanner sc = new Scanner(System.in);
            boolean disconnect = false;
            Socket socketClient = new Socket("127.0.0.1", 9999);

            BufferedReader is = new BufferedReader(new InputStreamReader(socketClient.getInputStream()));
            PrintWriter os = new PrintWriter(socketClient.getOutputStream(), true);
            String recived = "";

            while (!disconnect) {
                TimeUnit.MILLISECONDS.sleep(100);
                
                System.out.print("Inserisci messaggio: ");
                String line = sc.nextLine();
                os.println(line);
                recived = is.readLine();
              
                if (recived.equals("DISCONNECT")) {
                    disconnect = true;
                    is.close();
                    os.close();
                    sc.close();
                    socketClient.close();
                    System.out.println("Password corretta: client disconnesso");
                } else {
                    System.out.println(recived);
                }

            }
        } catch (IOException ex) {

        } catch (InterruptedException ex) {
                
        }

    }

}
