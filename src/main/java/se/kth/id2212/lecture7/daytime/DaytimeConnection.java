package se.kth.id2212.lecture7.daytime;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.net.URL;
import java.net.URLConnection;

/**
 * Opens a TCP connection to the specified echo server. This class is not thread-safe.
 */
public class DaytimeConnection extends URLConnection {

    private static final String MIME_TYPE = "text/plain";
    private Socket theConnection;

    /**
     * Constructs an instance that can connect to the daytime server specified by the URL. All URL
     * parts except host are silently ignored.
     *
     * @param url The host name of the daytime server is the host part of this URL. All other parts of
     * the URL are silently ignored.
     */
    public DaytimeConnection(URL url) {
        super(url);
    }

    /**
     * @see java.net.URLConnection#connect()
     */
    @Override
    public void connect() throws IOException {
        if (!connected) {
            theConnection = new Socket(url.getHost(), DaytimeStreamHandler.PORT_NO);
        }
    }

    /**
     * @see java.net.URLConnection#getInputStream()
     */
    @Override
    public InputStream getInputStream() throws IOException {
        if (!connected) {
            connect();
        }
        return theConnection.getInputStream();
    }

    /**
     * @see java.net.URLConnection#getContent()
     */
    @Override
    public Object getContent() throws IOException {
        return getInputStream();
    }

    /**
     * @see java.net.URLConnection#getContentType()
     */
    @Override
    public String getContentType() {
        return MIME_TYPE;
    }
}
