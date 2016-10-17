
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
        BufferedReader buf;        PrintStream PT;        StringTokenizer lista;
        
        Socket clientSocket = null;
        try {
            echoServer = new ServerSocket(port); 
            System.out.println("Server creato a porta " + port);
        } catch (IOException e) {
            System.out.println(e);
        }   
        try {
            clientSocket = echoServer.accept(); 
            System.out.println("connesso");
            buf = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PT = new PrintStream(clientSocket.getOutputStream());
            while (true) {
                line = buf.readLine();
                Thread.sleep(1000);
                lista = new StringTokenizer(line);
                if (lista.countTokens()==3) { 
		   PT.println("*[]CHIUDI{}/");
		   Thread.sleep(500);
		   System.out.println("Arresto ");
		   echoServer.close();
		   break;
                }PT.println("ECHO " + line);
            }
        } catch (IOException e) {
            System.out.println(e);
        }
        catch (InterruptedException ie) {
            System.out.println(ie);
        }
    }
}