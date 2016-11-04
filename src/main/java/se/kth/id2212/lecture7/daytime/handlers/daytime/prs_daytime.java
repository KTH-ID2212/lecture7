package se.kth.id2212.lecture7.daytime.handlers.daytime;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ContentHandler;
import java.net.URLConnection;

/**
 * Handles content with the mime type daytime/prs.daytime
 */
public class prs_daytime extends ContentHandler {

    /**
     * @see ContentHandler#getContent(java.net.URLConnection) Returns a <code>String</code> with the
     * current time, in an unspecified format.
     *
     * @param urlc The connection from which the daytime is read.
     * @return The current time.
     * @throws IOException If unable to read from time server.
     */
    @Override
    public Object getContent(URLConnection urlc) throws IOException {
        BufferedReader content = new BufferedReader(new InputStreamReader(
                (InputStream) urlc.getInputStream()));
        StringBuilder msg = new StringBuilder();
        String lastPart = null;
        while ((lastPart = content.readLine()) != null) {
            msg.append(lastPart);
        }
        return msg.toString();
    }

}
