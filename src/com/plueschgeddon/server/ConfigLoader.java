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
    String dbhost;
    String dbport;
    String dbname;
    String dbuser;
    String dbpass;

    public String[] getAll() throws IOException {
        Properties prop = new Properties();
        String propFileName = "config.properties";

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

        this.dbhost = prop.getProperty("dbhost");
        if(this.dbhost.equals("")) {
            this.dbhost = "localhost";
        }

        this.dbport = prop.getProperty("dbport");
        if(this.dbport.equals("")) {
            this.dbport = "3306";
        }

        this.dbname = prop.getProperty("dbname");
        this.dbuser = prop.getProperty("dbuser");
        this.dbpass = prop.getProperty("dbpass");

        String[] result = new String[7];
        result[0] = this.host;
        result[1] = this.port;
        result[2] = this.dbhost;
        result[3] = this.dbport;
        result[4] = this.dbname;
        result[5] = this.dbuser;
        result[6] = this.dbpass;

        return result;
    }
}
