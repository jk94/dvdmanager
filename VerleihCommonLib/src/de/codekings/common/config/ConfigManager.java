/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.codekings.common.config;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
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

        }
        return properties;
    }

}
