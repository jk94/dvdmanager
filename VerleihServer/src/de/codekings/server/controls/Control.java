/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.codekings.server.controls;

import de.codekings.common.Connection.Krypter;
import de.codekings.common.config.ConfigManager;
import de.codekings.common.datacontents.Film;
import de.codekings.common.json.JSON_Parser;
import de.codekings.common.log.LogInitialiser;
import java.io.File;
import java.io.FileNotFoundException;
import java.sql.ResultSet;
import java.util.ArrayList;
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
    private Krypter krypter;

    public Control() {
        //Initialisiere Log für alle!
        log = Logger.getLogger(Control.class.getSimpleName());
        loadConfig();

        //Log benutzerdefinieren
        LogInitialiser.initialiseLog(log, cfgManager.getConfigs().getProperty("logpath"), "DVD_Server");

        //Initialisiere Datenbankverbindung
        loadDatabase();

        //Initialisiere Keys für Verschlüsselung
        loadKrypter();
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

    public Krypter getKrypter() {
        return krypter;
    }

    public void setControl(Control c) {
        theControl = c;
    }

    public Logger getLogger() {
        return log;
    }

    public final void loadKrypter() {
        krypter = new Krypter();
        if (cfgManager.getConfigs().containsKey("generatenewkeys")) {
            boolean generate = Boolean.parseBoolean(cfgManager.getConfigs().getProperty("generatenewkeys"));
            if (generate) {
                krypter.generateKeyPair();
            } else {
                if (!krypter.loadKeyPair()) {
                    log.log(Level.WARNING, "Laden der KeyPairs fehlgeschlagen. Erzeuge neue Keys!");
                    krypter.generateKeyPair();
                }
            }
        }
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

    public final void getFilms() {
        ArrayList<Film> films = new ArrayList<>();
        ResultSet rs = DBSQL_Control.GetALLFilmsFromDB();
        try {

            while (rs.next()) {
                Film f = new Film(rs.getInt("FI_ID"));

                boolean vorhanden = false;
                for (Film fi : films) {
                    if (fi.getFILMID() == f.getFILMID()) {
                        vorhanden = true;
                        f = fi;
                        break;
                    }
                }
                if (vorhanden) {
                    f.addGenre(rs.getString("genrename"));
                    continue;
                }
                films.add(f);
                f.loadFilmFromDatabase(rs);
            }
            //System.out.println(JSON_Parser.getInstance().parseObjectOut(films));
            rs.close();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

    }

    private class DBConfig {

        private String dbHost, dbPort, dbName, dbUser, dbPass;

        public DBConfig() {
        }

        public String getDbHost() {
            return dbHost;
        }

        public void setDbHost(String dbHost) {
            this.dbHost = dbHost;
        }

        public String getDbPort() {
            return dbPort;
        }

        public void setDbPort(String dbPort) {
            this.dbPort = dbPort;
        }

        public String getDbName() {
            return dbName;
        }

        public void setDbName(String dbName) {
            this.dbName = dbName;
        }

        public String getDbUser() {
            return dbUser;
        }

        public void setDbUser(String dbUser) {
            this.dbUser = dbUser;
        }

        public String getDbPass() {
            return dbPass;
        }

        public void setDbPass(String dbPass) {
            this.dbPass = dbPass;
        }

    }
}
