/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serverudp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author fabio
 */
public class Server {
    static DatagramSocket server;
    static ArrayList<Client> client;
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws SocketException, IOException, Exception {        
        if(args.length!=1){
            System.out.println("Use java server <port_number>");
            System.exit(1);
        }
        client = new ArrayList<Client>();
        server = new DatagramSocket(Integer.parseInt(args[0]));
        System.out.println("server avviato");
        while(true){
            byte[] data = new byte[100];
            DatagramPacket packet = new DatagramPacket(data, data.length);
            server.receive(packet);
            String messaggio = new String(packet.getData());
            checkMessaggio(messaggio, packet.getAddress(), packet.getPort());
        }
               
    }
    
    static void checkMessaggio(String messaggio, InetAddress ip, int porta) throws IOException, Exception{
        /*login: {}, messaggio: [], registrazione: ()*/
        String nome = "", password = "", mex = "";
        if(!(messaggio.charAt(0)=='[' && messaggio.charAt(1)==']')){
            boolean pas = false;
            for (int i = 2; i < messaggio.length(); i++) {
                if(pas)password+=messaggio.charAt(i);
                else{
                    if(messaggio.charAt(i)=='|')pas=true;
                    else{
                        nome+=messaggio.charAt(i);
                    }
                }
            }
        }else{
            boolean pas = false;
            for (int i = 2; i < messaggio.length(); i++) {
                if(pas)mex+=messaggio.charAt(i);
                else{
                    if(messaggio.charAt(i)=='|')pas=true;
                    else{
                        nome+=messaggio.charAt(i);
                    }
                }
            }
        }
        if(messaggio.charAt(0)=='{' && messaggio.charAt(1)=='}'){
            if(checkDatabase(nome,password)){
                client.add(new Client(nome, ip, porta));
                byte[] data = "registrato".getBytes();
                DatagramPacket packet = new DatagramPacket(data, data.length,ip,porta);
                server.send(packet);
            }else{
                byte[] data = "no".getBytes();
                DatagramPacket packet = new DatagramPacket(data, data.length,ip,porta);
                server.send(packet);
            }
        }else if(messaggio.charAt(0)=='[' && messaggio.charAt(1)==']'){
            for (int i = 0; i < client.size(); i++) {
                InetAddress ipToSend = client.get(i).getIp();
                int portToSend = client.get(i).getPort();
                String nameToSend = client.get(i).getNome();
                if(!nameToSend.trim().equals(nome.trim())){
                    String mexToSend = nome.trim()+"#"+mex;    
                    System.out.println(mex);
                    byte[] data = mexToSend.getBytes();
                    DatagramPacket packet = new DatagramPacket(data, data.length,ipToSend,portToSend);
                    server.send(packet);
                }
            }
        }else if(messaggio.charAt(0)=='(' && messaggio.charAt(1)==')'){
            if(addDatabase(nome,password)){
                client.add(new Client(nome, ip, porta));
                byte[] data = "registrato".getBytes();
                DatagramPacket packet = new DatagramPacket(data, data.length,ip,porta);
                server.send(packet);
            }else{
                byte[] data = "no".getBytes();
                DatagramPacket packet = new DatagramPacket(data, data.length,ip,porta);
                server.send(packet);
            }
        }
    }
    
    static boolean checkDatabase(String nome, String password) throws Exception{
        Connection con = null;
        Class.forName("org.sqlite.JDBC").newInstance();
        con = DriverManager.getConnection("jdbc:sqlite:users.db");

        PreparedStatement stmt = con.prepareStatement("SELECT * FROM Users");

        ResultSet rs = stmt.executeQuery();
        boolean trovato = false;
        while (rs.next() && !trovato) {
            String nomeDB = rs.getString("Username");
            String passwordDB = rs.getString("Password");
            if(nomeDB.equals(nome) && password.trim().equals(passwordDB.trim())) {trovato = true;}
        }
        con.close();
        return trovato;
    }
    
    static boolean checkName(String nome) throws Exception{
        Connection con = null;
        Class.forName("org.sqlite.JDBC").newInstance();
        con = DriverManager.getConnection("jdbc:sqlite:users.db");

        PreparedStatement stmt = con.prepareStatement("SELECT * FROM Users");

        ResultSet rs = stmt.executeQuery();
        boolean trovato = false;
        while (rs.next() && !trovato) {
            String nomeDB = rs.getString("Username");
            String passwordDB = rs.getString("Password");
            if(nomeDB.trim().equals(nome.trim())) {trovato = true;}
        }
        con.close();
        return trovato;
    }
    
    static boolean addDatabase(String nome, String password) throws Exception{
        Connection con = null;
        Class.forName("org.sqlite.JDBC").newInstance();
        con = DriverManager.getConnection("jdbc:sqlite:users.db");
        if(!checkName(nome)){
            Statement stmt = con.createStatement();
            stmt.executeUpdate("insert into Users values ('" + nome.trim() + "','" + password.trim() +"')");
            con.close();
            return true;
        }
        con.close();
        return false;

    }
}
