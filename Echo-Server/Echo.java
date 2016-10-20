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
            System.out.println("Server> Socket Creato");
            System.out.println("Server> Server in ascolto:  localhost:" + port);
            System.out.println("");
            clientSocket = echoServer.accept();          //Non appena viene individuato un socket client si creano i due stream
            
            is = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));  //legge cosa invia il client
            os = new PrintStream(clientSocket.getOutputStream());                           //invia al client
            
            while (true) {
				// Ottengo la linea di testo inserita e la stampo a schermo
                line = is.readLine();
                System.out.println("Client> [SAY] " + line);
                
                Thread.sleep(100);
                
                if (line.equals("Ciao ciao ciao")){  // Se sono 3 invio al client l'ordine di chiudersi
                    os.println("{-[CloseConnectionMessage]-}");
					System.out.println("Server> FINE TRASMISSIONE!");
                    echoServer.close();
                    break;
                }
                
                // Invio il messaggio al client
                os.println("Server> Mi hai appena detto: " + line);
                
            }
        } catch (IOException e) {
            System.out.println(e);
        } catch (InterruptedException ie) {
            System.out.println(ie);
        }
    }
}

