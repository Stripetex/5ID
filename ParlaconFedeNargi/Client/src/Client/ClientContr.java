package client;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientContr {
    final String HOSTNAME ="127.0.0.1";
    final int PORT =9999;
    private Socket cl;
    private PrintWriter out = null;
    private BufferedReader in = null;

    private final static ClientContr init = new ClientContr();

    public static ClientContr startInit() {
        return init;
    }

    public void create(String hostname, int porta) throws IOException {
        cl = new Socket(HOSTNAME, PORT);
        out = new PrintWriter(cl.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(cl.getInputStream()));
    }

    public void prnt(String text) {
        out.println(text);
    }
    
    
    

    public String read() throws IOException {
        return in.readLine();
    }
    public void close() throws IOException {
        cl.close();
    }
}