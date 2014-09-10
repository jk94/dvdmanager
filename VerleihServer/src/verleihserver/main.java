/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package verleihserver;

import Enumerators.LogEnum;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Jan
 */
public class main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        try {
            PreparedStatement p = DBController.getConnection().prepareStatement("SELECT * FROM tbl_user");
            ResultSet rs = DBController.executeQuery(p);

            System.out.println(rs.getString("surname"));
            System.out.println(rs.getString("name"));
            System.out.println(new Date(rs.getLong("birthdate")));
        } catch (SQLException e) {
            log(LogEnum.ERROR, e.getMessage(), e);
        }
        VerleihServer.getInstance().start();
    }

    public static void log(Enumerators.LogEnum e, String s, Object o) {
        System.out.println("[" + e.name() + "] " + o.getClass() + ": " + s);
    }
}
