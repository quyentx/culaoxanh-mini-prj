package main;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class CommonOperations {
    /*
     * Read from configuration file
     */
    public static Properties readConfig() throws IOException {
        // Create new properties variable
        Properties p = new Properties();
        // Read object properties file
        InputStream stream = new FileInputStream("./config.properties");
        // Load input stream file
        p.load(stream);

        //Close input stream
        stream.close();
        return p;
    }
}
