/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.codekings.server.main;

import de.codekings.server.controls.Control;
import de.codekings.server.connection.VerleihServer;

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
        //json.json_parser.getInstance().parseObjectOut(new Testklasse());
        //Testklasse t;
        //t = json.json_parser.getInstance().parseObjectIn(new File("C:/Users/Jan/Desktop/test.txt"), Testklasse.class);
        VerleihServer.getInstance().start();
        //VerleihServer.getInstance().addLogHandler();
        Control.getInstance();
    }
    
    public static void stopServer(){
        System.exit(0);
    }
}