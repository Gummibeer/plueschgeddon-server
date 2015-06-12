package com.plueschgeddon.server;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.util.Properties;

/**
 * @author Crunchify.com
 */

public class ConfigLoader {

    String host;
    String port;

    public String[] getAll() throws IOException {
        Properties prop = new Properties();
        String propFileName = "sample.config.properties";

        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);

        if (inputStream != null) {
            prop.load(inputStream);
        } else {
            throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath");
        }

        this.host = prop.getProperty("host");
        if(this.host.equals("") || this.host.equals("localhost")) {
            this.host = InetAddress.getLocalHost().getHostAddress();
        }

        this.port = prop.getProperty("port");
        if(this.port.equals("")) {
            this.port = "55555";
        }

        String[] result = new String[2];
        result[0] = this.host;
        result[1] = this.port;

        return result;
    }
}
