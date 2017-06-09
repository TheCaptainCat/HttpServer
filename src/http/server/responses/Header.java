package http.server.responses;

public class Header {
    private final String protocol;
    private final int code;
    private final String label;

    public Header(String protocol, int code, String label) {
        this.protocol = protocol;
        this.code = code;
        this.label = label;
    }

    public String getProtocol() {
        return protocol;
    }

    public int getCode() {
        return code;
    }

    public String getLabel() {
        return label;
    }
}