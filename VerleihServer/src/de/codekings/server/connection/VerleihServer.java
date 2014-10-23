/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.codekings.server.connection;

import de.codekings.server.controls.Control;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Jan
 */
public class VerleihServer extends Thread {

    private final ArrayList<ServerThread> serverthreads = new ArrayList<>();
    private ServerSocket server;
    private final Logger log = Logger.getLogger(Control.class.getSimpleName());

    public VerleihServer(int port) {
        server = null;
        try {
            server = new ServerSocket(port);
        } catch (IOException ex) {
            log.log(Level.SEVERE, ex.getMessage());
        }
        log.log(Level.INFO, "Server wurde gestartet..");
    }

    /*public void closeSessions() {
        clientThreads.stream().forEach((clientThread) -> {
            try {
                clientThread.write("Connection will be closed now!");
                sleep(1000l);
                clientThread.closeConnection();
                clientThreads.remove(clientThread);
            } catch (IOException | InterruptedException e) {
                log.log(Level.SEVERE, e.getMessage());
            }
        });
    }*/
    
    /**
     * Löscht einen ServerThread aus der Liste, der offenen Connections
     * @param c Der @ServerThread, der aus der Liste gelöscht werden soll.
     */
    public void removeConnection(ServerThread c){
        if(serverthreads.contains(c)){
            serverthreads.remove(c);
        }
    }

    @Override
    public void run() {
        while (true) {
            try {
                Socket socket = server.accept();

                log.log(Level.INFO, "Verbindung wurde von {0} hergestellt", socket.getInetAddress());

                ServerThread serverthread = new ServerThread(socket, this);
                serverthread.start();
                serverthreads.add(serverthread);

            } catch (IOException ex) {
                log.log(Level.SEVERE, ex.getMessage());
            }
        }
    }

}
