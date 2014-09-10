/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package json;

import Enumerators.LogEnum;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import verleihserver.Testklasse;
import verleihserver.main;

/**
 *
 * @author Jan
 */
public class json_parser {
 
    private static final json_parser jp = new json_parser();
    private static ObjectMapper om;
    
    private json_parser(){
        om = new ObjectMapper();
    }
    
    public static json_parser getInstance(){
        return jp;
    }
    
    public void parseObject(Testklasse o){
        System.out.println("parsing started");
        try{
            System.out.println(om.writeValueAsString(o));
        }catch(Exception ex){
            main.log(LogEnum.ERROR, ex.getMessage(), getInstance());
        }
        System.out.println("parsing ended");
        
    }
    
    
    
}
