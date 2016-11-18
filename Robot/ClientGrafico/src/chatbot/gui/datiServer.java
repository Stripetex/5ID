package chatbot.gui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class datiServer {
	private Socket connessione;
	private final static datiServer instance = new datiServer();
	private PrintWriter out;
	private BufferedReader in;

	public boolean creaConnessione(int port, String ip) {
		try {
			this.connessione = new Socket(ip, port);
			out = new PrintWriter(connessione.getOutputStream(), true);
			in = new BufferedReader(new InputStreamReader(connessione.getInputStream()));
                        return true;
		} catch (IOException ex) {
		}
                return false;
		
	}

	public static datiServer getDatiServer() {
		return instance;
	}
	
	public void scrivi(String testo){
		out.println(testo);
	}
	
	public String leggi() throws IOException{
		return in.readLine();
	}
	
}
