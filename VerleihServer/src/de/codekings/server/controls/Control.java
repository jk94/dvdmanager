/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.codekings.server.controls;

import de.codekings.common.config.ConfigManager;
import de.codekings.common.log.LogInitialiser;
import de.codekings.server.connection.VerleihServer;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Jan
 */
public class Control {

    private static Control theControl;
    private static Logger log;
    private ConfigManager cfgManager;
    private DBController dbManager;
    private VerleihServer secureVerleihserver, unsecureVerleihserver;
    private CoverManager coverManager;

    public Control() {
        //Initialisiere Log für alle!
        log = Logger.getLogger(Control.class.getSimpleName());
        loadConfig();

        //Log benutzerdefinieren
        LogInitialiser.initialiseLog(log, cfgManager.getConfigs().getProperty("logpath"), "DVD_Server");

        //Initialisiere Datenbankverbindung
        loadDatabase();

        //Initialisiere CoverManager
        loadCoverManager();
        
        //Initialisiere unverschlüsselten Server und startet ihn.
        runServer();

        theControl = this;

    }

    public static Control getInstance() {
        return theControl;
    }

    public ConfigManager getCfgManager() {
        return cfgManager;
    }

    public DBController getDbManager() {
        return dbManager;
    }

    public void setControl(Control c) {
        theControl = c;
    }

    public Logger getLogger() {
        return log;
    }
    
    public CoverManager getCoverManager(){
        return coverManager;
    }

    public final void loadConfig() {
        //Initialisiere ConfigManager
        try {

            //Einlesen der Konfigurationsdatei
            File f = new File("./config/server.cfg");
            cfgManager = new ConfigManager(f);

        } catch (FileNotFoundException e) {
            cfgManager = new ConfigManager();
            cfgManager.writeDefaultServerConfig();
            log.log(Level.WARNING, "DB Config Datei nicht gefunden.\n"
                    + "Default Nachricht in '/configs/' erstellt.\n"
                    + "Bitte Server neu starten!");
            System.exit(0);
        }
    }

    public final void loadDatabase() {
        Properties p = cfgManager.getConfigs();

        //DatenbankManager wird initialisiert
        dbManager = new DBController(p.getProperty("dbHost"), p.getProperty("dbPort"), p.getProperty("dbDatabase"),
                p.getProperty("dbUser"), p.getProperty("dbPassword"), log);

        //Verbindung zur Datenbank öffnen (testen)
        try {
            dbManager.initDBConnection();
        } catch (RuntimeException e) {
            System.out.println("Verbindung zur Datenbank nicht möglich. "
                    + "Überprüfen Sie die Konfigurationsdatei: 'config/server.cfg'");
        }
    }

    public final void loadCoverManager() {
        this.coverManager = new CoverManager();
        while(!coverManager.isFirstLoaded()){
            try {
                Thread.sleep(100l);
            } catch (InterruptedException ex) {
                Logger.getLogger(Control.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private void runServer() {
        unsecureVerleihserver = new VerleihServer(Integer.parseInt(cfgManager.getConfigs().getProperty("port")));
        unsecureVerleihserver.start();
    }

}
