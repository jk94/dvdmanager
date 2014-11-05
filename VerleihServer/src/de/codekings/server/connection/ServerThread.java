/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.codekings.server.connection;

import de.codekings.common.Connection.Hasher;
import de.codekings.common.Connection.Message;
import de.codekings.common.datacontents.Cover;
import de.codekings.common.datacontents.DVD;
import de.codekings.common.datacontents.Film;
import de.codekings.common.datacontents.Genre;
import de.codekings.common.datacontents.Kunde;
import de.codekings.common.datacontents.Mitarbeiter;
import de.codekings.common.datacontents.Reservierung;
import de.codekings.common.datacontents.Sendable;
import de.codekings.common.datacontents.User;
import de.codekings.common.json.JSON_Parser;
import de.codekings.server.controls.Control;
import de.codekings.server.controls.DBOperations;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;
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
    private final TimeOut timeouter;
    private final int i_timeout = 30000;

    /**
     * Neuer ServerThread -> Instanz von Server zur Verbindung eines Clients
     *
     * @param s Socket des Servers
     * @param k Krypter (Wenn secure=false -> optional)
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
        timeouter = new TimeOut(i_timeout);
        timeouter.start();
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
        timeouter.reset();
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

            int filmid = Integer.parseInt(m.getAdditionalparameter().get("FILM_ID"));
            //Cover c = DBOperations.getCover(Integer.parseInt(m.getAdditionalparameter().get("FILM_ID")));
            Cover c = Control.getInstance().getCoverManager().getCover(filmid);
            returnMessage.addSendable(c);
            returnMessage.addAdditionalParameter("FILM_ID", m.getAdditionalparameter().get("FILM_ID"));

            if (c != null) {
                try {
                    String sMessage = j.parseObjectToString(returnMessage);
                    Thread.sleep(200l);
                    write(sMessage);
                } catch (InterruptedException ex) {
                    Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, null, ex);
                }
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
            for (Genre g : genres) {
                answer.addSendable(g);
            }
            write(j.parseObjectToString(answer));

            beenden = true;
        }//</editor-fold>

        // <editor-fold defaultstate="collapsed" desc="checkEmail">
        if (m.getCommand().equalsIgnoreCase("checkEmail")) {
            User u = DBOperations.getUser(m.getAdditionalparameter().get("email"));

            Message answer = new Message("ReturnEmailValidation");
            if (u != null) {
                answer.addAdditionalParameter("status", String.valueOf(false));
            } else {
                answer.addAdditionalParameter("status", String.valueOf(true));
            }

            write(j.parseObjectToString(answer));

            beenden = true;
        }//</editor-fold>

        // <editor-fold defaultstate="collapsed" desc="removeFilm">
        if (m.getCommand().equalsIgnoreCase("removeFilm")) {
            int id = Integer.parseInt(m.getAdditionalparameter().get("FILM_ID"));
            DBOperations.setFilmRemoved(id);

            //Lösche Cover
            Control.getInstance().getCoverManager().deleteCover(id);

            beenden = true;
        }//</editor-fold>

        // <editor-fold defaultstate="collapsed" desc="addGenre">
        if (m.getCommand().equalsIgnoreCase("addGenre")) {
            String bez = m.getAdditionalparameter().get("bez");

            int genreid = DBOperations.addGenre(bez);

            Message answer = new Message();
            if (genreid < 0) {
                answer.setCommand("GenreExists");
            } else {
                answer.setCommand("addedGenre");
            }
            Genre g = new Genre(genreid, bez);

            answer.addSendable(g);
            write(j.parseObjectToString(answer));

            beenden = true;
        }//</editor-fold>

        // <editor-fold defaultstate="collapsed" desc="addFilm">
        if (m.getCommand().equalsIgnoreCase("addFilm")) {
            Film f = null;
            Cover c = null;
            String email = m.getAdditionalparameter().get("email");
            User u = DBOperations.getUser(email);
            if (u != null) {
                Mitarbeiter mb = DBOperations.getMitarbeiter(u.getU_ID());
                if (mb != null) {
                    for (Sendable s : m.getContent()) {
                        if (s instanceof Film) {
                            f = (Film) s;
                            break;
                        }
                    }

                    int addedID = DBOperations.addFilm(f, u.getU_ID());

                    for (Sendable s : m.getContent()) {
                        if (s instanceof Cover) {
                            c = (Cover) s;
                            c.setFilm_id(addedID);
                            break;
                        }
                    }
                    DBOperations.setCover(c);
                }
            }

            beenden = true;
        }//</editor-fold>

        // <editor-fold defaultstate="collapsed" desc="updateFilm">
        if (m.getCommand().equalsIgnoreCase("updateFilm")) {
            Film f = null;
            Cover c = null;
            String email = m.getAdditionalparameter().get("email");
            User u = DBOperations.getUser(email);
            if (u != null) {
                Mitarbeiter mb = DBOperations.getMitarbeiter(u.getU_ID());
                if (mb != null) {
                    for (Sendable s : m.getContent()) {
                        if (s instanceof Film) {
                            f = (Film) s;
                            break;
                        }
                    }

                    DBOperations.UpdateFilm(f, u.getU_ID());

                    for (Sendable s : m.getContent()) {
                        if (s instanceof Cover) {
                            c = (Cover) s;
                            break;
                        }
                    }
                    String covername = DBOperations.getFilmProperty(f.getFILMID(), "cover");
                    if (covername.equals("")) {
                        DBOperations.setCover(c);
                    } else {
                        DBOperations.updateCover(c, covername);
                    }

                }
            }

            beenden = true;
        }//</editor-fold>

        // <editor-fold defaultstate="collapsed" desc="isFilmReserved">
        if (m.getCommand().equalsIgnoreCase("isFilmReserved")) {
            int filmid = Integer.parseInt(m.getAdditionalparameter().get("id"));
            String email = m.getAdditionalparameter().get("email");
            Message answer = new Message("filmReserving");
            //Überprüfe ob von Kunde reserviert
            User u = DBOperations.getUser(email);
            Kunde k = DBOperations.getKunde(u.getU_ID());
            ArrayList<DVD> res = DBOperations.getReservierungOfKunde(k.getKU_ID());
            boolean reserviert = false;
            for (DVD d : res) {
                if (d.getFilm().getFILMID() == filmid) {
                    answer.addAdditionalParameter("result", "ownreserved");
                    reserviert = true;
                    break;
                }
            }
            if (!reserviert) {
                //Überprüfe ob noch DVDs vorhanden
                ArrayList<DVD> dvd_liste = DBOperations.getDVDs(filmid);
                if (dvd_liste.isEmpty()) {
                    answer.addAdditionalParameter("result", "noDVD");
                    reserviert = true;
                } else {
                    int anzahlReservierungenAufFilm = DBOperations.getAnzahlReservierungZuFilm(filmid);
                    int anzahlAusleiheAufFilm = DBOperations.getAnzahlAusleiheZuFilm(filmid);
                    if (dvd_liste.size() <= anzahlReservierungenAufFilm + anzahlAusleiheAufFilm) {
                        answer.addAdditionalParameter("result", "noMore");
                        reserviert = true;
                    }
                }
            }

            if (!reserviert) {
                answer.addAdditionalParameter("result", "ok");
            }
            write(j.parseObjectToString(answer));

            beenden = true;
        }//</editor-fold>

        // <editor-fold defaultstate="collapsed" desc="reserveFilm">
        if (m.getCommand().equalsIgnoreCase("reserveFilm")) {
            int filmid = Integer.parseInt(m.getAdditionalparameter().get("FI_ID"));
            String email = m.getAdditionalparameter().get("email");
            Message answer = new Message("filmReserving");
            //Überprüfe ob von Kunde reserviert
            User u = DBOperations.getUser(email);
            Kunde k = DBOperations.getKunde(u.getU_ID());
            ArrayList<DVD> res = DBOperations.getReservierungOfKunde(k.getKU_ID());
            boolean reserviert = false;
            boolean bereitsreserviert = false;
            for (DVD d : res) {
                if (d.getFilm().getFILMID() == filmid) {
                    bereitsreserviert = true;
                    answer.addAdditionalParameter("result", "ownreserved");
                    break;
                }
            }
            if (!bereitsreserviert) {
                //Überprüfe ob noch DVDs vorhanden
                ArrayList<DVD> dvd_liste = DBOperations.getDVDs(filmid);
                if (dvd_liste.isEmpty()) {
                    answer.addAdditionalParameter("result", "noDVD");
                    reserviert = true;
                } else {
                    int anzahlReservierungenAufFilm = DBOperations.getAnzahlReservierungZuFilm(filmid);
                    int anzahlAusleiheAufFilm = DBOperations.getAnzahlAusleiheZuFilm(filmid);
                    if (dvd_liste.size() <= anzahlReservierungenAufFilm + anzahlAusleiheAufFilm) {
                        answer.addAdditionalParameter("result", "noMore");
                        reserviert = true;
                    }
                }
            }

            if (!reserviert && !bereitsreserviert) {
                //Reserviere Film
                int ku_id = k.getKU_ID();
                reserviert = DBOperations.reserviereEineDVD(filmid, ku_id);
                if (reserviert) {
                    answer.addAdditionalParameter("result", "reserved");
                } else {
                    answer.addAdditionalParameter("result", "failed");
                }
            }
            write(j.parseObjectToString(answer));

            beenden = true;
        }//</editor-fold>

        // <editor-fold defaultstate="collapsed" desc="getKunde">
        if (m.getCommand().equalsIgnoreCase("getKunde")) {
            Message answer = new Message("returnKunde");
            int id = 0;
            if (m.getAdditionalparameter().containsKey("email")) {
                id = DBOperations.getUser(m.getAdditionalparameter().get("email")).getU_ID();
            } else {
                id = Integer.parseInt(m.getAdditionalparameter().get("KU_ID"));
            }
            Kunde k = DBOperations.getKunde(id);
            answer.addSendable(k);

            write(j.parseObjectToString(answer));
            beenden = true;

        }//</editor-fold>

        // <editor-fold defaultstate="collapsed" desc="getUsers">
        if (m.getCommand().equalsIgnoreCase("getUsers")) {
            Message answer = new Message("returnUsers");

            ArrayList<Integer> ids = DBOperations.getUserIDs();
            for (int id : ids) {
                User u = DBOperations.getUser(id);
                answer.addSendable(u);
            }

            write(j.parseObjectToString(answer));
            beenden = true;

        }//</editor-fold>

        // <editor-fold defaultstate="collapsed" desc="removeReservierung">
        if (m.getCommand().equalsIgnoreCase("removeReservierung")) {
            DBOperations.setReservierungUngueltig(Integer.parseInt(m.getAdditionalparameter().get("RES_ID")));
            beenden = true;

        }//</editor-fold>

        // <editor-fold defaultstate="collapsed" desc="getReservierungen">
        if (m.getCommand().equalsIgnoreCase("getReservierungen")) {
            Message answer = new Message("returnReservierungen");
            if (m.getAdditionalparameter().containsKey("email")) {
                String email = m.getAdditionalparameter().get("email");
                ArrayList<Reservierung> res = DBOperations.getReservierungOfKunde2(DBOperations.getKunde(DBOperations.getUser(email).getU_ID()).getKU_ID());
                for (Reservierung r : res) {
                    answer.addSendable(r);
                }
            } else {
                int accountid = Integer.parseInt(m.getAdditionalparameter().get("accnr"));
                ArrayList<Reservierung> res = DBOperations.getReservierungOfKunde2(accountid);
                for (Reservierung r : res) {
                    answer.addSendable(r);
                }
            }

            write(j.parseObjectToString(answer));
            beenden = true;

        }//</editor-fold>

        // <editor-fold defaultstate="collapsed" desc="updateUser">
        if (m.getCommand().equalsIgnoreCase("updateUser")) {
            User u = null;

            for (Sendable s : m.getContent()) {
                if (s instanceof User) {
                    u = (User) s;
                    break;
                }
            }

            Message answer = new Message("updateUserSuccess");

            DBOperations.updateUser(u);

            write(j.parseObjectToString(answer));
            beenden = true;

        }//</editor-fold>

        // <editor-fold defaultstate="collapsed" desc="addUser">
        if (m.getCommand().equalsIgnoreCase("addUser")) {
            User u = null;
            for (Sendable s : m.getContent()) {
                if (s instanceof User) {
                    u = (User) s;
                    break;
                }
            }
            Message answer = new Message("addUserSuccess");

            DBOperations.addUser(u);

            write(j.parseObjectToString(answer));
            beenden = true;

        }//</editor-fold>

        // <editor-fold defaultstate="collapsed" desc="getDVDs">
        if (m.getCommand().equalsIgnoreCase("getDVDs")) {
            Message answer = new Message("returnDVDs");
            int filmid = Integer.parseInt(m.getAdditionalparameter().get("FI_ID"));
            ArrayList<DVD> dvds = DBOperations.getDVDs(filmid);
            for (DVD d : dvds) {
                answer.addSendable(d);
            }

            write(j.parseObjectToString(answer));
            beenden = true;

        }//</editor-fold>

        // <editor-fold defaultstate="collapsed" desc="removeDVD">
        if (m.getCommand().equalsIgnoreCase("removeDVD")) {
            Message answer = new Message("DVDremove");
            int dvdid = Integer.parseInt(m.getAdditionalparameter().get("DVD_ID"));
            if (DBOperations.removeDVD(dvdid)) {
                answer.addAdditionalParameter("result", "success");
            } else {
                answer.addAdditionalParameter("result", "dvdsout");
            }

            write(j.parseObjectToString(answer));
            beenden = true;

        }//</editor-fold>

        // <editor-fold defaultstate="collapsed" desc="addDVD">
        if (m.getCommand().equalsIgnoreCase("addDVD")) {
            Message answer = new Message("DVDadd");
            DVD dvd = null;
            for (Sendable s : m.getContent()) {
                if (s instanceof DVD) {
                    dvd = (DVD) s;
                    break;
                }
            }
            String email = m.getAdditionalparameter().get("email");
            int editor = DBOperations.getUser(email).getU_ID();
            if (DBOperations.addDVD(dvd, editor)) {
                answer.addAdditionalParameter("result", "success");
            } else {
                answer.addAdditionalParameter("result", "artnrfailed");
            }

            write(j.parseObjectToString(answer));
            beenden = true;

        }//</editor-fold>

        // <editor-fold defaultstate="collapsed" desc="saveDVD">
        if (m.getCommand().equalsIgnoreCase("saveDVD")) {
            Message answer = new Message("DVDsave");
            DVD dvd = null;
            for (Sendable s : m.getContent()) {
                if (s instanceof DVD) {
                    dvd = (DVD) s;
                    break;
                }
            }
            String email = m.getAdditionalparameter().get("email");
            int editor = DBOperations.getUser(email).getU_ID();
            if (DBOperations.updateDVD(dvd, editor)) {
                answer.addAdditionalParameter("result", "success");
            } else {
                answer.addAdditionalParameter("result", "artnrfailed");
            }

            write(j.parseObjectToString(answer));
            beenden = true;

        }//</editor-fold>

        // <editor-fold defaultstate="collapsed" desc="submitAusleihe">
        if (m.getCommand().equalsIgnoreCase("submitAusleihe")) {
            Message answer = new Message("returnsubmitAusleihe");
            int id = Integer.parseInt(m.getAdditionalparameter().get("accnr"));
            double kosten = Double.parseDouble(m.getAdditionalparameter().get("kosten"));
            Kunde k = DBOperations.getKunde(id);
            k.setAccountbalance(k.getAccountbalance() - kosten);
            Date ende = new Date(Long.parseLong(m.getAdditionalparameter().get("date")));

            if (DBOperations.ausleihe(id, ende)) {
                answer.addAdditionalParameter("result", "success");
            } else {
                answer.addAdditionalParameter("result", "artnrfailed");
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
            timeouter.ende();
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
            this.timeouter.ende();
            log.log(Level.INFO, "Connection to {0} was closed automized!");
        } catch (IOException ex) {
            log.log(Level.SEVERE, ex.getMessage());
        }
    }

    class TimeOut extends Thread {

        private final int timeout;
        private boolean ende = false;
        int counter = 0;

        public TimeOut(int timeout) {
            this.timeout = timeout;
        }

        @Override
        public void run() {
            while (!ende) {
                try {
                    Thread.sleep(1);
                } catch (InterruptedException ex) {
                    Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, null, ex);
                }
                if (counter >= timeout) {
                    ThreadcloseConnection();
                }
                counter++;
            }
        }

        public void reset() {
            counter = 0;
        }

        public void ende() {
            ende = true;
        }
    }

}
