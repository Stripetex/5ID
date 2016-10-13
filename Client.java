/**
 *
 * @author Giugie Andrea
 */
import java.io.*;
import java.net.*;

public class Client {
    public static void main(String[] args) throws IOException {
		

        Socket Client = null;
        PrintWriter out = null;
        BufferedReader in = null;

        String address = "localhost";
        int port = 9999;

        try {
            Client = new Socket(address, port); //Creo un socket client sulla porta 9999 e creo due buffer
            out = new PrintWriter(Client.getOutputStream(), true); //messaggio da inviare al server
            in = new BufferedReader(new InputStreamReader(Client.getInputStream()));    //messaggio ricevuto dal server
        } catch (Exception e) {
            System.err.println("Errore");
            System.exit(1);
        } 

	    BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
            
	    String userInput;
            System.out.println("Comunicazione con il server alla porta "+port);
	    while (true) { 
                System.out.print("> "); 
                userInput = stdIn.readLine();
                
	        out.println(userInput); 
	        String serverRisposta = in.readLine();
	               
	        if(serverRisposta.equals("<QUIT>")){    //Se quello che ricevo dal server è il messaggio di spegnimento, arresto il socket ed esco dal loop
	            Client.close();
                    System.out.println("Inserita la frase segreta!");
                    break;
	        }
                System.out.println(serverRisposta);
	    }
    }
}
