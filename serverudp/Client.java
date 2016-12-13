/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serverudp;

import java.net.InetAddress;

/**
 *
 * @author fabio
 */
public class Client {
    private String nome;
    private InetAddress ip;
    private int port;

    public Client(String nome, InetAddress ip, int port) {
        this.nome = nome;
        this.ip = ip;
        this.port = port;
    }

    public String getNome() {
        return nome;
    }

    public InetAddress getIp() {
        return ip;
    }

    public int getPort() {
        return port;
    }
    
    
}
