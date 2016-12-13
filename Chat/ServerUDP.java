package serverudp;

import java.io.IOException;
import java.math.BigInteger;
import java.net.*;
import java.security.MessageDigest;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import org.jasypt.util.text.BasicTextEncryptor;

class ServerUDP {

    public static void main(String args[]) throws Exception {
        boolean pass = false;
        BasicTextEncryptor textEncryptor = new BasicTextEncryptor();
        textEncryptor.setPassword("PASSWORDUDP");
        Connection con = null;
        DatagramSocket serverSocket = new DatagramSocket(9999);
        DatagramPacket receivePacket = null;
        Utente[] clients = new Utente[50];

        Map<String, String> UTENTI_CACHE = new HashMap<>();
        loadMap(UTENTI_CACHE);

        int ClientsIndex = 0;

        byte[] receiveData = new byte[1024];
        byte[] sendData = new byte[1024];
        while (true) {
            Arrays.fill(receiveData, (byte) 0); // try to remove
            Arrays.fill(sendData, (byte) 0); // try to remove
            receivePacket = new DatagramPacket(receiveData, receiveData.length);
            serverSocket.receive(receivePacket);    //qui aspetta che arrivi un pacchetto

            String s = receivePacket.getAddress().getHostName();

            String sentence = new String(receivePacket.getData());
            if (sentence.contains("REQUESTLOGIN")) {    //chiedo un login
                InetAddress IPAddress = receivePacket.getAddress();
                int port = receivePacket.getPort();

                sentence = sentence.substring(14);

                String plainText = textEncryptor.decrypt(sentence);
                String[] arr = plainText.split(",");

                con = connection();

                PreparedStatement stmt = con.prepareStatement("SELECT * FROM User");

                ResultSet rs = stmt.executeQuery();

                MessageDigest m = MessageDigest.getInstance("MD5"); //mi calcolo l'md5
                m.update(arr[1].getBytes(), 0, arr[1].length());
                String md = String.valueOf(new BigInteger(1, m.digest()).toString(16));

                String data = null;
                while (rs.next()) {

                    String nome = rs.getString("Username");
                    String hashed = rs.getString("HashedPassword");

                    if (nome.equals(arr[0]) && hashed.equals(md)) {//login
                        //esegui il login
                        data = "";
                        pass = true;
                        data = "" + pass;
                        clients[ClientsIndex] = new Utente(IPAddress, nome, port);
                        ClientsIndex++;
                    } else {
                        //digli che è sbagliata
                        pass = false;
                        System.out.println("data " + data);
                        data = "" + pass;
                    }

                }
                sendData = send(sendData, data, IPAddress, port, serverSocket);

            } else if (sentence.contains("CREATEACCOUNT")) {InetAddress IPAddress = receivePacket.getAddress();
                int port = receivePacket.getPort();
                String data = serverOperation("INSERT INTO USER (UserName, Hashedpassword) VALUES (?,?)", IPAddress, port, receivePacket, sentence, textEncryptor, UTENTI_CACHE);
                sendData = send(sendData, data, IPAddress, port, serverSocket);
            } else if (sentence.contains("DELETEACCOUNT")) {
                InetAddress IPAddress = receivePacket.getAddress();
                int port = receivePacket.getPort();
                String data = serverOperation("DELETE INTO USER (UserName, Hashedpassword) VALUES (?,?)", IPAddress, port, receivePacket, sentence, textEncryptor, UTENTI_CACHE);
                sendData = send(sendData, data, IPAddress, port, serverSocket);
            } else {
                InetAddress IPAddress = receivePacket.getAddress();
                int port = receivePacket.getPort();
                inoltra(sentence, IPAddress, clients, ClientsIndex, serverSocket, port);
            }
        }
    }

    private static byte[] send(byte[] sendData, String data, InetAddress IPAddress, int port, DatagramSocket serverSocket) throws IOException {
        sendData = data.getBytes();
        DatagramPacket sendPacket //devo inviarlo a tutti tranne all'ip che l'ha inviato
                = new DatagramPacket(sendData, sendData.length, IPAddress, port);
        serverSocket.send(sendPacket);
        return sendData;
    }

    private static String serverOperation(String sql, InetAddress IPAddress, int port, DatagramPacket receivePacket, String sentence, BasicTextEncryptor textEncryptor, Map<String, String> UTENTI_CACHE) throws SQLException, IllegalAccessException, InstantiationException, ClassNotFoundException {
        Connection con;
        sentence = sentence.substring(15);
        String plainText = textEncryptor.decrypt(sentence);
        String[] arr = plainText.split(",");
        String passwd = textEncryptor.encrypt(arr[1]);
        con = connection();
        String data = null;
        if (!UTENTI_CACHE.containsKey(arr[0])) {
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, arr[0]);
            stmt.setString(2, passwd);
            data = "true";
            return data;
        } else {
            data = "false";
            return data;
        }
    }

    private static void loadMap(Map<String, String> UTENTI_CACHE) throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
        Connection connection = connection();
        PreparedStatement carico = connection.prepareStatement("SELECT * FROM User");
        ResultSet executeQuery = carico.executeQuery();
        while (executeQuery.next()) {
            String nome = executeQuery.getString("Username");
            String hashed = executeQuery.getString("HashedPassword");
            UTENTI_CACHE.put(nome, hashed);
        }
    }

    private static Connection connection() throws IllegalAccessException, InstantiationException, SQLException, ClassNotFoundException {
        Connection con;
        Class.forName("org.sqlite.JDBC").newInstance();
        con = DriverManager.getConnection("jdbc:sqlite:user.db");
        return con;
    }

    private static void inoltra(String text, InetAddress ip, Utente[] utenti, int index, DatagramSocket serverSocket, int port) throws IOException {
        for (int i = 0; i < index; i++) {
            if ((ip.equals(utenti[i].getIp()) && port != utenti[i].getPort()) || !ip.equals(utenti[i].getIp())) {
                byte[] b = new byte[text.length()];
                b = send(b, text, utenti[i].getIp(), utenti[i].getPort(), serverSocket);
            }
        }

    }
}
