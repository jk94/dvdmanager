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

    private static final ArrayList<ClientThread> clientThreads = new ArrayList<>();
    private static ServerSocket server;
    private final Logger log = Logger.getLogger(Control.class.getSimpleName());
    private final boolean secure;

    public VerleihServer(int port, boolean secure) {
        server = null;
        this.secure = secure;
        try {
            server = new ServerSocket(port);
        } catch (IOException ex) {
            log.log(Level.SEVERE, ex.getMessage());
        }
        log.log(Level.INFO, "Server wurde gestartet..");
    }

    public boolean isSecure() {
        return secure;
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
     *
     * @param c Der @ClientThread, der aus der Liste gelöscht werden soll.
     */
    public void removeClientThread(ClientThread c){
        if(clientThreads.contains(c)){
            clientThreads.remove(c);
        }
    }

    @Override
    public void run() {
        while (true) {
            try {
                Socket socket = server.accept();

                log.log(Level.INFO, "Verbindung wurde von {0} hergestellt", socket.getInetAddress());

                ClientThread client = new ClientThread(socket, Control.getInstance().getKrypter(), secure);
                client.start();
                clientThreads.add(client);

            } catch (IOException ex) {
                log.log(Level.SEVERE, ex.getMessage());
            }
            log.log(Level.INFO, String.valueOf(clientThreads.size()));
        }
    }

}
