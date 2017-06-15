package http.server.requests;

/**
* Cette classe sert à découper la première ligne d'une requête afin d'extraire les informations.
*
* La première ligne est de type GET /index.html HTTP/1.1
*/
public class Header {
    private final String method;
    private final String file;
    private final String protocol;

    public Header(String method, String file, String protocol) {
        this.method = method;
        this.file = file;
        this.protocol = protocol;
    }

    public Header(String raw) {
        this.method = raw.split(" ")[0];
        this.file = raw.split(" ")[1].substring(1);
        this.protocol = raw.split(" ")[2];
    }

    public String getMethod() {
        return method;
    }

    public String getFile() {
        return file;
    }

    public String getProtocol() {
        return protocol;
    }

    @Override
    public String toString() {
        return String.format("%s %s %s", method, file, protocol);
    }
}
