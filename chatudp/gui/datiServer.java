package chatudp.gui;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class datiServer {
	private DatagramSocket connessione;
	private final static datiServer instance = new datiServer();
        private InetAddress ipAddress;
        private int port;
        private String nome;

	public boolean creaConnessione(int port, String ip, String nome) throws UnknownHostException {
                ipAddress = InetAddress.getByName(ip);
                this.port = port;
                this.nome = nome;
		try {
			this.connessione = new DatagramSocket();
                        return true;
		} catch (IOException ex) {
		}
                return false;
		
	}

	public static datiServer getDatiServer() {
		return instance;
	}
	
	public void scrivi(String testo) throws IOException{
            byte[] data = testo.getBytes();
            DatagramPacket packet = new DatagramPacket(data,data.length,ipAddress, port);
            connessione.send(packet);
	}
	
	public String leggi() throws IOException{
            byte[] data = new byte[100];
            DatagramPacket packet = new DatagramPacket(data, data.length,ipAddress, port);
            connessione.receive(packet);
            return new String(packet.getData());
	}
        
        public boolean checkRegistration(String nome, String password) throws IOException{
            String combo = "{}"+nome+"|"+password;
            byte[] data = combo.getBytes();
            DatagramPacket sendPacket = new DatagramPacket(data,data.length,ipAddress, port);
            connessione.send(sendPacket);
            data = new byte[100];
            DatagramPacket receivedPacket = new DatagramPacket(data,data.length,ipAddress, port);
            connessione.receive(receivedPacket);
            if(new String(receivedPacket.getData()).trim().equals("registrato")) return true;
            return false;
        }
        
        public boolean setRegistration(String nome, String password) throws IOException{
            String combo = "()"+nome+"|"+password;
            byte[] data = combo.getBytes();
            DatagramPacket sendPacket = new DatagramPacket(data,data.length,ipAddress, port);
            connessione.send(sendPacket);
            data=new byte[100];
            DatagramPacket receivedPacket = new DatagramPacket(data,data.length,ipAddress, port);
            connessione.receive(receivedPacket);
            if(new String(receivedPacket.getData()).trim().equals("registrato")) return true;
            return false;
        }
	
        public boolean checkConnection(){
            try {
                String prova = "prova";
                byte[] data = prova.getBytes();
                DatagramPacket sendPacket = new DatagramPacket(data,data.length,ipAddress, port);
                connessione.send(sendPacket);
                return true;
            } catch (IOException ex) {
                return false;
            }
        }

        public InetAddress getIpAddress() {
            return ipAddress;
        }

        public int getPort() {
            return port;
        }

        public String getNome() {
            return nome;
        }

        
}
