import java.lang.Thread;
import java.io.*;
import java.net.*;
import java.util.*;

public class Server {

    public static void main(String args[]) {

        ServerSocket echoServer = null;
        String s;
        BufferedReader input;
        PrintStream output;
        int port = 9999;
        Socket clientSocket = null;
        ArrayList<String> keyWords = new ArrayList();
        int counter = 0;
        
        keyWords.add("ciao");
        keyWords.add("sono");
        keyWords.add("marco");
        
        try {
            echoServer = new ServerSocket(port);
            System.out.println("Il server sta ascoltando sulla porta " + port);
            clientSocket = echoServer.accept();
            input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream())); //comunicazione in entrata dal socket client
            output = new PrintStream(clientSocket.getOutputStream()); //comunicazione in uscita verso il socket client
            while (true) {
                s = input.readLine();
                Thread.sleep(200);
                if (counter < 3 && s.equals(keyWords.get(counter))) {
                    counter ++;
                } else {
                    counter = 0;
                }
                if (counter == 3) {
                    output.println("Combinazione esatta! Chiudendo il server");
                    echoServer.close();
                    break;
                }
                output.println("ECHO " + s);
            }
        } catch (IOException e) {
            System.out.println(e);
        } catch (InterruptedException ie) {
            System.out.println(ie);
        }
    }
}
