package http.server.requests;

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
