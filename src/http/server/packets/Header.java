package http.server.packets;

public class Header {
    private String method;
    private int code;
    private String label;

    public Header(String method, int code, String label) {
        this.method = method;
        this.code = code;
        this.label = label;
    }

    public String getMethod() {
        return method;
    }

    public int getCode() {
        return code;
    }

    public String getLabel() {
        return label;
    }
}