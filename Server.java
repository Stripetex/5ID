
import java.lang.Thread;
import java.io.*;
import java.net.*;
import java.util.StringTokenizer;

public class Server {
    public static void main(String args[]) {
        
        ServerSocket echoServer = null;
        String s;
        BufferedReader input;
        PrintStream output;
		int port = 9999;
        Socket clientSocket = null;
		StringTokenizer words;
		String keyword = "<gameover>";
		
        try {
            echoServer = new ServerSocket(port);
            System.out.println("Il server sta ascoltando sulla porta " + port);
			clientSocket = echoServer.accept();
			input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream())); //comunicazione in entrata dal socket client
			output = new PrintStream(clientSocket.getOutputStream()); //comunicazione in uscita verso il socket client
			while(true){
				s = input.readLine();
				Thread.sleep(200);
				words = new StringTokenizer(s);
				
				if(words.countTokens() == 3){ //se rileva tre parole inserita, invia messaggio di chiusura a client
					output.println(keyword);
					echoServer.close();
					break;
				}
				output.println("ECHO " + s);
			}
        } catch (IOException e) {
            System.out.println(e);
        }
        catch (InterruptedException ie) {
            System.out.println(ie);
        }
    }
}
