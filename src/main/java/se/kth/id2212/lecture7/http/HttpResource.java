package se.kth.id2212.lecture7.http;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.image.ImageProducer;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * Handles a HTP resource.
 */
public class HttpResource {

    private URL resourceUrl;

    /**
     * Downloads the resource at the specified url.
     *
     * @param url The url of the resource that shall be downloaded.
     * @throws java.net.MalformedURLException If the specified url is invalid.
     */
    public HttpResource(String url) throws MalformedURLException {
        this.resourceUrl = new URL(url);
    }

    /**
     * Loads the resource specified by this instance. If the resource is an image, it is displayed.
     * If the resource is an <code>InputStream</code> its content is printed to
     * <code>System.out</code>. If the resource is something else, it is ignored.
     */
    public void loadResource() throws IOException {
        final Image image;
        System.out.println("MIME: "
                           + resourceUrl.openConnection().getContentType());
        Object resource = resourceUrl.getContent();
        if (resource instanceof ImageProducer) {
            handleImageResource(resource);
        } else if (resource instanceof InputStream) {
            handleStreamResource(resource);
        }
    }

    private void handleStreamResource(Object resource) throws IOException {
        BufferedReader fromStream = new BufferedReader(
                new InputStreamReader((InputStream) resource));
        String line = null;
        while ((line = fromStream.readLine()) != null) {
            System.out.println(line);
        }
    }

    private void handleImageResource(Object resource) {
        JFrame f = new JFrame("Downloaded Image");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        final Image image = f.createImage((ImageProducer) resource);
        try {
            MediaTracker tracker = new MediaTracker(f);
            tracker.addImage(image, 1);
            tracker.waitForID(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        f.setContentPane(new JPanel() {
            @Override
            public void paintComponent(Graphics g) {
                g.drawImage(image, 0, 0, this);
            }
        });
        f.setSize(image.getWidth(f), image.getHeight(f));
        f.setVisible(true);
    }

    /**
     * Starts the application.
     *
     * @param args Takes one command line argument, which is the URL identifying the resource that
     *             shall be downloaded.
     * @throws MalformedURLException If the specified URL is not understood.
     * @throws IOException           If unable to download the specified resource.
     */
    public static void main(String[] args) throws MalformedURLException,
                                                  IOException {
        new HttpResource(args[0]).loadResource();
    }
}
