/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package json;

import Enumerators.LogEnum;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import verleihserver.Testklasse;
import verleihserver.main;

/**
 *
 * @author Jan
 */
public class json_parser {

    private static final json_parser jp = new json_parser();
    private static ObjectMapper om;

    private json_parser() {
        om = new ObjectMapper();
    }

    public static json_parser getInstance() {
        return jp;
    }

    public void parseObjectOut(Testklasse o) {
        System.out.println("parsing started");
        try {
            //System.out.println(om.writeValueAsString(o));
            om.writeValue(new File("C:/Users/Jan/Desktop/test.txt"), o);
        } catch (Exception ex) {
            main.log(LogEnum.ERROR, ex.getMessage(), getInstance());
        }
        System.out.println("parsing ended");

    }

    public Testklasse parseObjectIn(File f, Class<Testklasse> c) {
        System.out.println("start");
        try {
            return om.readValue(f, c);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        System.out.println("end");
        return null;
    }

}
