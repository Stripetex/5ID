
import java.lang.Thread;
import java.io.*;
import java.net.*;
import java.util.StringTokenizer;

public class Echo {
    public static void main(String args[]) {
        
        ServerSocket echoServer = null;
        String line;
        BufferedReader is;
        PrintStream os;
		int port = 9999;
        Socket clientSocket = null;
		StringTokenizer parole;
		String keyword = "<gameover>";
		
        try {
            echoServer = new ServerSocket(port);
            System.out.println("Il server sta ascoltando sulla porta" +);
			clientSocket = echoServer.accept();
			input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream())); //comunicazione in entrata dal socket client
			output = new PrintStream(clientSocket.getOutputStream()); //comunicazione in uscita verso il socket client
			while(true){
				s = is.readLine();
				Thread.sleep(200);
				words = new StringTokenizer(line);
				
				if(words.countTokens() == 3){ //se rileva tre parole inserita, invia messaggio di chiusura a client
					os.println(keyword);
					echoServer.close();
					break;
				}
				os.println("ECHO" + line);
			}
        } catch (IOException e) {
            System.out.println(e);
        }
        catch (InterruptedException ie) {
            System.out.println(ie);
        }
    }
}
