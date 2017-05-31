package http.server.packets;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Content {
    private final List<Byte> content;
    private final String type;
    private final String encoding;

    public Content(String filename, String encoding)
            throws IOException {
        this.content = openFile(filename);
        String[] tmp = filename.split("\\.");
        String ext = tmp[tmp.length - 1];
        this.type = MimeTypes.getMimeType(ext) + "; charset=UTF-8";
        this.encoding = encoding;
    }
    
    private List<Byte> openFile(String filename) throws IOException {
        List<Byte> bytes = new ArrayList<>();
        FileInputStream fis = new FileInputStream(filename);
        while (true) {
            byte[] data = new byte[1024];
            int length = fis.read(data);
            for (int i = 0; i < length; i++)
                bytes.add(data[i]);
            if (length != 1024)
                break;
        }
        return bytes;
    }

    public List<Byte> getContent() {
        return content;
    } 

    public String getType() {
        return type;
    }

    public String getEncoding() {
        return encoding;
    }
}
