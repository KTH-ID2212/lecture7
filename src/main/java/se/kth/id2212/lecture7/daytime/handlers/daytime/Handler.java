package se.kth.id2212.lecture7.daytime.handlers.daytime;

import java.net.URL;
import java.net.URLConnection;
import java.net.URLStreamHandler;

/**
 * A read-only stream handler that uses the TCP based daytime protocol to get
 * the current time from the specified host.
 */
public class Handler extends URLStreamHandler {
    /**
     * Creates a <code>URLConnection</code> that can be used to read the
     * time from the specified host.
     *
     * @param url The host with the daytime server.
     * @return A <code>URLConnection</code> that can be used to request the daytime from the
     *         specified host.
     */
    @Override
    protected URLConnection openConnection(URL url) {
        return new DaytimeConnection(url);
    }

}
