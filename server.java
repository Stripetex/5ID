
package echoserver;

/**
 * @author Kevin gemolo
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
        BufferedReader buf;
        PrintStream PT;
        StringTokenizer lista;
        
        Socket clientSocket = null;
        try {
            echoServer = new ServerSocket(port); 
            System.out.println("Server creato alla porta " + port);
        } catch (IOException e) {
            System.out.println(e);
        }   
        try {
            clientSocket = echoServer.accept(); 
            System.out.println("Client connesso");
            buf = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));//Riceve codice dal client
            PT = new PrintStream(clientSocket.getOutputStream()); //Invia 
            while (true) {
                line = buf.readLine();
                Thread.sleep(1000);
                lista = new StringTokenizer(line);
                if (lista.countTokens()==3) { 
                   PT.println("*[]STOP{}/");
                   Thread.sleep(500);
                   System.out.println("Arresto server");
                   echoServer.close(); //chiudo il server
                   break;
                }
                 PT.println("ECHO " + line);
            }
        } catch (IOException e) {
            System.out.println(e);
        }
        catch (InterruptedException ie) {
            System.out.println(ie);
        }
    }
}