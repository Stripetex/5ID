/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package robotclient;



import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class communication {

  public static void main(String[] args) throws Exception {
    DatagramSocket s = new DatagramSocket();
    byte[] buf = new byte[1000];
    DatagramPacket dp = new DatagramPacket(buf, buf.length);

    InetAddress hostAddress = InetAddress.getByName("localhost");
    while (true) {
      BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
      String outMessage = stdin.readLine();

      if (outMessage.equals("bye"))
        break;
      String outString = "Client say: " + outMessage;
      buf = outString.getBytes();

      DatagramPacket out = new DatagramPacket(buf, buf.length, hostAddress, 9999);
      s.send(out);

      s.receive(dp);
      String rcvd = "rcvd from " + dp.getAddress() + ", " + dp.getPort() + ": "
          + new String(dp.getData(), 0, dp.getLength());
      System.out.println(rcvd);
    }
  }
}


/**

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.Socket;


 *
 * @author Andrea
 
public class communication {

    private DatagramSocket datagramSocket;
    private Socket Client;
    private PrintWriter out = null;
    private BufferedReader in = null;

    private final static communication instance = new communication();

    public static communication getInstance() {
        return instance;
    }

    public void create(String hostname, int porta) throws IOException {
        datagramSocket= new DatagramSocket();
        Client = new Socket(hostname, porta);
        out = new PrintWriter(Client.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(Client.getInputStream()));
    }

    public void Scrivi(String text) {
        DatagramPacket dp= new DatagramPacket(buf, length, null)
        out.println(text);
    }

    public String leggi() throws IOException {
        return in.readLine();
    

}
    public void close() throws IOException{
        Client.close();
    }
}
*/