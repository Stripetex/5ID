
import java.io.*;
import java.net.*;
import java.lang.Thread;

public class Echo {

    public static void main(String args[]) {

        ServerSocket echoServer = null;
        String line;
        BufferedReader is;
        PrintStream os;
        Socket clientSocket = null;
        int c = 0;
        try {
            echoServer = new ServerSocket(9999);
            //System.out.println("Use \"netcat 127.0.0.1 9999\"");
        } catch (IOException e) {
            System.out.println(e);
        }
        try {
            clientSocket = echoServer.accept();
            is = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            os = new PrintStream(clientSocket.getOutputStream());
            while (c != 3) {
                line = is.readLine();
                c++;
                Thread.sleep(1000);
                os.println("ECHO " + line);
            }
			os.println("Chiusura client e server in corso...");
            clientSocket.close();
            c = 0;
        } catch (IOException e) {
            System.out.println(e);
        } catch (InterruptedException ie) {
            System.out.println(ie);
        }
    }
}
