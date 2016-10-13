/**
 *
 * @author Giugie Andrea
 */
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
        StringTokenizer parole;
        Socket clientSocket = null;
        try {
            echoServer = new ServerSocket(port);    //Creo un socket alla porta 9999
            System.out.println("Creato socket server in ascolto alla porta" + port);
            clientSocket = echoServer.accept();          //Non appena viene individuato un socket client si creano i due stream
            is = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));  //legge cosa invia il client
            os = new PrintStream(clientSocket.getOutputStream());                           //invia al client
            while (true) {

                line = is.readLine();
                Thread.sleep(100);
                
                 parole= new StringTokenizer(line); //conto le parole
                
                if (parole.countTokens()==3) {  //se sono 3 invio al client l'ordine di chiudersi
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
