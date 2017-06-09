package http.server.requests;

public class RequestLine {
    private final String title;
    private final String content;

    public RequestLine(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public RequestLine(String raw) {
        this.title = raw.split(": ", 2)[0];
        this.content = raw.split(": ", 2)[1];
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    @Override
    public String toString() {
        return String.format("%s: %s", title, content);
    }
}
