package smartserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
/**
 *
 * @author Marco
 */
public class SmartServer {
    private static int portNumber = 9999;
    private static ServerSocket serverSocket = null;
    private static Socket clientSocket = null;
    private static final Executor exec = Executors.newFixedThreadPool(100);
    private static int nClient = 0;
  
    
    public static void main(String[] args){
        letsTheGameBegins();
        
    }
    
    public static void letsTheGameBegins(){
        try{
            serverSocket = new ServerSocket(portNumber);
        }catch (IOException e){
            System.out.println("Exception caught when trying to listen on port" + portNumber);
            System.out.println(e.getMessage());
        } finally {
            System.out.println("Server started correctly on port " + portNumber);
        }
        
        while(true){
            try{
                clientSocket = serverSocket.accept();
                exec.execute(new smartThread(clientSocket));
                nClient++;
                System.out.println("Client number " + nClient + " connected");
            } catch(IOException e){
                System.out.println(e.getMessage());
            }
        }
        
    }
    
    private void close() throws IOException{
            serverSocket.close();
            System.out.println(serverSocket.isClosed());
    }
}

class smartThread implements Runnable{
    private Socket clientSocket = null;
    private PrintWriter out = null;
    private BufferedReader in = null;
    public SmartProtocol sp;
    private String close = "Ciao grazie";
    
    public smartThread(Socket clientSocket){
        this.clientSocket = clientSocket;
    }
    
    public void run(){
        try{
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            String input, output;
            sp = new SmartProtocol();
            
            while((input = in.readLine()) != "Grazie ciao"){
                output = sp.processInput(input);
                out.println(output);
                if(output.equals(close) || output.equals(close.toLowerCase())){
                    out.println("<gameover>");
                    break;
                }
                
            }
            out.close();
            in.close();
            clientSocket.close();
        } catch (Exception e){
            System.out.println(e.getMessage());
        } finally {
            System.out.println("Client disconnected");
        }
    }
}

