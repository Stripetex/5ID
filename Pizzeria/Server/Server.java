package server;

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.*;

/**
 @author Nico
 */
public class Server {

    public final static int PORT = 9999;

    public Server() {
        ExecutorService pool = Executors.newFixedThreadPool(50);
        try {
            ServerSocket server = new ServerSocket(PORT);
            System.out.println("Attivazione Server...");
            System.out.println("Server Attivo");
            System.out.println("Attesa di connessioni sulla porta " + PORT);
            
            while (true) {
                try {
                    Socket socket = server.accept();
                    System.out.println("Nuovo utente connesso da " + socket.getRemoteSocketAddress() +":"+ socket.getPort());
                    Callable<Void> task = new ServerTask(socket);
                    pool.submit(task);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException ex) {
            System.err.println("Errore: Avvio Server non riuscito!");
        }
    }
    
    public static void main(String[] args) {
        new Server();
    }
}
