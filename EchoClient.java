
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.DataOutputStream;
import java.net.Socket;
import java.io.PrintWriter;

public class EchoClient {

    public static void main(String[] args) throws IOException {
        String serverAddress = "127.0.0.1"; // server string
        String userInput = "prova";
        Socket s = new Socket(serverAddress, 9999);
        BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
        PrintWriter out = new PrintWriter(s.getOutputStream(), true);
        BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));


        /*for (int i = 0; i < 3; i++) {
            System.out.println(in.readLine());
        }*/
        while ((userInput = stdIn.readLine()) != null) { // send ^C
	        out.println(userInput); //flush the buffer (see 18)
	        System.out.println(in.readLine());
	    }
    }
}
