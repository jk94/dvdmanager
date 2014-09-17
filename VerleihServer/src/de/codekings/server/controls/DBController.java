/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.codekings.server.controls;

import de.codekings.common.log.LogInitialiser;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Jan
 */
public final class DBController {

    private static final DBController dbcontroller = new DBController();
    private static Connection connection;
    private String dbHost, dbPort, dbName, dbUser, dbPass;
    private static final Logger log = Logger.getLogger(DBController.class.getSimpleName());

    static {
        LogInitialiser.initialiseLog(log, "DBController");
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            log.log(Level.INFO, "Fehler beim Laden des JDBC-Treibers");
            log.log(Level.SEVERE, e.getMessage());
        }
    }

    public DBController(String dbHost, String dbPort, String dbName, String dbUser, String dbPass) {
        this.dbHost = dbHost;
        this.dbPort = dbPort;
        this.dbName = dbName;
        this.dbUser = dbUser;
        this.dbPass = dbPass;
    }

    private DBController() {

    }

    public static DBController getInstance() {
        return dbcontroller;
    }

    public static Connection getConnection() {
        return connection;
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

    public void initDBConnection() {
        try {
            if (connection != null) {
                return;
            }
            log.log(Level.INFO, "Connect to Database...");
            //TODO Connection!!!!
            connection = DriverManager.getConnection("jdbc:mysql://" + dbHost + ":" + dbPort + "/" + dbName + "?" + "user=" + dbUser + "&" + "password=" + dbPass);
            if (!connection.isClosed()) {
                log.log(Level.INFO, "...Connected");
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        Runtime.getRuntime().addShutdownHook(new Thread() {
            public void run() {
                try {
                    if (!connection.isClosed() && connection != null) {
                        connection.close();
                        if (connection.isClosed()) {
                            log.log(Level.WARNING, "Connection to Database closed");
                        }
                    }
                } catch (SQLException e) {
                    log.log(Level.SEVERE, e.getMessage());
                }
            }
        });
    }

    public static void createClearDataBase() {

    }

    public static ResultSet executeQuery(PreparedStatement pst) {
        ResultSet ergebnisRS = null;
        try {
            if (!(connection != null) || connection.isClosed()) {
                getInstance().initDBConnection();
            }
            ergebnisRS = pst.executeQuery();

        } catch (SQLException ex) {
            log.log(Level.SEVERE, ex.getMessage());
        }
        return ergebnisRS;
    }

    public static ResultSet executeQuery(String sql) {
        ResultSet ergebnisRS = null;
        try {
            if (!(connection != null) || connection.isClosed()) {
                getInstance().initDBConnection();
            }
            Statement stmt = connection.createStatement();
            ergebnisRS = stmt.executeQuery(sql);

        } catch (SQLException ex) {
            log.log(Level.SEVERE, ex.getMessage());
        }
        return ergebnisRS;
    }

    public static void executeBatch(PreparedStatement pst) {
        try {
            if (connection.isClosed()) {
                getInstance().initDBConnection();
            }
            connection.setAutoCommit(true);
            pst.executeBatch();
            connection.setAutoCommit(false);
        } catch (SQLException e) {
            log.log(Level.SEVERE, "Couldn't execute SQL-Statement. {0}", e.getMessage());
        }
    }

    public void closeConnection() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }

}
