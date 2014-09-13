/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controls;

import Enumerators.LogEnum;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import verleihserver.main;

/**
 *
 * @author Jan
 */
public final class DBController {

    private static final DBController dbcontroller = new DBController();
    private static Connection connection;
    private final String DB_PATH = new File("../dvd_verleih.db").getAbsolutePath();            //System.getProperty("user.home") + "/" + "testdb.db";
    
    static {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            System.err.println("Fehler beim Laden des JDBC-Treibers");
            e.printStackTrace();
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
                return;
            }
            System.out.println("Pfad: " +DB_PATH);
            if (new File(DB_PATH).exists()) {
                main.log(LogEnum.INFO, "Connect to Database...", this);
                connection = DriverManager.getConnection("jdbc:sqlite:" + DB_PATH);
                if (!connection.isClosed()) {
                    main.log(LogEnum.INFO, "...Connected", this);
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
                            main.log(LogEnum.INFO, "Connection to Database closed", this);
                        }
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
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
            connection.close();
        } catch (SQLException ex) {
            main.log(LogEnum.ERROR, ex.getMessage(), DBController.getInstance());
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
            connection.close();
        } catch (SQLException ex) {
            main.log(LogEnum.ERROR, ex.getMessage(), DBController.getInstance());
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
            connection.close();
        } catch (SQLException e) {
            main.log(LogEnum.ERROR, "Couldn't execute SQL-Statement. " + e.getMessage(), DBController.getInstance());
            e.printStackTrace();
        }
    }

}
