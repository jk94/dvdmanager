/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package connection;

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
    private Socket socket;
    private static final Logger log = Logger.getLogger(ClientThread.class.getSimpleName());

    public ClientThread(Socket s) {
        this.socket = s;
        try {
            this.reader = new BufferedReader(new InputStreamReader(s.getInputStream()));
            this.writer = new PrintWriter(s.getOutputStream());
        } catch (IOException ex) {
            log.log(Level.SEVERE, ex.getMessage(), this);
        }
    }

    @Override
    public void run() {
        try {
            String s = "";
            while ((s = reader.readLine()) != null) {
                //TODO Auslesen!
                if(this.socket.isConnected()){
                log.log(Level.INFO, "{0} wrote: {1}", new Object[]{this.socket.getInetAddress().toString(), s});
                if(s.equalsIgnoreCase("exit")){
                    this.socket.close();
                }
                }else{
                    log.log(Level.SEVERE, "User {0} disconnected!", this.socket.getInetAddress());
                    break;
                }
            }
        } catch (IOException ex) {
            log.log(Level.SEVERE, "User {0} disconnected!", socket.getInetAddress());
        }
    }

    public void write(String s) {
        writer.append(s + "\n").flush();
    }

}
