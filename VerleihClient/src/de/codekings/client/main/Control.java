/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.codekings.client.main;

import de.codekings.client.Controls.ContentManager;
import de.codekings.client.GUI.MainFrame.TemplateController;
import de.codekings.client.connection.ClientThread;
import de.codekings.common.Connection.Krypter;
import de.codekings.common.config.ConfigManager;
import java.io.File;
import java.io.FileNotFoundException;
import javafx.scene.layout.Pane;

/**
 *
 * @author Jan
 */
public class Control {

    private ConfigManager cfgManager;
    private Krypter krypter;
    private ContentManager contManager;
    private static Control control;

    public Control() {
        loadConfig();
        loadKrypter();
        loadContentManager();
    }

    public final void loadConfig() {
        try {
            File config = new File("./config/client.cfg");

            cfgManager = new ConfigManager(config);

            /*new ClientThread(null).HeartBeat(cfgManager.getConfigs().getProperty("ip"),
             Integer.parseInt(cfgManager.getConfigs().getProperty("standardport")));*/
        } catch (FileNotFoundException ex) {
            cfgManager = new ConfigManager();
            cfgManager.writeDefaultClientConfig();
            System.err.println("DB Config Datei nicht gefunden.\n"
                    + "Default Nachricht erstellt.\n"
                    + "Bitte Server neu starten!");
            System.exit(0);
        }
    }

    public final void loadKrypter() {
        krypter = new Krypter();
        if (cfgManager.getConfigs().containsKey("generatenewkeys")) {
            boolean generate = Boolean.parseBoolean(cfgManager.getConfigs().getProperty("generatenewkeys"));
            if (generate) {
                krypter.generateKeyPair();
            } else {
                if (!krypter.loadKeyPair()) {
                    System.out.println("Laden der KeyPairs fehlgeschlagen. Erzeuge neue Keys!");
                    krypter.generateKeyPair();
                }
            }
        }

    }

    public ConfigManager getCfgManager() {
        return cfgManager;
    }

    public Krypter getKrypter() {
        return krypter;
    }

    public static final void setControl(Control c){
        control = c;
    }
    
    public static final Control getControl(){
        return control;
    }
    
    public final void getPublicKey(){
        ClientThread c = new ClientThread(krypter);
        c.requestForPubKeyFromServer(cfgManager.getConfigs().getProperty("ip"),
                Integer.parseInt(cfgManager.getConfigs().getProperty("standardport")));
    }

    public final void loadContentManager() {
        this.contManager = new ContentManager();
    }
    
    public final ContentManager getContentManager(){
        return this.contManager;
    }
}
