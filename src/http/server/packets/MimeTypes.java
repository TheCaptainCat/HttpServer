package http.server.packets;

public class MimeTypes {
    public static String getMimeType(String ext) {
        if (ext.matches("html"))
            return "text/html";
        if (ext.matches("jpg|jpeg"))
            return "image/jpeg";
        return "text/plain";
    }
}
