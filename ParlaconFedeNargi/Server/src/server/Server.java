package server;

import java.net.*;
import java.io.*;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class Server {

    private static final Executor e = Executors.newFixedThreadPool(100);
    private static ServerSocket serverSocket = null;
    private static Socket clientSocket = null;
    private int portNumber;

    public Server(int portNumber) {
        this.portNumber = portNumber;
        init();
    }

    public static void main(String[] args) {
        Server serv = new Server(9999);
    }

    public void init() {

        try {
            serverSocket = new ServerSocket(portNumber);
           
        } catch (IOException e) {

        }
        while (true) {
            try {

                clientSocket = serverSocket.accept();
                e.execute(new chatThread(clientSocket));
                System.out.println("Client connesso");
            } catch (IOException e) {
                System.out.println(e);
            }
        }
    }

    public void close() throws IOException {
        serverSocket.close();
    }
}

class chatThread implements Runnable {

    private Socket socCl = null;
    private PrintWriter out = null;
    private BufferedReader in = null;
    private Protocol cp = null;

    public chatThread(Socket clientSocket) {
        this.socCl = clientSocket;
    }

    public void run() {

        try {
            out = new PrintWriter(socCl.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socCl.getInputStream()));
            cp = new Protocol();
            String inputLine, outputLine;


            // Initiate conversation with client
            

            while ((inputLine = in.readLine()) != "bye.") {
                outputLine = cp.processInput(inputLine);
                
                out.println(outputLine);
                if (outputLine.equals("bye.")) {
                   

                    break;
                }
            }
            out.close();
            in.close();
            socCl.close();
        } catch (Exception e) {

        } finally {
            System.out.println("Client disconnesso");
        }
    }
}
