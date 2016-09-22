package pasman.model.service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.Properties;

/**
 * Created by Max on 04.09.2016.
 */
public class ReadConfigMain {
    String result = "";
    static InputStream inputStream;

    public String getPropValues() throws IOException {
        try {
            Properties prop = new Properties();
            String propFileName = "config.properties";

            inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);

            if (inputStream != null) {
                prop.load(inputStream);
            } else {
                throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath");
            }
            String key = prop.getProperty("key");

            result = key;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            inputStream.close();
        }
        return result;
    }
}
