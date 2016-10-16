import java.io.*;
import java.net.*;

public class Client {

    public static void main(String[] args) throws IOException {

        Socket client = null;
        PrintWriter out = null;
        BufferedReader in = null;

        String address = "localhost";
        int port = 9999;

        try {
            client = new Socket(address, port);
            out = new PrintWriter(client.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(client.getInputStream()));
        } catch (Exception e) {
            System.err.println("Error");
            System.exit(1);

            BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
            String userInput;

            System.out.println("Connesso");
            while ((userInput = stdIn.readLine()) != null) {
                out.println(userInput);
                System.out.println(in.readLine());

            }
        }
    }
}
