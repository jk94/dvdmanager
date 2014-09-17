/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package connection;

import de.jan.common.log.LogInitialiser;
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
    private static final VerleihServer verServer = new VerleihServer(1234);
    private static ServerSocket server;
    private static final Logger log = Logger.getLogger(VerleihServer.class.getSimpleName());

    private VerleihServer(int port) {
        LogInitialiser.initialiseLog(log, "VerleihServer");
        server = null;
        try {
            server = new ServerSocket(port);
        } catch (IOException ex) {
            log.log(Level.SEVERE, ex.getMessage());
        }
        log.log(Level.INFO, "Server wurde gestartet..");
    }

    public static void writeToAll(String name, String s) {
        clientThreads.stream().forEach((clientThread) -> {
            clientThread.write(s);
        });
    }

    public void closeSessions() {
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
    }
    
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

                ClientThread client = new ClientThread(socket);
                client.start();
                clientThreads.add(client);

            } catch (IOException ex) {
                log.log(Level.SEVERE, ex.getMessage());
            }
            log.log(Level.INFO, String.valueOf(clientThreads.size()));
        }
    }

    public static String whoIsOnline() {
        String s = "Clients connected: " + String.valueOf(clientThreads.size());

        /*for (int i = 0; i < clientThreads.size(); i++) {
         s += clientThreads.get(i).getClientName() + ", ";
         }*/
        return s;
    }

    public static VerleihServer getInstance() {
        return verServer;
    }

}
