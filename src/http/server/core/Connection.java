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

/**
* Représente une connexion avec le client.
* La connexion est Runnable afin que le serveur soit concurent.
*/
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
            // Lecture de la requête. Le contenu est placé dans un buffer puis dans une String.
            while ((length = inBuf.read(data)) != -1) {
                content += new String(data, 0, length);
                if (length != MAX_SIZE)
                    break;
            }
            // Lecture de la requête
            Request request = new Request(content);
            String filename = request.getHeader().getFile();
            if (filename.equals(""))
                filename = "index.html";
            // Création de la réponse.
            Header h = new Header("HTTP/1.1", 200, "OK");
            Content c = new Content("www/" + filename, "UTF-8");
            Response r = new Response(h, c);
            // Ajout des cookies dans la réponse.
            // Les cookies sont ajoutés seulement s'ils ne sont pas dans la requête.
            if (request.getCookie("id") == null)
                r.addCookie(new Cookie("id", "1574-885751-111860843"));
            if (request.getCookie("remember") == null)
                r.addCookie(new Cookie("remember", "true"));
            if (request.getCookie("__g_") == null)
                r.addCookie(new Cookie("__g_", "qfg4qd6g872qeg7q6d"));
            // Ecriture de la réponse
            OutputStream out = socket.getOutputStream();
            out.write(r.toByteArray());
            out.flush();
        } catch (FileNotFoundException fnfe) {
            try {
                // En cas d'erreur de lecture, le serveur envoie une erreur 404.
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
