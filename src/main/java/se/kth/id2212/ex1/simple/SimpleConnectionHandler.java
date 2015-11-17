package se.kth.id2212.ex1.simple;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.Socket;

public class SimpleConnectionHandler extends Thread {
    private Socket clientSocket;

    public SimpleConnectionHandler(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    public void run() {
        BufferedInputStream in;
        BufferedOutputStream out;

        try {
            in = new BufferedInputStream(clientSocket.getInputStream());
            out = new BufferedOutputStream(clientSocket.getOutputStream());
        } catch (IOException e) {
            System.out.println(e.toString());
            return;
        }
        for (;;) {
            try {
                byte[] msg = new byte[256];
                int bytesRead = 0;
                int n;

                while ((n = in.read(msg, bytesRead, 256)) != -1) {
                    bytesRead += n;
                    if (bytesRead == 256) {
                        break;
                    }
                    if (in.available() == 0) {
                        break;
                    }
                }
                out.write(msg, 0, bytesRead);
                out.flush();
            } catch (IOException e) {
                System.out.println(e.toString());
            }
        }
    }
}
