/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.codekings.server.main;

import de.codekings.server.controls.Control;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 *
 * @author Jan
 */
public class main {

    private static Control c;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here

        c = new Control();
        c.setControl(c);
        //t = json.json_parser.getInstance().parseObjectIn(new File("C:/Users/Jan/Desktop/test.txt"), Testklasse.class);
        BufferedReader bfr = new BufferedReader(new InputStreamReader(System.in));
        while (true) {
            String command = "";
            try {
                command = bfr.readLine();
            } catch (IOException e) {

            }
            if (command.equalsIgnoreCase("exit")) {
                stopServer();
            }
        }
    }

    public static void stopServer() {
        System.exit(0);
    }
}
