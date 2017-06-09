package http.server.requests;

import java.util.ArrayList;
import java.util.List;

public class Request {
    private Header header;
    private List<RequestLine> lines;

    public Header getHeader() {
        return header;
    }

    public List<RequestLine> getLines() {
        return lines;
    }

    public Request(Header header) {
        this.header = header;
        this.lines = new ArrayList<>();
    }
    
    public Request(String raw) {
        String[] tmp = raw.split("\n", 2);
        this.header = new Header(tmp[0]);
        this.lines = new ArrayList<>();
        for (String s : tmp[1].split("\n")) {
            try {
                lines.add(new RequestLine(s));
            } catch (ArrayIndexOutOfBoundsException ex) {
                
            }
        }
    }

    @Override
    public String toString() {
        String s = header.toString() + "\n";
        for (RequestLine line : lines)
            s += line.toString() + "\n";
        return s;
    }
}
