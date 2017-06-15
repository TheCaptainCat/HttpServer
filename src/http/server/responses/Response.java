package http.server.responses;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
* Cette classe sert à construire une requête à envoyer au client.
*/
public class Response {
    Header header;
    Content content;
    List<Cookie> cookies;

    public Response(Header header, Content content) {
        this.header = header;
        this.content = content;
        cookies = new ArrayList<>();
    }
    
    public void addCookie(Cookie c) {
        cookies.add(c);
    }
    
    /**
    * Transforme le contenu de la réponse en chaîne de caractères,
    * dont chaque élément est dans un octet. Le tableau d'octets
    * sera écrit dans le socket.
    *
    * @return un tableau d'octet contenant l'entête et le corps de la réponse
    */
    public byte[] toByteArray() {
        String s = String.format(
                "%s %d %s\n",
                header.getProtocol(), header.getCode(), header.getLabel());
        s += "Date: " + new Date() + "\n";
        s += String.format(
                "Content-Type: %s\nContent-Encoding: %s\nContent-Length: %d\n",
                content.getType(), content.getEncoding(), content.getContent().size());
        s += "Server: HttpServer/0.1 (Windows 6.4)\nConnection: close\n";
        for (Cookie c : cookies)
            s += "Set-Cookie: " +  c.toString() + "\n";
        s += "\n";
        int sLen = s.length();
        int cLen = content.getContent().size();
        byte[] data = new byte[sLen + cLen];
        List<Byte> bytes = content.getContent();
        for (int i = 0; i < sLen; i++) {
            data[i] = (byte) s.charAt(i);
        }
        for (int i = 0; i < cLen; i++) {
            data[sLen + i] = bytes.get(i);
        }
        return data;
    }
}
