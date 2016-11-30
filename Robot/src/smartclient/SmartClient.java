/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package smartclient;

/**
 *
 * @author Marco
 */
import java.io.*;
import java.net.*;

public class SmartClient {
    private static int port = 9999;
    
    public static void main(String[] args) throws IOException {
		
        Socket client = null;
        PrintWriter output = null;
        BufferedReader input = null;
        String keyword = "<gameover>";
        
        String address = "localhost";

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
	
	    System.out.println("Il client sta comunicando sulla porta " + port);
	    while (true) {
	        System.out.print("> ");
			userInput = stdIn.readLine();
			output.println(userInput);
			String serversAnswer = input.readLine();
			//determina la chiusura del socket in caso la parola inserita corrisponda con la keyword determinata
			if(serversAnswer.equals(keyword)){
				client.close();
				System.out.println("Non ho altri motivi per sopravvivere, addio mondo");
				break;
			}
                        String a = "";
			for (int i = 0; i < serversAnswer.length(); i++) {
                            if(serversAnswer.charAt(i)=='*'){
                                a = a + "\n";
                            }else{
                                a = a + serversAnswer.charAt(i);
                            }
                        }
			System.out.println(a);
	    }
    }
}
