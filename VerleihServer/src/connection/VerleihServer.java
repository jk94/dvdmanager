/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package connection;

import Enumerators.LogEnum;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import verleihserver.main;

/**
 *
 * @author Jan
 */
public class VerleihServer extends Thread{

    private static ArrayList<ClientThread> clientThreads = new ArrayList<>();
    private static final VerleihServer verServer = new VerleihServer(1234);
    private static ServerSocket server;
    private VerleihServer(int port) {
        server = null;
        try {
            server = new ServerSocket(port);
        } catch (IOException ex) {
            Logger.getLogger(VerleihServer.class.getName()).log(Level.SEVERE, null, ex);
        }
        main.log(LogEnum.INFO, "Server wurde gestartet..", this);
    }
    
    
    public static void writeToAll(String name, String s) {
        for (ClientThread clientThread : clientThreads) {
            clientThread.write(s);
        }
        //main.log(name + ": " + s);
    }

    @Override
    public void run() {
        while (true) {
            try {
                Socket socket = server.accept();

                main.log(Enumerators.LogEnum.INFO, "Verbindung wurde von " + socket.getInetAddress() + " hergestellt", this);

                ClientThread client = new ClientThread(socket);
                client.start();
                clientThreads.add(client);

            } catch (IOException ex) {
                Logger.getLogger(VerleihServer.class.getName()).log(Level.SEVERE, null, ex);
            }
            main.log(LogEnum.INFO, String.valueOf(clientThreads.size()), this);
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
