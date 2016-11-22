package robotserver;

/**
 *
 * @author Andrea
 */
import java.net.*;
import java.io.*;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class RobotServer {

    private static final Executor exec = Executors.newFixedThreadPool(100);
    private static ServerSocket serverSocket = null;
    private static Socket clientSocket = null;
    private int portNumber;

    public RobotServer(int portNumber) {
        this.portNumber = portNumber;
        startServer();
    }

    public static void main(String[] args) {
        RobotServer s = new RobotServer(9999);
    }

    public void startServer() {

        try {
            serverSocket = new ServerSocket(portNumber);
            System.out.println("Server avviato alla porta " + portNumber);
        } catch (IOException e) {

        }
        while (true) {
            try {

                clientSocket = serverSocket.accept();
                exec.execute(new chatThread(clientSocket));
                System.out.println("Client connesso");
            } catch (IOException e) {
                System.out.println(e);
            }
        }
    }

    public void close() throws IOException {
        serverSocket.close();
    }
}

class chatThread implements Runnable {

    private Socket clientSocket = null;
    private PrintWriter out = null;
    private BufferedReader in = null;
    private Protocol cp = null;

    public chatThread(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    public void run() {

        try {
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            String inputLine, outputLine;

            // Initiate conversation with client
            cp = new Protocol();
            //outputLine = cp.processInput(null);
         //   out.println(outputLine);

            while ((inputLine = in.readLine()) != "bye.") {
                outputLine = cp.processInput(inputLine);
                out.println(outputLine);
                if (outputLine.equals("bye.")) {
                    break;
                }
            }
            out.close();
            in.close();
            clientSocket.close();
        } catch (Exception e) {

        } finally {
            System.out.println("Client disconnesso");
        }
    }
}
