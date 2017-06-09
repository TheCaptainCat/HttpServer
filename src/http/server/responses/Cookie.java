package http.server.responses;

public class Cookie {
    public String content;

    public Cookie(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return content;
    }
}
