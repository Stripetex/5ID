package server;

import java.io.*;
import java.net.*;
import java.util.concurrent.*;

/**
 * @author Nico
 */
class ServerTask implements Callable<Void> {

    private Socket connection;
    private PrintStream output;
    private BufferedReader input;
    private boolean ACTIVE = true;

    public ServerTask(Socket connection) {
        this.connection = connection;
    }

    @Override
    public Void call() throws InterruptedException {
        try {
            input = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            output = new PrintStream(connection.getOutputStream());
            //output.println("Benvenuto alla Pizzeria!");
            Protocol p = new Protocol();
            String fromUser=  "";
            while (ACTIVE) {
                fromUser = input.readLine();
                String forUser = p.checkText(fromUser);
                if (forUser.equalsIgnoreCase("COMMAND x0 - QUIT")) {
                    ACTIVE = false;
                    output.println("COMMAND x0 - QUIT");
                } else {
                    output.println(forUser);
                }
            }
        } catch (IOException ex) {
            System.err.println(ex);
        } finally {
            try {
                connection.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

}
