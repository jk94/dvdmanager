/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.codekings.server.controls;

import de.codekings.common.datacontents.Film;
import de.codekings.common.json.JSON_Parser;
import java.io.File;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 *
 * @author Jan
 */
public class Control {

    private static final Control theControl = new Control();

    public Control() {
        //getFilms();
    }

    public static Control getInstance() {
        return theControl;
    }

    public void readDBConfig(String path) {
        File f = new File(path);
        if(f.exists()){
            JSON_Parser.getInstance().parseStringToObject(path, DBConfig.class);
        }else{
            System.err.println("Keine Konfigurationsdatei der Datenbank vorhanden.");
            System.err.println("Server wird beendet!");
            System.exit(0);
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
    
    private class DBConfig{

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
