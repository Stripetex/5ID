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
        System.out.println(" Comunicazione con il server:  localhost:"+port);
        
	    while (true) { 
                System.out.print("> "); 
                userInput = stdIn.readLine();
                
	        out.println(userInput); 
	        String serverRisposta = in.readLine();
	               
	        // Controllo se ho ricevuto il messaggio di chiusura delle trasmissioni
	        if(serverRisposta.equals("{-[CloseConnectionMessage]-}")){
	            Client.close();
                    System.out.println("Sono state inserite 3 parole.");
                    System.out.println("Server> FINE TRASMISSIONE!");
                    break;
	        }
            System.out.println(serverRisposta);
	    }
    }
}

