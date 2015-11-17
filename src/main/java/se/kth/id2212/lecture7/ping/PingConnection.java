package se.kth.id2212.lecture7.ping;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Opens a TCP connection to the specified echo server. This class is not thread-safe.
 */
public class PingConnection extends URLConnection {

    private static final String MIME_TYPE = "text/plain";
    private static final String MSG = Long.toHexString(new Random().nextLong());
    private List<String> answers = new ArrayList<>();

    /**
     * Constructs an instance that can connect to the echo server specified by the URL. All URL
     * parts except host are silently ignored.
     *
     * @param url The host name of the echo server is the host part of this URL. All other parts of
     * the URL are silently ignored.
     */
    public PingConnection(URL url) {
        super(url);
    }

    /**
     * @see java.net.URLConnection#connect()
     */
    @Override
    public void connect() throws IOException {
        if (!connected) {
            final Socket theConnection = new Socket(url.getHost(), PingStreamHandler.PORT_NO);
            new Thread(new Receiver(theConnection)).start();
            connected = true;
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
        StringBuilder builder = new StringBuilder();
        for (String answer : answers) {
            builder.append(answer);
            builder.append("\n");
        }
        return new ByteArrayInputStream(builder.toString().getBytes());
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

    private class Receiver implements Runnable {

        private Socket theConnection;

        private Receiver(Socket theConnection) {
            this.theConnection = theConnection;
        }

        @Override
        public void run() {
            long sendTime = 0;
            long pingTime = 0;
            try (PrintWriter out = new PrintWriter(theConnection.getOutputStream(), true);
                    BufferedReader in = new BufferedReader(new InputStreamReader(theConnection.getInputStream()));) {
                for (;;) {
                    sendTime = System.currentTimeMillis();
                    out.println(MSG);
                    out.flush();
                    if (in.readLine().equals(MSG)) {
                        pingTime = System.currentTimeMillis() - sendTime;
                        answers.add(createSuccessMsg(pingTime));
                    }
                    Thread.sleep(1000);
                }
            } catch (Exception e) {
                answers.add(e.getMessage());
            }
        }

        private String createSuccessMsg(long pingTime) {
            StringBuilder builder = new StringBuilder();
            builder.append(Long.SIZE);
            builder.append(" bits from ");
            builder.append(getURL().getHost());
            builder.append(" in ");
            builder.append(pingTime);
            builder.append(" ms.");
            return builder.toString();
        }
    }
}
