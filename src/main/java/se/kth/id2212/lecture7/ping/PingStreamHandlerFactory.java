package se.kth.id2212.lecture7.ping;

import java.net.URLStreamHandler;
import java.net.URLStreamHandlerFactory;

/**
 * A <code>URLStreamHandlerFactory</code> that can create a stream handler factory, which know how
 * to connect to an echo server.
 */
public class PingStreamHandlerFactory implements URLStreamHandlerFactory {

    @Override
    public URLStreamHandler createURLStreamHandler(String protocol) {
        try {
            return (URLStreamHandler) Class.forName(PingStreamHandler.class.getCanonicalName()).newInstance();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
