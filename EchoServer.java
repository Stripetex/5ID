/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package echoserver;

/**
 *
 * @author fabio
 */
import java.io.*;
import java.net.*;
import java.lang.Thread;
import java.util.StringTokenizer;

public class EchoServer {
    public static void main(String args[]) {
        
        ServerSocket echoServer = null;
        String line;
        int port = 8888;
        BufferedReader is;
        PrintStream os;
        StringTokenizer listaParole;
        
        Socket clientSocket = null;
        try {
            echoServer = new ServerSocket(port); //Creo il server alla porta 8888
            System.out.println("Server creato alla porta " + port);
        } catch (IOException e) {
            System.out.println(e);
        }   
        try {
            clientSocket = echoServer.accept(); //Attende la connessione di un client
            System.out.println("Client connesso");
            is = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));//Riceve codice dal client
            os = new PrintStream(clientSocket.getOutputStream()); //Invia codice al client
            while (true) {
                line = is.readLine();
                Thread.sleep(1000);
                listaParole = new StringTokenizer(line); //Suddivido la frase in parole
                if (listaParole.countTokens()==3) {  //Se ricevo tre parole chiudo la connessione
                   os.println("*[]STOP{}/");
                   System.out.println("Chiusura server in corso..");
                   Thread.sleep(500);
                   System.out.println("Server spento");
                   echoServer.close(); //Spengo il server
                   break;
                }
                 os.println("ECHO " + line); //Scrivo nel client
            }
        } catch (IOException e) {
            System.out.println(e);
        }
        catch (InterruptedException ie) {
            System.out.println(ie);
        }
    }
}