/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.codekings.server.connection;

import de.codekings.common.Connection.Hasher;
import de.codekings.common.Connection.Message;
import de.codekings.common.datacontents.Cover;
import de.codekings.common.datacontents.Film;
import de.codekings.common.datacontents.Genre;
import de.codekings.common.datacontents.Mitarbeiter;
import de.codekings.common.datacontents.User;
import de.codekings.common.json.JSON_Parser;
import de.codekings.server.controls.Control;
import de.codekings.server.controls.DBOperations;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
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
    private final VerleihServer verleihserver;
    private static Logger log = Control.getInstance().getLogger();
    private Timer timeoutTimer;
    private final int i_timeout = 120000;

    /**
     * Neuer ServerThread -> Instanz von Server zur Verbindung eines Clients
     *
     * @param s Socket des Servers
     * @param k Krypter (Wenn secure=false -> optional)
     * @param secure Verschlüsselte Verbindung (bei true -> Krypter PFLICHT!)
     */
    public ServerThread(Socket s, VerleihServer vs) {
        this.socket = s;
        this.verleihserver = vs;
        try {
            this.reader = new BufferedReader(new InputStreamReader(s.getInputStream()));
            this.writer = new PrintWriter(s.getOutputStream());

        } catch (IOException ex) {
            log.log(Level.SEVERE, ex.getMessage());
        }
        timeoutTimer = new Timer();

        timeoutTimer.schedule(new TimerTask() {
            int counter = 0;

            @Override
            public void run() {
                if (counter >= i_timeout) {
                    ThreadcloseConnection();
                }
                counter++;
            }
        }, 1);
    }

    /**
     * Socketlistener -> Liest die Nachrichten.
     */
    @Override
    public void run() {
        boolean ende = false;
        while (!ende) {
            try {
                String s;
                JSON_Parser j = new JSON_Parser();
                while ((s = reader.readLine()) != null) {
                    s = s.trim();
                    if (s.equals("")) {
                    } else {
                        Message m = (Message) j.parseStringToObject(s, Message.class); //Konvertierung von JSON-String in Message
                        resetTimeout();
                        if (MessageAuswertung(m)) { //Wenn MessageAuswertung = true, wird Verbindung getrennt.
                            reader.close();
                            writer.close();
                            socket.close();
                            verleihserver.removeConnection(this);
                            ende = true;
                            break;
                        } else {
                        }
                    }
                }
            } catch (IOException ex) {
                log.log(Level.INFO, "Client {0} disconnected!", socket.getInetAddress());
                ende = true;
            }
        }
    }

    /**
     * Schickt eine is JSON-Format konvertierte Message
     *
     * @param s Message als JSON-String
     * @throws PublicKeyNotFoundException
     */
    private void write(String s) {
        writer.append(s + "\n");
        writer.flush();
        resetTimeout();
    }

    private void resetTimeout() {
        timeoutTimer.cancel();
        timeoutTimer.purge();
        timeoutTimer = new Timer();
        timeoutTimer.schedule(new TimerTask() {
            int counter = 0;

            @Override
            public void run() {
                if (counter >= i_timeout) {
                    ThreadcloseConnection();
                }
                counter++;
            }
        }, 1);
    }

    /**
     * Auswertung einer empfangenen Message. Führt entsprechende Operationen zur
     * Bearbeitung dieser aus.
     *
     * @param m Message die ausgewertet und bearbeitet werden soll
     * @return Ob Verbdindung zum Client getrennt werden kann
     */
    private boolean MessageAuswertung(Message m) {
        boolean beenden = false;
        JSON_Parser j = new JSON_Parser();

        // <editor-fold defaultstate="collapsed" desc="getFilms">
        if (m.getCommand().equalsIgnoreCase("getFilms")) {
            Message returnMessage = new Message("returnFilms");

            ArrayList<Film> liste = DBOperations.getFilme();
            liste.stream().forEach((f) -> {
                returnMessage.addSendable(f);
            });
            String sMessage = j.parseObjectToString(returnMessage);

            write(sMessage);

            beenden = true;
        }//</editor-fold>

        // <editor-fold defaultstate="collapsed" desc="getCover">
        if (m.getCommand().equalsIgnoreCase("getCover")) {
            Message returnMessage = new Message("returnCover");

            Cover c = DBOperations.getCover(Integer.parseInt(m.getAdditionalparameter().get("FILM_ID")));

            returnMessage.addSendable(c);
            returnMessage.addAdditionalParameter("FILM_ID", m.getAdditionalparameter().get("FILM_ID"));

            if (c != null) {
                String sMessage = j.parseObjectToString(returnMessage);

                write(sMessage);
            }
            beenden = true;
        }//</editor-fold>

        // <editor-fold defaultstate="collapsed" desc="login">
        if (m.getCommand().equalsIgnoreCase("login")) {
            String email, hashedpw;
            email = m.getAdditionalparameter().get("email");
            hashedpw = m.getAdditionalparameter().get("passwort");

            User u = DBOperations.getUser(email);

            Message answer = new Message("loginresult");
            if (u != null) {
                if (u.getPasswort().equals(hashedpw)) {
                    //Hier wird eingeloggt
                    answer.addAdditionalParameter("result", "success");

                    Random rnd = new Random();
                    int rndint = Math.round(rnd.nextFloat() * 1000);
                    String hash = Hasher.getInstance().ToMD5(email + hashedpw + new Date().getTime() + rndint);

                    answer.addAdditionalParameter("email", email);
                    answer.addAdditionalParameter("passwort", hashedpw);
                    answer.addAdditionalParameter("session", hash);

                    Mitarbeiter ma = DBOperations.getMitarbeiter(u.getU_ID());
                    if (ma != null) {
                        answer.addAdditionalParameter("permission", String.valueOf(ma.getPermission()));
                    } else {
                        answer.addAdditionalParameter("permission", "1");
                    }
                } else {
                    answer.addAdditionalParameter("result", "failed");
                }

            } else {
                answer.addAdditionalParameter("result", "failed");
            }

            write(j.parseObjectToString(answer));

            beenden = true;
        }//</editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="getGenres">
        if (m.getCommand().equalsIgnoreCase("getGenres")) {
            ArrayList<Genre> genres = DBOperations.getGenre();

            Message answer = new Message("returnGenres");
            for(Genre g:genres){
                answer.addSendable(g);
            }
            write(j.parseObjectToString(answer));

            beenden = true;
        }//</editor-fold>
        
        

        return beenden;

    }

    /**
     * Schließt die Verbindung zum Client
     *
     * @throws IOException
     */
    public void closeConnection() {
        try {
            this.reader.close();
            this.writer.close();
            this.socket.close();
            timeoutTimer.cancel();
            timeoutTimer.purge();
        } catch (IOException ex) {
            log.log(Level.SEVERE, ex.getMessage());
        }
    }

    private void ThreadcloseConnection() {
        try {
            this.reader.close();
            this.writer.close();
            this.socket.close();
            verleihserver.removeConnection(this);
            timeoutTimer.cancel();
            timeoutTimer.purge();
            log.log(Level.INFO, "Connection to {0} was closed automized!");
        } catch (IOException ex) {
            log.log(Level.SEVERE, ex.getMessage());
        }
    }

}
