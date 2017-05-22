package http.server.core;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Connection implements Runnable {
    public static final int MAX_SIZE = 2048;
    private Socket socket;

    public Connection(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            BufferedInputStream inBuf = new BufferedInputStream(socket.getInputStream());
            byte[] data = new byte[MAX_SIZE];
            int length = 0;
            String content = "";
            while ((length = inBuf.read(data)) != -1) {
                content += new String(data, 0, length);
                if (length != MAX_SIZE)
                    break;
            }
            OutputStream out = socket.getOutputStream();
            String answer = "HTTP/1.1 200 OK\n" +
                    "Date: " + new Date() + "\n" +
                    "Content-Type: text/html; charset=UTF-8\n" +
                    "Content-Encoding: UTF-8\n" +
                    "Content-Length: 47\n" +
                    "Last-Modified: " + new Date() + "\n" +
                    "Server: HttpServer/0.1 (Windows 6.4)\n" +
                    "Connection: close\n\n" +
                    "<html><body><h1>Hello World!</h1></body></html>";
            out.write(answer.getBytes());
            out.flush();
        } catch (IOException ex) {
            Logger.getLogger(Connection.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
