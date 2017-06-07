 package http.server.core;

import http.server.packets.Content;
import http.server.packets.Header;
import http.server.packets.Response;
import java.io.BufferedInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Connection implements Runnable {
    public static final int MAX_SIZE = 2048;
    private final Socket socket;

    public Connection(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            BufferedInputStream inBuf = new BufferedInputStream(socket.getInputStream());
            byte[] data = new byte[MAX_SIZE];
            int length;
            String content = "";
            while ((length = inBuf.read(data)) != -1) {
                content += new String(data, 0, length);
                if (length != MAX_SIZE)
                    break;
            }
            String filename = content.split(" ")[1].substring(1);
            if (filename.equals(""))
                filename = "index.html";
            Header h = new Header("HTTP/1.1", 200, "OK");
            Content c = new Content("www/" + filename, "UTF-8");
            Response r = new Response(h, c);
            OutputStream out = socket.getOutputStream();
            out.write(r.toByteArray());
            out.flush();
        } catch (FileNotFoundException fnfe) {
            try {
                Header h = new Header("HTTP/1.1", 404, "Not Found");
                Content c = new Content("www/errors/404.html", "UTF-8");
                Response r = new Response(h, c);
                OutputStream out = socket.getOutputStream();
                out.write(r.toByteArray());
                out.flush();
            } catch (IOException ex) {
                Logger.getLogger(Connection.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (Exception e) {
            Logger.getLogger(Connection.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            try {
                socket.close();
            } catch (IOException ex) {
                Logger.getLogger(Connection.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
