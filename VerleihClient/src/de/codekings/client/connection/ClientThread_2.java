/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.codekings.client.connection;

import de.codekings.common.Connection.Krypter;
import de.codekings.common.Connection.Message;
import de.codekings.common.datacontents.SendablePublicKey;
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
public class ClientThread_2 {

    private BufferedReader reader;
    private PrintWriter writer;
    private Socket socket;
    private Krypter k;
    private boolean secured;

    public ClientThread_2(Krypter ownKrypter) {
        this.k = ownKrypter;
    }

    private void neueVerbindung(String addresse, int port, boolean secure) {
        this.secured = secure;
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
                    s = s.trim();
                    if (s.equals("")) {
                    } else {
                        Message m = (Message) j.parseStringToObject(s, Message.class);
                        if (MessageAuswertung(m)) {
                            break;
                        }
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
            answer.addSendable(new SendablePublicKey(k.getKeys().getPublic().getEncoded()));
            try {
                write(m);
                beenden = true;
            } catch (PublicKeyNotFoundException e) {
            }
        }

        //RETURN PUBLIC KEY
        if (m.getCommand().equalsIgnoreCase("returnPublicKey")) {
            SendablePublicKey ckpk = new SendablePublicKey();
            for (Sendable send : m.getContent()) {
                if (send instanceof SendablePublicKey) {
                    ckpk = (SendablePublicKey) send;
                    break;
                }
            }
            k.setForeignPublicKey(ckpk.generatePublicKey());
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
            if (secured) {
                if (k.getKeys().getPublic() != null) {
                    m.addSendable(new SendablePublicKey(k.getKeys().getPublic().getEncoded()));
                } else {
                    throw new PublicKeyNotFoundException();
                }

            }
            String s;

            JSON_Parser j = new JSON_Parser();
            s = j.parseObjectToString(m);

            writer.append(s + "\n");
            writer.flush();
        } catch (PublicKeyNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    public void requestForPubKeyFromServer(String host, int port) {
        SendablePublicKey erg = null;

        neueVerbindung(host, port, false);
        Message m = new Message("getPublicKey");
        try {
            write(m);
        } catch (PublicKeyNotFoundException e) {

        }
    }

    public void requestForFilms(String host, int port, int startindex, int anzahl) {
        Message m = new Message("getFilms");
        m.addAdditionalParameter("startindex", "" + startindex);
        m.addAdditionalParameter("anzahl", "" + anzahl);
        neueVerbindung(host, port, true);
        try{
            write(m);
        }catch (PublicKeyNotFoundException e){
            System.out.println(e.getMessage());
        }
    }
}
