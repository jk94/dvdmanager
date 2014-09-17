/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.codekings.common.config;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.MessageFormat;
import java.util.Properties;

/**
 *
 * @author Jan
 */
public class ConfigManager {

    public Properties readConfig(String datei) {
        Properties properties = null;
        try {
            properties = new Properties();
            try (BufferedInputStream stream = new BufferedInputStream(new FileInputStream(datei))) {
                properties.load(stream);
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return properties;
    }

    public void writeConfig(Properties p, File f) {
        try {
            if (!f.exists()) {
                OutputStream stream = new FileOutputStream(f);
                p.store(stream, "");

                System.out.println(MessageFormat.format("Hallo", "Tschüss"));

            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void writeDefaultDBConfig() {
        Properties property = new Properties();
        property.put("dbHost", "localhost");
        property.put("dbPort", "3306");
        property.put("dbDatabase", "codekings-dvd");
        property.put("dbUser", "ckdbuser");
        property.put("dbPassword", "test123");

        writeConfig(property, new File("DB.conf.properties"));
    }
}
