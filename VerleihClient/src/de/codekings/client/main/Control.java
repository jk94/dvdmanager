/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.codekings.client.main;

import de.codekings.client.connection.ClientThread;
import de.codekings.common.Connection.Krypter;
import de.codekings.common.config.ConfigManager;
import java.io.File;
import java.io.FileNotFoundException;

/**
 *
 * @author Jan
 */
public class Control {

    private ConfigManager cfgManager;
    private Krypter krypter;

    public Control() {
        loadConfig();
        

    }

    public final void loadConfig() {
        try {
            File config = new File("./config/client.cfg");

            cfgManager = new ConfigManager(config);

            new ClientThread(null).HeartBeat(cfgManager.getConfigs().getProperty("ip"),
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

    public final void loadKrypter(){
        krypter = new Krypter();
        if(cfgManager.getConfigs().containsKey("generatenewkeys")){
            boolean generate = Boolean.parseBoolean(cfgManager.getConfigs().getProperty("generatenewkeys"));
            if(generate){
                krypter.generateKeyPair();
            }else{
                if(!krypter.loadKeyPair()){
                    System.out.println("Laden der KeyPairs fehlgeschlagen. Erzeuge neue Keys!");
                    krypter.generateKeyPair();
                }
            }
        }
        ClientThread c = new ClientThread(krypter);
        
    }
    
    
}
