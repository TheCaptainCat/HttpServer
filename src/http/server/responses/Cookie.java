package http.server.responses;

/**
* Cette classe représente un cookie.
*/
public class Cookie {
    private String title;
    private String content;

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public Cookie(String title, String content) {
        this.title = title;
        this.content = content;
    }

    @Override
    public String toString() {
        return title + "=" + content;
    }
}
