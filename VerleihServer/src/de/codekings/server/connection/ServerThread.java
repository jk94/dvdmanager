/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.codekings.server.connection;

import de.codekings.common.Connection.Krypter;
import de.codekings.common.Connection.Message;
import de.codekings.common.datacontents.Cover;
import de.codekings.common.datacontents.Film;
import de.codekings.common.datacontents.SendablePublicKey;
import de.codekings.common.exceptions.PublicKeyNotFoundException;
import de.codekings.common.json.JSON_Parser;
import de.codekings.server.controls.Control;
import de.codekings.server.controls.DBOperations;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Jan
 */
class ServerThread extends Thread {

    private BufferedReader reader;
    private PrintWriter writer;
    private final Socket socket;
    private static Logger log = Control.getInstance().getLogger();
    private Krypter krypter;
    private final boolean secured;

    public ServerThread(Socket s, Krypter k, boolean secure) {
        this.krypter = k;
        this.socket = s;
        this.secured = secure;
        try {
            if (secure) {
                try {
                    writer = new PrintWriter(Krypter.encryptOutputStream(socket.getOutputStream(), k.getForeignPublicKey()));
                    reader = new BufferedReader(new InputStreamReader(Krypter.decryptInputStream(socket.getInputStream(), k.getKeys().getPrivate())));
                } catch (Exception e) {
                    log.log(Level.SEVERE, e.getMessage());
                }
            } else {
                this.reader = new BufferedReader(new InputStreamReader(s.getInputStream()));
                this.writer = new PrintWriter(s.getOutputStream());
            }
        } catch (IOException ex) {
            log.log(Level.SEVERE, ex.getMessage());
        }
    }

    @Override
    public void run() {
        try {
            String s = "";
            JSON_Parser j = new JSON_Parser();
            while ((s = reader.readLine()) != null) {
                //TODO Auslesen!
                s = s.trim();
                System.out.println(s);
                if (s.equals("")) {
                } else {
                    Message m = (Message) j.parseStringToObject(s, Message.class);
                    if (MessageAuswertung(m)) {
                        break;
                    } else {
                    }
                }
            }
        } catch (IOException ex) {
            log.log(Level.INFO, "Client {0} disconnected!", socket.getInetAddress());
        }
    }

    private void write(String s) throws PublicKeyNotFoundException {
        if (secured) {
            try {
                if (krypter.getForeignPublicKey() != null) {
                    throw new PublicKeyNotFoundException();
                }
            } catch (PublicKeyNotFoundException e) {
                System.out.println(e.getMessage());
            }
        }
        writer.append(s + "\n");
        writer.flush();
    }

    private boolean MessageAuswertung(Message m) {
        boolean beenden = false;
        JSON_Parser j = new JSON_Parser();
        //GET PUBLIC KEY
        if (m.getCommand().equalsIgnoreCase("getPublicKey")) {
            Message answer = new Message("returnPublicKey");
            answer.addSendable(new SendablePublicKey(krypter.getKeys().getPublic().getEncoded()));

            try {
                write(j.parseObjectToString(answer));
            } catch (PublicKeyNotFoundException e) {
                log.log(Level.WARNING, e.getMessage());
                beenden = true;
            }
        }
        //RETURN FILMS
        if (m.getCommand().equalsIgnoreCase("getFilms")) {
            Message returnMessage = new Message("returnFilms");

            ArrayList<Film> liste = DBOperations.getFilme();
            liste.stream().forEach((f) -> {
                returnMessage.addSendable(f);
            });
            String sMessage = j.parseObjectToString(returnMessage);
            try {
                write(sMessage);
            } catch (PublicKeyNotFoundException e) {
            }
            beenden = true;
        }
        if (m.getCommand().equalsIgnoreCase("getCover")) {
            Message returnMessage = new Message("returnCover");

            Cover c = DBOperations.getCover(Integer.parseInt(m.getAdditionalparameter().get("FILM_ID")));
            
            returnMessage.addSendable(c);
            returnMessage.addAdditionalParameter("FILM_ID", m.getAdditionalparameter().get("FILM_ID"));

            if (c != null) {
                String sMessage = j.parseObjectToString(returnMessage);
                try {
                    write(sMessage);
                } catch (PublicKeyNotFoundException e) {

                }
            }
            beenden = true;
        }
        return beenden;

    }

    public void closeConnection() throws IOException {
        this.socket.close();
    }

}
