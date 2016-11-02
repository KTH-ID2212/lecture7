package se.kth.id2212.lecture7.daytime;

import java.net.URLStreamHandler;
import java.net.URLStreamHandlerFactory;

/**
 * A <code>URLStreamHandlerFactory</code> that can create a stream handler factory, which know how
 * to connect to a daytime server.
 */
public class DaytimeStreamHandlerFactory implements URLStreamHandlerFactory {

    @Override
    public URLStreamHandler createURLStreamHandler(String protocol) {
        try {
            return (URLStreamHandler) Class.forName(DaytimeStreamHandler.class.getCanonicalName()).newInstance();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
