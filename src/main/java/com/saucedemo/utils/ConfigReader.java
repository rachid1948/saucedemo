package com.saucedemo.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.io.*;
import java.util.Properties;

/** ConfigReader - Lecture de config.properties (surcharge par -Dkey=value). */
public class ConfigReader {
    private static final Logger logger = LogManager.getLogger(ConfigReader.class);
    private static final Properties p = new Properties();
    static {
        String env = System.getProperty("env", "dev");
        String fileName = env.equals("dev") ? "config.properties" : "config-" + env + ".properties";
        logger.info("Environnement : {} | Fichier : {}", env, fileName);
        try (InputStream in = ConfigReader.class.getClassLoader()
                .getResourceAsStream(fileName)) {
            if (in != null) p.load(in);
            else logger.warn("Fichier introuvable : {}", fileName);
        } catch (IOException e) { logger.error(e.getMessage()); }
    }
    private ConfigReader() {}
    public static String get(String k)              { String v = System.getProperty(k, p.getProperty(k)); if(v==null) throw new RuntimeException("Cle manquante: "+k); return v.trim(); }
    public static String get(String k, String def)  { return System.getProperty(k, p.getProperty(k, def)).trim(); }
}
