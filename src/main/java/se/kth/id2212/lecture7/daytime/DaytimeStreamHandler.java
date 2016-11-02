package se.kth.id2212.lecture7.daytime;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLStreamHandler;

/**
 * A read-only stream handler that uses the TCP based echo protocol to ping the specified host.
 */
public class DaytimeStreamHandler extends URLStreamHandler {

    static final int PORT_NO = 13;
    static final String PROTOCOL_NAME = "daytime";
    private static final String PROTOCOL_DELIMITER = "://";
    private static final String PORT_DELIMITER = ":";

    /**
     * Creates a <code>URLConnection</code> that can be used to read the time from the specified
     * host.
     *
     * @param url The host with the daytime server.
     * @return A <code>URLConnection</code> that can be used to request the daytime from the
     *         specified host.
     * @throws IOException If unable to open a connection to the specified host.
     */
    @Override
    protected URLConnection openConnection(URL url) throws IOException {
        return new DaytimeConnection(url);
    }

    /**
     * Protocol is alway <code>daytime</code>, port is always 13 and all fields except protocol,
     * host and port are null.
     *
     * @see java.net.URLStreamHandler#parseURL(java.net.URL, java.lang.String, int, int)
     */
    @Override
    protected void parseURL(URL url, String spec, int start, int limit) {
        super.parseURL(url, spec, start, limit);
        setURL(url, PROTOCOL_NAME, url.getHost(), PORT_NO, null, null, null, null, null);
    }

    /**
     * Protocol is alway <code>daytime</code> and port is always 13. There are no fields except
     * protocol, host and port.
     *
     * @see java.net.URLStreamHandler#toExternalForm(java.net.URL)
     */
    @Override
    protected String toExternalForm(URL url) {
        StringBuilder builder = new StringBuilder();
        builder.append(PROTOCOL_NAME);
        builder.append(PROTOCOL_DELIMITER);
        builder.append(url.getHost());
        builder.append(PORT_DELIMITER);
        builder.append(PORT_NO);
        return builder.toString();
    }
}
