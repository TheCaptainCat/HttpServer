package http.server.core;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
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
        while (true) {            
            try {
                Socket connection = serverSocket.accept();
                new Thread(new Connection(connection)).start();
            } catch (IOException ex) {
                Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    } 
}
