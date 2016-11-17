
package dummyrobotserver;

import dummyia.*;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;


public class SessionProtocol implements Runnable {

    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;

    private DummyIA ia;
    private String username;

    public SessionProtocol(Socket socket, DummyGraph graph) {

        this.socket = socket;

        try {
			
            in = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
            out = new PrintWriter(this.socket.getOutputStream(), true);
			
			ia = new DummyIA(graph);
			
        } catch (Exception e) {
            System.out.println("Error estabilishing connection with socket " + this.socket.toString());
        }
    }

    @Override
    public void run() {

        try {

            username = in.readLine();
            System.out.println("User " + username + " connected.");

            String read = "";
            String reply = "";
            String stop = "#STOP#";

            while (true) {
                read = in.readLine();
                if (read.equals(stop)) { break; } 
                else { reply = ia.reply(read); }
                    out.println(reply);
            }

            System.out.println("User " + username + " disconnected.\n(\tSocket " + socket.toString() + ")\n");
            socket.close();

        } catch (Exception e) {
            System.out.println("Error with socket " + socket.toString() + " during session.");
        }
    }
}
