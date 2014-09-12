/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package verleihserver;

import Enumerators.LogEnum;
import connection.VerleihServer;
import contents.Film;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import javax.imageio.ImageIO;

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
        new Testklasse();
        //json.json_parser.getInstance().parseObjectOut(new Testklasse());
        //Testklasse t;
        //t = json.json_parser.getInstance().parseObjectIn(new File("C:/Users/Jan/Desktop/test.txt"), Testklasse.class);
    }

    public static void log(Enumerators.LogEnum e, String s, Object o) {
        System.out.println("[" + e.name() + "] " + o.getClass() + ": " + s);
    }
}
