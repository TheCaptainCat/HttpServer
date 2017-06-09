 package http.server.core;

import http.server.requests.Request;
import http.server.responses.Content;
import http.server.responses.Cookie;
import http.server.responses.Header;
import http.server.responses.Response;
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
            Request request = new Request(content);
            System.out.println(request);
            String filename = request.getHeader().getFile();
            if (filename.equals(""))
                filename = "index.html";
            Header h = new Header("HTTP/1.1", 200, "OK");
            Content c = new Content("www/" + filename, "UTF-8");
            Response r = new Response(h, c);
            r.addCookie(new Cookie("Set-Cookie: id=1574-885751-111860843"));
            r.addCookie(new Cookie("Set-Cookie: remember=true"));
            r.addCookie(new Cookie("Set-Cookie: __g_=qfg4qd6g872qeg7q6d"));
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
