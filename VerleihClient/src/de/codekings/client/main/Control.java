/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.codekings.client.main;

import de.codekings.client.connection.ClientThread;
import de.codekings.common.config.ConfigManager;
import java.io.File;
import java.io.FileNotFoundException;

/**
 *
 * @author Jan
 */
public class Control {

    private ConfigManager cfgManager;

    public Control() {
        try {
            File config = new File("./config/client.cfg");
            
            cfgManager = new ConfigManager(config);

            new ClientThread().HeartBeat(cfgManager.getConfigs().getProperty("ip"),
                    Integer.parseInt(cfgManager.getConfigs().getProperty("port")));

        } catch (FileNotFoundException ex) {
            cfgManager = new ConfigManager();
            cfgManager.writeDefaultClientConfig();
            System.err.println("DB Config Datei nicht gefunden.\n"
                    + "Default Nachricht erstellt.\n"
                    + "Bitte Server neu starten!");
            System.exit(0);
        }
    }

}
