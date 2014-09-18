/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.codekings.client.connection;

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
public class ClientThread {

    private BufferedReader reader;
    private PrintWriter writer;

    public ClientThread() {

    }

    private void neueVerbindung(String addresse, int port) {
        try {
            Socket socket = new Socket(addresse, port);

            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer = new PrintWriter(socket.getOutputStream());

        } catch (UnknownHostException ex) {
            System.out.println(ex.getMessage());
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        new Thread(() -> {
            try {
                String s;
                while ((s = reader.readLine()) != null) {
                    System.out.println((s + "\n"));
                }
                //sSystem.out.println("3");
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
        }).start();
    }

    public boolean HeartBeat(String host, int port) {
        Socket socket = null;
        PrintWriter p = null;
        try {
            socket = new Socket(host, port);
            p = new PrintWriter(socket.getOutputStream());
            p.write("heartbeat\n");
            p.flush();
            return socket.isConnected();
        } catch (UnknownHostException ex) {
            System.out.println(ex.getMessage());
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        } finally {
            if (socket != null) {
                try {
                    socket.close();
                    p.close();
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                }
            }
        }
        return false;
    }
}
