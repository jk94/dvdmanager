/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package de.codekings.common.log;

import java.io.IOException;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Jan
 */
public class LogInitialiser {
    
    public static final void initialiseLog(Logger log, String path, String name){
        try {
            for(Handler h: log.getHandlers()){
                log.removeHandler(h);
            }
            Handler handler = new FileHandler("./" + path + "/"+ name + ".log");
            handler.setLevel(Level.FINE);
            handler.setFormatter(new VerleihCostumHandler());
            log.addHandler(handler);
            handler = new ConsoleHandler();
            handler.setFormatter(new VerleihCostumHandler());
            log.addHandler(handler);
            log.setLevel(Level.FINE);
        } catch (IOException | SecurityException ex) {
            Logger.getLogger(LogInitialiser.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
