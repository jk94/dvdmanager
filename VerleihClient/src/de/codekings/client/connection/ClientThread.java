/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.codekings.client.connection;

import de.codekings.common.Connection.Krypter;
import de.codekings.common.Connection.Message;
import de.codekings.common.datacontents.CKPublicKey;
import de.codekings.common.datacontents.Sendable;
import de.codekings.common.exceptions.PublicKeyNotFoundException;
import de.codekings.common.json.JSON_Parser;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.rmi.UnknownHostException;

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

    private void neueVerbindung(String addresse, int port, boolean secure) {

        try {
            socket = new Socket(addresse, port);
            try {
                if (secure) {
                    writer = new PrintWriter(Krypter.encryptOutputStream(socket.getOutputStream(), k.getForeignPublicKey()));
                    reader = new BufferedReader(new InputStreamReader(Krypter.decryptInputStream(socket.getInputStream(), k.getKeys().getPrivate())));
                } else {
                    this.reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    this.writer = new PrintWriter(socket.getOutputStream());
                }
            } catch (Exception e) {

            }

        } catch (UnknownHostException ex) {
            System.out.println(ex.getMessage());
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        new Thread(() -> {
            try {
                String s;
                JSON_Parser j = new JSON_Parser();
                while ((s = reader.readLine()) != null) {
                    //TODO Annahme der Daten
                    Message m = (Message) j.parseStringToObject(s, Message.class);
                    if (MessageAuswertung(m)) {
                        break;
                    }

                }
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
        }).start();

    }

    private boolean MessageAuswertung(Message m) {
        boolean beenden = false;
        //GET PUBLIC KEY
        if (m.getCommand().equalsIgnoreCase("getPublicKey")) {
            Message answer = new Message("returnPublicKey");
            answer.addSendable(new CKPublicKey(k.getKeys().getPublic()));
            try {
                write(m);
                beenden = true;
            } catch (PublicKeyNotFoundException e) {
            }
        }

        //RETURN PUBLIC KEY
        if (m.getCommand().equalsIgnoreCase("returnPublicKey")) {
            CKPublicKey ckpk = new CKPublicKey(null);
            for (Sendable send : m.getContent()) {
                if (send instanceof CKPublicKey) {
                    ckpk = (CKPublicKey) send;
                    break;
                }
            }
            k.setForeignPublicKey(ckpk.getPublicKey());
            beenden = true;
        }

        //RETURN FILMS
        if (m.getCommand().equalsIgnoreCase("returnFilms")) {
            int startindex = Integer.parseInt(m.getAdditionalparameter().get("startindex"));
            int howmany = Integer.parseInt(m.getAdditionalparameter().get("anzahl"));

        }

        return beenden;
    }

    public void write(Message m) throws PublicKeyNotFoundException {
        try {
            if (k.getForeignPublicKey() != null) {
                throw new PublicKeyNotFoundException();
            }
            m.addSendable(new CKPublicKey(k.getKeys().getPublic()));
            String s = "";
            try {
                JSON_Parser j = new JSON_Parser();
                s = j.parseObjectToString(m);
            } catch (ExceptionInInitializerError a) {
                System.out.println(a.getMessage());
            }
            System.out.println(s);
            writer.append(s + "\n");
            writer.flush();
        } catch (PublicKeyNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    public void requestForPubKeyFromServer(String host, int port) {
        CKPublicKey erg = null;

        neueVerbindung(host, port, false);
        Message m = new Message("getPublicKey");
        try {
            write(m);
        } catch (PublicKeyNotFoundException e) {

        }
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
