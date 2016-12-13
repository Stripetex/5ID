/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serverudp;

import java.net.InetAddress;

/**
 *
 * @author Andrea
 */
public class Utente {

    private InetAddress ip; //L'ip dell'utente
    private String name;
    private int port; //La porta del client

    //Costruttore
    public Utente(InetAddress ip, String name, int port) {
        this.ip = ip;
        this.name = name;
        this.port = port;
    }

    //Vari get per le informazioni 
    public InetAddress getIp() {
        return ip;
    }

    public String getName() {
        return name;
    }

    public int getPort() {
        return port;
    }

}
