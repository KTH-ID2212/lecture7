package se.kth.id2212.lecture7.ping;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class Pinger {

    public static void main(String[] args) throws MalformedURLException, IOException, InterruptedException {
        URL.setURLStreamHandlerFactory(new PingStreamHandlerFactory());
        URL echoServer = new URL(args[0]);
        URLConnection pinger = echoServer.openConnection();
        pinger.connect();
        Thread.sleep(5000);
        BufferedReader content = new BufferedReader(new InputStreamReader((InputStream) pinger.getContent()));
        String result = null;
        while((result = content.readLine()) != null) {
            System.out.println(result);
        }
    }
}
