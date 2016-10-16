import java.io.*;
import java.net.*;

public class Client {
    public static void main(String[] args) throws IOException {
		
        Socket client = null;
        PrintWriter out = null;
        BufferedReader in = null;
		String keyword = "<gameover>";
        
        String address = "localhost";
        int port = 9999;

        try {
            client = new Socket(address, port); //creazione socket client su porta 9999
            output = new PrintWriter(client.getOutputStream(), true); //comunicazione in uscita con il server
            input = new BufferedReader(new InputStreamReader(client.getInputStream())); //comunicazione in entrata dal server
        } catch (IOException e) {
            System.err.println("Cannot reach the server");
            System.exit(1);
        }

	    BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
	    String userInput;
	
	    System.out.println("Il client sta comunicando sulla porta" + port);
	    while (true) {
	        System.out.print("> ");
			userInput = stdIn.readLine();
			output.println(userInput);
			String serversAnswer = input.readLine();
			//determina la chiusura del socket in caso la parola inserita corrisponda con la keyword determinata
			if(serversAnswer.equals(keyword)){
				client.close();
				System.out.println("Non altri motivi per sopravvivere, addio mondo");
				break;
			}
			
			System.out.println(serversAnswer);
	    }
    }
}
