package chatserver;

import java.net.*;
import java.io.*;
import java.sql.*;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import org.relique.jdbc.csv.CsvDriver;
 
public class ChatServer {
    private static final Executor exec = Executors.newFixedThreadPool(100);
    private static ServerSocket serverSocket =  null;
    private static Socket clientSocket = null;
    private int portNumber;

    public void setChatServer(int portNumber) {
        this.portNumber = portNumber;
        startServer();
    }
	
    
    public void startServer(){
 
        try {
            serverSocket = new ServerSocket(portNumber);
			System.out.println("Server avviato alla porta " + portNumber);
        }catch (IOException e) {
            System.out.println("Exception caught when trying to listen on port "
                + portNumber + " or listening for a connection");
            System.out.println(e.getMessage());
        }
		while (true) {
			try {
				
				clientSocket = serverSocket.accept();
				//new chatThread(clientSocket).start();
                                exec.execute(new chatThread(clientSocket));
				System.out.println("Client connesso");
		    } catch (IOException e) {
				System.out.println(e);
		    }
		}
    }
    
    public void close() throws IOException{
        serverSocket.close();
        System.out.println(serverSocket.isClosed());
    }
}

class chatThread implements Runnable{
	  private Socket clientSocket = null;
	  private PrintWriter out = null;
	  private BufferedReader in = null;
	  private ChatProtocol cp = null;
	  public chatThread(Socket clientSocket) {
		this.clientSocket = clientSocket;
	  }

	  public void run() {

		try {
			out =  new PrintWriter(clientSocket.getOutputStream(), true);
                        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			String inputLine, outputLine;
             
            // Initiate conversation with client
            cp = new ChatProtocol();
            outputLine = cp.processInput(null);
            out.println(outputLine);
 
            while ((inputLine = in.readLine()) != "Ok ciao") {
                outputLine = cp.processInput(inputLine);
                out.println(outputLine);
                if (outputLine.equals("Ok ciao"))
                    break;
            }
			out.close();
			in.close();
			clientSocket.close();
		} catch (Exception e) {
			
		}finally{
			System.out.println("Client disconnesso");
		}
	  }
}
