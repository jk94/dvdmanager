/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controls;

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
    private final String DB_PATH = new File("../dvd_verleih.db").getAbsolutePath();            //System.getProperty("user.home") + "/" + "testdb.db";
    private static final Logger log = Logger.getLogger(DBController.class.getSimpleName());
    
    
    static {
        try {
            Class.forName("org.mysql.jdbc");
        } catch (ClassNotFoundException e) {
            log.log(Level.INFO, "Fehler beim Laden des JDBC-Treibers");
            log.log(Level.SEVERE, e.getMessage());
        }
    }

    private DBController() {
        initDBConnection();
    }

    public static DBController getInstance() {
        return dbcontroller;
    }
    
    public static Connection getConnection(){
        return connection;
    }

    public void initDBConnection() {
        try {
            if (connection != null) {
                connection.close();
                //return;
            }
            
            if (new File(DB_PATH).exists()) {
                log.log(Level.INFO, "Connect to Database...");
                //TODO Connection!!!!
                connection = DriverManager.getConnection("jdbc:mysql:" + DB_PATH);
                if (!connection.isClosed()) {
                    log.log(Level.INFO, "...Connected");
                }
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

        /*stmt.executeUpdate("DROP TABLE IF EXISTS books;"); 
         stmt.executeUpdate("CREATE TABLE books (author, title, publication, pages, price);"); 
         stmt.execute("INSERT INTO books (author, title, publication, pages, price) VALUES ('Paulchen Paule', 'Paul der Penner', '2001-05-06', '1234', '5.67')"); 
         */
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
    
    public static ResultSet executeQuery(String sql){
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
