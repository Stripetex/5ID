/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientudp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jasypt.util.text.BasicTextEncryptor;

/**
 *
 * @author Andrea
 */
public class communication {

    private String Username;

    private DatagramSocket Client;
    private PrintWriter out = null;
    BufferedReader in = null;
    InetAddress IPAddress;
    int porta;

    byte[] sendData = new byte[1024];
    byte[] receiveData = new byte[1024];

    private final static communication instance = new communication();

    public static communication getInstance() {
        return instance;
    }

    public void create(String hostname, int porta) throws IOException {
        Client = new DatagramSocket();
        IPAddress = InetAddress.getByName(hostname);
        in = new BufferedReader(new InputStreamReader(System.in));
        this.porta = porta;
    }

    public void Scrivi(String text) throws IOException {
        sendData = new byte[100];
        sendData = text.getBytes();
        DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, porta);
        Client.send(sendPacket);
    }

    public String leggi() throws IOException {
        receiveData = new byte[100];
        DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
        Client.receive(receivePacket);
        String dati = new String(receivePacket.getData());
        trimEnd(dati);
        System.out.println(dati);
        return dati;

    }

    public void close() throws IOException {
        Client.close();
    }

    private boolean operation(String Username, String Password, String op) throws IOException {

        String prepare = (Username + "," + Password);

        BasicTextEncryptor textEncryptor = new BasicTextEncryptor();
        textEncryptor.setPassword("PASSWORDUDP");
        String myEncryptedText = textEncryptor.encrypt(prepare);

        sendData = (op + myEncryptedText).getBytes();

        DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, porta);
        Client.send(sendPacket);
        //aspetto che mi risponda e ritorno quello che devo
        receiveData = new byte[1024];
        DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
        Client.receive(receivePacket);    //qui aspetta che arrivi un pacchetto

        String sentence = new String(receivePacket.getData());
        System.out.println(sentence);
        if (sentence.trim().contains("true")) {
            return true;
        } else {
            return false;
        }

    }

    public boolean askLogin(String Username, String Password) throws IOException, NoSuchAlgorithmException {
        return operation(Username, Password, "<REQUESTLOGIN>");
    }

    public boolean createAccount(String Username, String Password) throws IOException, NoSuchAlgorithmException {
        return operation(Username, Password, "<CREATEACCOUNT>");
    }

    public boolean deleteAccount(String Username, String Password) throws IOException, NoSuchAlgorithmException {
        return operation(Username, Password, "<DELETEACCOUNT>");
    }

    public boolean changeNomeAccount(String Username, String Password) throws IOException, NoSuchAlgorithmException {
        return operation(Username, Password, "<CHANGENOMEACCOUNT>");
    }

    public boolean changePasswdAccount(String Username, String Password) throws IOException, NoSuchAlgorithmException {
        return operation(Username, Password, "<CHANGEPASSWDACCOUNT>");
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String Username) {
        this.Username = Username;
    }

    public static String trimEnd(String s) {
        if (s == null || s.length() == 0) {
            return s;
        }
        int i = s.length();
        while (i > 0 && Character.isWhitespace(s.charAt(i - 1))) {
            i--;
        }
        if (i == s.length()) {
            return s;
        } else {
            return s.substring(0, i);
        }
    }
}
