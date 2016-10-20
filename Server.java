package server;

/**
 * @author andrea.giugie
 */
/**
 *
 * @author Giugie Andrea
 */
import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class Server {

    public static void main(String args[]) {
		ArrayList password = new ArrayList();
		password.add("a");
		password.add("b");
		password.add("c");
        ServerSocket echoServer = null;
        String line;
        BufferedReader is;
        PrintStream os;
        int port = 9999;
        StringTokenizer parole;
        Socket clientSocket = null;
		ArrayList temp= password;
		
        try {
            echoServer = new ServerSocket(port);    //Creo un socket alla porta 9999
            System.out.println("Creato socket server in ascolto alla porta" + port);
            clientSocket = echoServer.accept();          //Non appena viene individuato un socket client si creano i due stream
            is = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));  //legge cosa invia il client
            os = new PrintStream(clientSocket.getOutputStream());                           //invia al client
            while (true) {
				
				

                line = is.readLine();
                Thread.sleep(100);
                if(temp.contains(line)){
					temp.remove(temp.indexOf(line));
				}
				else{
					temp=password;
				}
                
                if (temp.size()==0) {  //condizione
                    os.println("<QUIT>");

                    echoServer.close();
                    break;
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