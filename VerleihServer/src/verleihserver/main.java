/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package verleihserver;

import connection.VerleihServer;
import java.io.File;

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
        json.json_parser.getInstance().parseObjectOut(new Testklasse());
        Testklasse t;
        t = json.json_parser.getInstance().parseObjectIn(new File("C:/Users/Jan/Desktop/test.txt"), Testklasse.class);
        System.out.println(t.getA());
        System.out.println(t.getImgb().length);
        /*try {
            PreparedStatement p = DBController.getConnection().prepareStatement("SELECT * FROM tbl_user");
            ResultSet rs = DBController.executeQuery(p);

            System.out.println(rs.getString("surname"));
            System.out.println(rs.getString("name"));
            System.out.println(new Date(rs.getLong("birthdate")));
        } catch (SQLException e) {
            log(LogEnum.ERROR, e.getMessage(), e);
        }*/
        VerleihServer.getInstance().start();
    }

    public static void log(Enumerators.LogEnum e, String s, Object o) {
        System.out.println("[" + e.name() + "] " + o.getClass() + ": " + s);
    }
}
