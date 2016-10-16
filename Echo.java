
import java.io.*;
import java.net.*;
import java.lang.Thread;

public class Echo {

    public static void main(String args[]) {

        ServerSocket echoServer = null;
        String line;
        BufferedReader is;
        PrintStream os;
        String a;

        Socket clientSocket = null;
        try {
            echoServer = new ServerSocket(9999); // creo il socket 
        } catch (IOException e) {
            System.out.println(e);
        }
        try {
            clientSocket = echoServer.accept();// stabilisce la comunicazione
            is = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            os = new PrintStream(clientSocket.getOutputStream());
            a = "chiudo la comunicazione"; // frase che una volta immessa dal client provoca la chiusura
            while (true) {
                line = is.readLine();
                Thread.sleep(1000);
                if (line.toLowerCase().equals(a)) { // controllo se la stringa che mi manda il client è uguale a quella di chiusura
                    System.out.println("Sto chiudendo la comunicazione");
                    os.println("$chiudi$");
                    echoServer.close();
                }
                os.println("ECHO " + line);
            }
        } catch (IOException e) {
            System.out.println(e);
        } catch (InterruptedException ie) {
            System.out.println(ie);
        }
    }
}
