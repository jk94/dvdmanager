/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.codekings.server.connection;

import de.codekings.common.Connection.Krypter;
import de.codekings.common.datacontents.Sendable;
import de.codekings.common.json.JSON_Parser;
import de.codekings.server.controls.Control;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Jan
 */
class ClientThread extends Thread {

    private BufferedReader reader;
    private PrintWriter writer;
    private final Socket socket;
    private static Logger log = Control.getInstance().getLogger();
    private Krypter krypter;

    public ClientThread(Socket s, Krypter k) {
        this.krypter = k;
        this.socket = s;
        try {
            this.reader = new BufferedReader(new InputStreamReader(s.getInputStream()));
            this.writer = new PrintWriter(s.getOutputStream());
        } catch (IOException ex) {
            log.log(Level.SEVERE, ex.getMessage());
        }
    }

    @Override
    public void run() {
        try {
            String s = "";
            while ((s = reader.readLine()) != null) {
                //TODO Auslesen!
                if (this.socket.isConnected()) {
                    log.log(Level.INFO, "{0} wrote: {1}", new Object[]{this.socket.getInetAddress().toString(), s});
                    if (s.equalsIgnoreCase("exit")) {
                        this.socket.close();
                    }

                    //SICHERER/VERSCHLÜSSELTER BEREICH
                    if (s.startsWith("cks://")) {

                    } else {
                        //NORMALER/UNVERSCHLÜSSELTER BEREICH
                        if (s.startsWith("ck://")) {
                            String[] s2 = s.split("//");
                            if (s2[1].startsWith("list")) {

                            } else {
                                Sendable inbox = JSON_Parser.getInstance().parseStringToObject(s, Sendable.class);
                            }

                        } else {
                            //BEREICH FÜR VERSCHIEDENE COMMANDS   
                        }
                    }
                } else {
                    log.log(Level.INFO, "User {0} disconnected!", this.socket.getInetAddress());
                    break;
                }
            }
        } catch (IOException ex) {
            log.log(Level.INFO, "User {0} disconnected!", socket.getInetAddress());
        }
    }

    public void write(String s) {
        writer.append(s + "\n").flush();
    }

    public void closeConnection() throws IOException {
        this.socket.close();
    }

}
