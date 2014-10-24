/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.codekings.client.connection;

import de.codekings.common.Connection.Message;
import de.codekings.common.json.JSON_Parser;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.rmi.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Jan
 */
public class ClientThread extends Thread {

    private final String host;
    private final int port;
    private Socket conn;
    PrintWriter writer = null;
    BufferedReader reader = null;
    MessageReturn mr;

    public ClientThread(MessageReturn mr, String host, int port) {
        this.mr = mr;
        this.host = host;
        this.port = port;
    }

    public ClientThread requestToServer(Message m) {
        conn = newConnection();
        JSON_Parser jwriter = new JSON_Parser();
        try {
            reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            writer = new PrintWriter(conn.getOutputStream());

        } catch (Exception e) {
        }

        new Thread(() -> {
            try {
                String s;
                JSON_Parser jreader = new JSON_Parser();
                while (reader != null && (s = reader.readLine()) != null) {
                    System.out.println(s);
                    //TODO Annahme der Daten
                    s = s.trim();
                    if (s.equals("")) {
                    } else {
                        Message m1 = (Message) jreader.parseStringToObject(s, Message.class);
                        mr.returnedMessage(m1);
                        break;
                    }
                }
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
        }).start();
        writer.append(jwriter.parseObjectToString(m) + "\n");
        writer.flush();
        return this;
    }

    private Socket newConnection() {
        Socket socket = null;
        try {
            socket = new Socket(this.host, this.port);
        } catch (UnknownHostException ex) {
            System.out.println(ex.getMessage());
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        return socket;
    }
    
    public void closeConnection(){
        try {
            conn.close();
        } catch (IOException ex) {
            Logger.getLogger(ClientThread.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
