package se.kth.id2212.lecture7.daytime;

import java.io.IOException;
import java.net.URL;

/**
 * A client using the daytime protocol to get the current time.
 */
public class GetCurrentTime {
    private static final String DAYTIME_SERVER = "time.nist.gov";

    /**
     * Reads the current time and prints it to std out.
     *
     * @param args There are no command line parameters.
     */
    public static void main(String[] args) {
        try {
            Object daytimeResponse = new URL("daytime://" + DAYTIME_SERVER).getContent();

            if (daytimeResponse instanceof String) {
                System.out.println("The time is " + daytimeResponse);
            } else {
                System.out.println("Could not get current time.");
            }
        } catch (IOException ioe) {
            System.out.println("Could not get current time.");
        }
    }
}
