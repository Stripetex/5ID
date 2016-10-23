

/**
 *
 * @author fabio
 */
import java.io.*;
import java.net.*;
import java.lang.Thread;

public class EchoServer {
    public static void main(String args[]) {

        ServerSocket echoServer = null;
        String line;
        int port = 8888;
        BufferedReader is;
        PrintStream os;
		  String[] paroleChiusura = {"chiudi","il","server"};
		  int contParoleChiusura = 0;

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
            while (contParoleChiusura<3) {//Finchè le parole rilevate sono minori di 3
					line = is.readLine();
					Thread.sleep(1000);
					if (line.equals(paroleChiusura[contParoleChiusura])) {  //Se ricevo la parola in ordine
						contParoleChiusura++; //aumento il contatore per controllare la parola successiva
					}else {contParoleChiusura=0;}
					os.println("ECHO " + line); //Scrivo nel client
            }
				os.println("*[]STOP{}/");
				System.out.println("Chiusura server in corso..");
				Thread.sleep(500);
				System.out.println("Server spento");
				echoServer.close(); //Spengo il server
        } catch (IOException e) {
            System.out.println(e);
        }
        catch (InterruptedException ie) {
            System.out.println(ie);
        }
    }
}