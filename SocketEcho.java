/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package socketecho;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author Giovanni
 */
public class SocketEcho {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        server();
    }

    public static void server() {
        ServerSocket echoServer = null;
        //final String PASSWORD = "FINE SOCKET CLIENT";
        final String[] PASSWORD = {"FINE", "SOCKET", "CLIENT"};
        int count = 0;
        String line;
        BufferedReader is;
        PrintStream os;

        Socket clientSocket = null;
        try {
            echoServer = new ServerSocket(9999);
            //System.out.println("vai con: \"netcat 127.0.0.1 9999\"");
        } catch (IOException e) {
            System.out.println(e);
        }
        try {
            clientSocket = echoServer.accept();
            is = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            os = new PrintStream(clientSocket.getOutputStream());
            boolean disconnect = false;
            while (!disconnect) {
                Thread.sleep(1000);
                line = is.readLine();
                if (!line.equals(PASSWORD[count])) {
                    os.println("Server: ECHO " + line);
                    count = 0;
                } else {
                    if(count < 2){
                        os.println("Server: ECHO " + line);
                        count++;
                    } else {
                        os.println("DISCONNECT");
                        echoServer.close();
                        disconnect = true;
                    }
                    
                }

            }

        } catch (IOException e) {
            System.out.println(e);
        } catch (InterruptedException ie) {
            System.out.println(ie);
        } 
    }
}
