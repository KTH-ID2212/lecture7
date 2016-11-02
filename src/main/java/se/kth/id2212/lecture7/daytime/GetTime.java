package se.kth.id2212.lecture7.daytime;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class GetTime {
    private static final String DAYTIME_SERVER = "time.nist.gov";
    public static void main(String[] args) throws MalformedURLException, IOException,
                                                  InterruptedException {
        URL.setURLStreamHandlerFactory(new DaytimeStreamHandlerFactory());
        URL daytimeServer = new URL("daytime://" + DAYTIME_SERVER);
        URLConnection timeServer = daytimeServer.openConnection();
        timeServer.connect();
        Thread.sleep(5000);
        BufferedReader content = new BufferedReader(new InputStreamReader((InputStream) timeServer.
                getContent()));
        String result = null;
        while ((result = content.readLine()) != null) {
            System.out.println(result);
        }
    }
}
