package com.plueschgeddon.server;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class ConfigLoader {

    private static ConfigLoader instance;
    private static Properties properties;

    private ConfigLoader() throws IOException {
        properties = new Properties();
        String propFileName = "config.properties";
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);

        if (inputStream != null) {
            properties.load(inputStream);
        } else {
            throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath");
        }
    }

    public static ConfigLoader getInstance() {
        if (ConfigLoader.instance == null) {
            try {
                ConfigLoader.instance = new ConfigLoader();
            } catch (IOException ie) {
                ie.printStackTrace();
            }
        }
        return ConfigLoader.instance;
    }

    public Map<String, String> getAll() throws Exception {
        Map<String, String> config = new HashMap<>();

        for (String key : properties.stringPropertyNames()) {
            if(key.equals("host") && properties.getProperty(key).equals("localhost")) {
                config.put(key, InetAddress.getLocalHost().getHostAddress());
            } else {
                config.put(key, properties.getProperty(key));
            }
        }

        return config;
    }
}
