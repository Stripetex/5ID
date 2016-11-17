/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package robotclient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 *
 * @author Andrea
 */
public class communication {

    private Socket Client;
    private PrintWriter out = null;
    private BufferedReader in = null;

    private final static communication instance = new communication();

    public static communication getInstance() {
        return instance;
    }

    public void create(String hostname, int porta) throws IOException {
        Client = new Socket(hostname, porta);
        out = new PrintWriter(Client.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(Client.getInputStream()));
    }

    public void Scrivi(String text) {
        out.println(text);
    }

    public String leggi() throws IOException {
        return in.readLine();
    

}
    public void close() throws IOException{
        Client.close();
    }
}
