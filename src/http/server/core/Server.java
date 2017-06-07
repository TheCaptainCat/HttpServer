package http.server.core;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Server implements Runnable {
    private final int port;
    private final ServerSocket serverSocket;

    public Server(int port) throws IOException {
        this.port = port;
        this.serverSocket = new ServerSocket(port);
    }

    @Override
    public void run() {
        try {
            System.out.println(String.format("Server started.\nAddress: %s.\nListening on port %d.",
                    InetAddress.getLocalHost(), port));
        } catch (UnknownHostException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
        while (true) {
            try {
                System.out.println("Waiting for a connection.");
                Socket connection = serverSocket.accept();
                System.out.println("Connection accepted.");
                new Thread(new Connection(connection)).start();
            } catch (IOException ex) {
                Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    } 
}
