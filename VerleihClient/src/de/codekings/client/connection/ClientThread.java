/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.codekings.client.connection;

import de.codekings.common.Connection.Krypter;
import de.codekings.common.Connection.Message;
import de.codekings.common.datacontents.CKPublicKey;
import de.codekings.common.exceptions.PublicKeyNotFoundException;
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
public class ClientThread {

    private BufferedReader reader;
    private PrintWriter writer;
    private Socket socket;
    private Krypter k;

    public ClientThread(Krypter ownKrypter) {
        this.k = ownKrypter;
    }

    private void neueVerbindung(String addresse, int port) {
        try {
            socket = new Socket(addresse, port);
            writer = new PrintWriter(socket.getOutputStream());
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        } catch (UnknownHostException ex) {
            System.out.println(ex.getMessage());
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        new Thread(() -> {
            try {
                String s;
                while ((s = reader.readLine()) != null) {
                    //TODO Annahme der Daten
                    if (s.startsWith("cks://")) {

                    } else if (s.startsWith("ck://")) {
                        String messagecontent = s.split("://", 1)[1];
                        Message m = (Message) JSON_Parser.getInstance().parseStringToObject(messagecontent, Message.class);
                        if(MessageAuswertung(m)){
                            break;
                        }
                    } else {

                    }
                }
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
        }).start();
    }

    private boolean MessageAuswertung(Message m){
        boolean beenden = false;
        
        return beenden;
    }
    
    private void write(String s, boolean secured) throws PublicKeyNotFoundException {
        if (secured) {
            try {
                if (k.getForeignPublicKey() != null) {
                    throw new PublicKeyNotFoundException();
                }
                writer.append("cks://");
                writer.flush();
                PrintWriter writersecure = new PrintWriter(Krypter.encryptOutputStream(System.out, k.getForeignPublicKey()));
                writersecure.append(s + "\n");
                writersecure.flush();

            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        } else {
            writer.append("ck://");
            writer.append(s);
            writer.flush();
        }
    }

    public CKPublicKey getPubKeyFromServer(String host, int port) {
        CKPublicKey erg = null;

        neueVerbindung(host, port);
        Message m = new Message("getPublicKey");
        try {
            write(JSON_Parser.getInstance().parseObjectToString(m), false);
        } catch (PublicKeyNotFoundException e) {

        }
        return erg;
    }

    public boolean HeartBeat(String host, int port) {
        Socket socket = null;
        PrintWriter p = null;
        boolean isConnected = false;
        try {
            socket = new Socket(host, port);
            p = new PrintWriter(socket.getOutputStream());
            p.write("heartbeat\n");
            p.flush();
            isConnected = socket.isConnected();
            socket.close();
            System.out.println(socket.isClosed());
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
        return isConnected;
    }
}
