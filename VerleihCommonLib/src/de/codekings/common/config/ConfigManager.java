/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.codekings.common.config;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Iterator;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Jan
 */
public class ConfigManager {

    private final Properties einstellungen = new Properties();
    private final Logger log = Logger.getLogger(ConfigManager.class.getSimpleName());

    public ConfigManager(File f) throws FileNotFoundException{
        try {
            readConfig(f);
        } catch (FileNotFoundException e) {
            throw e;
        }
    }

    public ConfigManager(){
    }

    public final Properties readConfig(File f) throws FileNotFoundException {
        Properties properties = null;
        if (f.exists()) {
            try {
                properties = new Properties();
                try (BufferedInputStream stream = new BufferedInputStream(new FileInputStream(f))) {
                    properties.load(stream);
                }
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
            appendConfig(properties);
        } else {
            throw new FileNotFoundException(f.getAbsolutePath() + " could not be found!");
        }
        return properties;
    }

    private void appendConfig(Properties newProperties) {
        for (String key : newProperties.stringPropertyNames()) {
            einstellungen.put(key, newProperties.getProperty(key));
        }
    }

    /**
     * Speichert die geladenen Einstellungen.
     *
     * @param dateiname DateiNAME der zu speichernden Datei
     */
    public void writeConfig(String dateiname) {
        writeConfig(einstellungen, dateiname);
    }

    private void writeConfig(Properties p, String dateiname) {
        try {
            File ordner = new File("./config/");
            if (ordner.exists() && ordner.isDirectory()) {
                try (OutputStream stream = new FileOutputStream(new File(ordner.getPath() + "/" + dateiname))) {
                    System.out.println(new File(ordner.getPath() + dateiname).getAbsoluteFile());
                    p.store(stream, "");
                }
            } else {
                if (ordner.mkdir()) {
                    writeConfig(p, dateiname);
                }
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void writeDefaultServerConfig() {
        Properties property = new Properties();
        property.put("dbHost", "localhost");
        property.put("dbPort", "3306");
        property.put("dbDatabase", "codekings-dvd");
        property.put("dbUser", "ckdbuser");
        property.put("dbPassword", "test123");
        property.put("logpath", "./logs/");

        writeConfig(property, "server.cfg");
    }
    
    public void writeDefaultClientConfig(){
        Properties property = new Properties();
        property.put("ip", "127.0.0.1");
        property.put("port", "1234");
        
        writeConfig(property, "client.cfg");
    }

    public Properties getConfigs() {
        return einstellungen;
    }
}
