package http.server.requests;

import http.server.responses.Cookie;
import java.util.ArrayList;
import java.util.List;

public class Request {
    private Header header;
    private List<RequestLine> lines;
    private List<Cookie> cookies;

    public Header getHeader() {
        return header;
    }

    public List<RequestLine> getLines() {
        return lines;
    }

    public List<Cookie> getCookies() {
        return cookies;
    }

    public Request(Header header, List<RequestLine> lines, List<Cookie> cookies) {
        this.header = header;
        this.lines = lines;
        this.cookies = cookies;
    }

    public Request(Header header) {
        this(header, new ArrayList<>(), new ArrayList<>());
    }
    
    public Request(String raw) {
        String[] tmp = raw.split("\n", 2);
        this.header = new Header(tmp[0]);
        this.lines = new ArrayList<>();
        for (String s : tmp[1].split("\n")) {
            try {
                lines.add(new RequestLine(s));
            } catch (ArrayIndexOutOfBoundsException e) { }
        }
        this.cookies = new ArrayList<>();
        try {
            String cookieContent = getRequestLine("Cookie").getContent();
            for (String s : cookieContent.split("; "))
                cookies.add(new Cookie(s.split("=")[0], s.split("=")[1]));
        } catch (Exception e) { }
    }
    
    public RequestLine getRequestLine(String title) {
        for (RequestLine rl : lines)
            if (rl.getTitle().equals(title))
                return rl;
        return null;
    }
    
    public Cookie getCookie(String title) {
        for (Cookie c : cookies)
            if (c.getTitle().equals(title))
                return c;
        return null;
    }

    @Override
    public String toString() {
        String s = header.toString() + "\n";
        for (RequestLine line : lines)
            s += line.toString() + "\n";
        return s;
    }
}
