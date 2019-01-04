package com.fosun.esutil.config;

import java.io.*;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @ClassName PropertiesUtil
 * @Description TODO
 * @Author zhangyq
 * @Date 2019/1/2 16:43
 **/
public class PropertiesUtil {

    final Map<String, String> kvPairs = new ConcurrentHashMap<>();

    final File confFile;

    private PropertiesUtil(String confPath) throws IOException {
        confFile = new File(confPath);
        load();
    }

    void load() throws IOException {
        InputStream is = null;
        is = new FileInputStream(confFile);
        Properties properties = new Properties();
        properties.load(is);

        for (Map.Entry<Object, Object> entry : properties.entrySet()) {
            kvPairs.put((String) entry.getKey(), (String) entry.getValue());
        }
        if (is != null) {
            is.close();
        }
    }

    public String get(String key) {
        String value = kvPairs.get(key);
        if (value == null) {
            return "";
        }
        return value.trim();
    }

    public String get(String key, String defaultValue) {
        String value = kvPairs.get(key);
        if (value == null) {
            return defaultValue;
        }
        return value.trim();
    }


    public static PropertiesUtil getProperties(){
        try {
            return new PropertiesUtil("target/classes/es.properties");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
