package de.codekings.server.controls;

import de.codekings.common.Enumerators.ClassType;
import de.codekings.common.datacontents.Ausleihe;
import de.codekings.common.datacontents.Cover;
import de.codekings.common.datacontents.DVD;
import de.codekings.common.datacontents.Film;
import de.codekings.common.datacontents.Genre;
import de.codekings.common.datacontents.Kunde;
import de.codekings.common.datacontents.Mitarbeiter;
import de.codekings.common.datacontents.Reservierung;
import de.codekings.common.datacontents.User;
import de.codekings.common.json.JSON_Parser;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Jan
 */
public class DBOperations {

    public static ArrayList<Film> getFilme() {
        ArrayList<Genre> ge_liste = getGenre();
        ArrayList<Film> liste = new ArrayList<>();
        DBController dbc = Control.getInstance().getDbManager();

        String sqlstatement = "SELECT * FROM tbl_film f, tbl_genre_zuordnung gz, "
                + "tbl_genre g, tbl_fsk fk WHERE f.FI_ID = gz.FI_ID "
                + "AND g.GE_ID = gz.GE_ID AND f.fsk = fk.FSK_ID "
                + "AND f.removed = 0 "
                + "ORDER BY f.title, f.subtitle, f.FI_ID ASC";

        ResultSet rs = dbc.executeQuery(sqlstatement);
        try {
            while (rs.next()) {
                String genre = rs.getString("name");
                int film_id = rs.getInt("FI_ID");

                boolean filmvorhanden = false;
                Film neuerFilm = new Film(film_id);
                for (Film f : liste) {
                    if (f.getFILMID() == neuerFilm.getFILMID()) {
                        neuerFilm = f;
                        filmvorhanden = true;
                        break;
                    }
                }
                for (Genre g : ge_liste) {
                    if (g.getGenrebezeichnung().equals(genre)) {
                        neuerFilm.addGenre(g);
                        break;
                    }
                }
                if (filmvorhanden) {
                    continue;
                }

                int duration = rs.getInt("duration"), rating = rs.getInt("rating"), iFSK = rs.getInt("age");
                String titel = rs.getString("title"), subt = rs.getString("subtitle"), des = rs.getString("desc"),
                        sFSK = rs.getString("bez"), trailer = rs.getString("trailer"), regie = rs.getString("regie");
                double preis = rs.getDouble("preis");
                Date release = rs.getDate("release_date");

                neuerFilm.setS_titel(titel);
                neuerFilm.setS_subtitel(subt);
                neuerFilm.setS_FSK(sFSK);
                neuerFilm.setS_trailer(trailer);
                neuerFilm.setS_regie(regie);
                neuerFilm.setS_description(des);
                neuerFilm.setI_duration(duration);
                neuerFilm.setI_rating(rating);
                neuerFilm.setI_fsk(iFSK);

                String actorsunsplitted = rs.getString("actor");
                String[] actors = new String[0];
                if (actorsunsplitted.contains(";")) {
                    actors = actorsunsplitted.split(";");
                }
                for (String actor : actors) {
                    neuerFilm.addActor(actor.trim());
                }
                liste.add(neuerFilm);
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        //Für Filme ohne Genre
        sqlstatement = "SELECT * FROM tbl_film f, tbl_fsk fk WHERE "
                + "f.fsk = fk.FSK_ID "
                + "AND f.removed = 0 "
                + "ORDER BY f.title, f.subtitle, f.FI_ID ASC";
        rs = dbc.executeQuery(sqlstatement);
        try {
            while (rs.next()) {
                int filmid = rs.getInt("FI_ID");
                Film neuerFilm = new Film(filmid);

                boolean found = false;
                for (Film f : liste) {
                    if (filmid == f.getFILMID()) {
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    int duration = rs.getInt("duration"), rating = rs.getInt("rating"), iFSK = rs.getInt("age");
                    String titel = rs.getString("title"), subt = rs.getString("subtitle"), des = rs.getString("desc"),
                            sFSK = rs.getString("bez"), trailer = rs.getString("trailer"), regie = rs.getString("regie");
                    double preis = rs.getDouble("preis");
                    Date release = rs.getDate("release_date");

                    neuerFilm.setS_titel(titel);
                    neuerFilm.setS_subtitel(subt);
                    neuerFilm.setS_FSK(sFSK);
                    neuerFilm.setS_trailer(trailer);
                    neuerFilm.setS_regie(regie);
                    neuerFilm.setS_description(des);
                    neuerFilm.setI_duration(duration);
                    neuerFilm.setI_rating(rating);
                    neuerFilm.setI_fsk(iFSK);

                    String actorsunsplitted = rs.getString("actor");
                    String[] actors = new String[0];
                    if (actorsunsplitted.contains(";")) {
                        actors = actorsunsplitted.split(";");
                    }
                    for (String actor : actors) {
                        neuerFilm.addActor(actor.trim());
                    }
                    liste.add(neuerFilm);
                }
            }
            rs.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return liste;
    }

    public static Film getFilm(int film_id) {
        ArrayList<Genre> ge_liste = getGenre();
        Film erg = new Film(film_id);
        DBController dbc = Control.getInstance().getDbManager();

        String sqlstatement = "SELECT * FROM tbl_film f, tbl_genre_zuordnung gz, "
                + "tbl_genre g, tbl_fsk fk WHERE f.FI_ID = gz.FI_ID "
                + "AND g.GE_ID = gz.GE_ID AND f.fsk = fk.FSK_ID "
                + "AND f.removed = 0 AND f.FI_ID = " + film_id + " "
                + "ORDER BY f.title, f.subtitle, f.FI_ID ASC";

        ResultSet rs = dbc.executeQuery(sqlstatement);
        boolean hatgenre = false;
        try {
            while (rs.next()) {
                hatgenre = true;
                String genre = rs.getString("name");

                for (Genre g : ge_liste) {
                    if (g.getGenrebezeichnung().equals(genre)) {
                        erg.addGenre(g);
                        break;
                    }
                }

                int duration = rs.getInt("duration"), rating = rs.getInt("rating"), iFSK = rs.getInt("age");
                String titel = rs.getString("title"), subt = rs.getString("subtitle"), des = rs.getString("desc"),
                        sFSK = rs.getString("bez"), trailer = rs.getString("trailer"), regie = rs.getString("regie");
                double preis = rs.getDouble("preis");
                Date release = rs.getDate("release_date");

                erg.setS_titel(titel);
                erg.setS_subtitel(subt);
                erg.setS_FSK(sFSK);
                erg.setS_trailer(trailer);
                erg.setS_regie(regie);
                erg.setS_description(des);
                erg.setI_duration(duration);
                erg.setI_rating(rating);
                erg.setI_fsk(iFSK);
                erg.setD_preis(preis);

                String actorsunsplitted = rs.getString("actor");
                String[] actors = new String[0];
                if (actorsunsplitted.contains(";")) {
                    actors = actorsunsplitted.split(";");
                }
                for (String actor : actors) {
                    erg.addActor(actor.trim());
                }
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (!hatgenre) {//Für Filme ohne Genre
            sqlstatement = "SELECT * FROM tbl_film f, tbl_fsk fk WHERE "
                    + "f.fsk = fk.FSK_ID "
                    + "AND f.removed = 0 AND f.FILM_ID = " + film_id + " "
                    + "ORDER BY f.title, f.subtitle, f.FI_ID ASC";
            rs = dbc.executeQuery(sqlstatement);
            try {
                while (rs.next()) {

                    int duration = rs.getInt("duration"), rating = rs.getInt("rating"), iFSK = rs.getInt("age");
                    String titel = rs.getString("title"), subt = rs.getString("subtitle"), des = rs.getString("desc"),
                            sFSK = rs.getString("bez"), trailer = rs.getString("trailer"), regie = rs.getString("regie");
                    double preis = rs.getDouble("preis");
                    java.sql.Date release = rs.getDate("release_date");

                    erg.setS_titel(titel);
                    erg.setS_subtitel(subt);
                    erg.setS_FSK(sFSK);
                    erg.setS_trailer(trailer);
                    erg.setS_regie(regie);
                    erg.setS_description(des);
                    erg.setI_duration(duration);
                    erg.setI_rating(rating);
                    erg.setI_fsk(iFSK);
                    erg.setD_preis(preis);

                    String actorsunsplitted = rs.getString("actor");
                    String[] actors = new String[0];
                    if (actorsunsplitted.contains(";")) {
                        actors = actorsunsplitted.split(";");
                    }
                    for (String actor : actors) {
                        erg.addActor(actor.trim());
                    }
                }
                rs.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }

        return erg;
    }

    public static ArrayList<Genre> getGenre() {
        ArrayList<Genre> liste = new ArrayList<>();

        String sqlstatement = "SELECT * FROM tbl_genre";

        DBController dbc = Control.getInstance().getDbManager();

        ResultSet rs = dbc.executeQuery(sqlstatement);

        try {
            while (rs.next()) {
                int genre_id = rs.getInt("GE_ID");
                String bezeichnung = rs.getString("name");
                Genre g = new Genre(genre_id, bezeichnung);
                liste.add(g);
            }
            rs.close();
        } catch (SQLException ex) {
        }
        return liste;
    }

    public static Genre getGenre(String bez) {
        Genre erg = null;

        DBController dbc = Control.getInstance().getDbManager();

        String sqlstatement = "SELECT * FROM tbl_genre WHERE name = ?";

        try {
            PreparedStatement pst = dbc.getConnection().prepareStatement(sqlstatement);
            pst.setString(1, bez);
            ResultSet rs = dbc.executeQuery(pst);
            while (rs.next()) {
                int genre_id = rs.getInt("GE_ID");
                String bezeichnung = rs.getString("name");
                erg = new Genre(genre_id, bezeichnung);
            }
            rs.close();
        } catch (SQLException ex) {
            return null;
        }
        return erg;
    }

    public static Genre getGenre(int id) {
        Genre erg = null;

        DBController dbc = Control.getInstance().getDbManager();

        String sqlstatement = "SELECT * FROM tbl_genre WHERE GE_ID = ?";

        try {
            PreparedStatement pst = dbc.getConnection().prepareStatement(sqlstatement);
            pst.setInt(1, id);
            ResultSet rs = dbc.executeQuery(pst);
            while (rs.next()) {
                String bezeichnung = rs.getString("name");
                erg = new Genre(id, bezeichnung);
            }
            rs.close();
        } catch (SQLException ex) {
            return null;
        }
        return erg;
    }

    public static Cover getCover(int FILM_ID) {
        Cover cover = new Cover();

        cover.setFilm_id(FILM_ID);

        String sqlstatement = "SELECT cover FROM tbl_film WHERE FI_ID = " + String.valueOf(cover.getFilm_id());

        DBController dbc = Control.getInstance().getDbManager();

        ResultSet rs = dbc.executeQuery(sqlstatement);

        try {
            while (rs.next()) {
                String covername = rs.getString("cover");
                BufferedImage nocover = null;

                try {
                    nocover = ImageIO.read(DBOperations.class
                            .getClassLoader().getResourceAsStream("de/codekings/server/main/NoCover.jpg"));
                } catch (IOException ex) {
                    System.out.println(ex.getMessage());
                }
                if (covername.equals("")) {
                    cover.setCover(JSON_Parser.encodeToString(nocover, "jpg"));
                } else {
                    try {
                        File coverfile = new File("./covers/" + covername);
                        BufferedImage img = ImageIO.read(coverfile);
                        cover.setCover(JSON_Parser.encodeToString(img, "jpg"));
                    } catch (IOException ex) {
                        cover.setCover(JSON_Parser.encodeToString(nocover, "jpg"));
                    }
                }
            }
            rs.close();
        } catch (SQLException ex) {
        }
        return cover;
    }

    public static void addUser(User u) {
        String sql = "INSERT INTO `tbl_user` ( name, vorname, strasse, plz, ort, passwort, email, accountnummer, hausnr, geburtsdatum) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        DBController dbc = Control.getInstance().getDbManager();

        try {
            PreparedStatement pst = dbc.getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pst.setString(1, u.getName());
            pst.setString(2, u.getVorname());
            pst.setString(3, u.getStrasse());
            pst.setString(4, u.getPlz());
            pst.setString(5, u.getOrt());
            pst.setString(6, u.getPasswort());
            pst.setString(7, u.getEmail());
            pst.setString(8, "");
            pst.setInt(9, u.getHausnr());
            pst.setDate(10, new java.sql.Date(u.getGeburtsdatum().getTime()));

            pst.executeUpdate();
            ResultSet rs = pst.getGeneratedKeys();
            while (rs.next()) {
                u.setU_ID(rs.getInt(1));
            }
            String accnr = String.valueOf(u.getU_ID());
            while (accnr.length() < 10) {
                accnr = "0" + accnr;
            }
            dbc.executeUpdate(pst); //Übernehme userUpdates

            String sql2 = "UPDATE `tbl_user` SET `accountnummer`=?  WHERE (`U_ID`=?)";
            PreparedStatement pst2 = dbc.getConnection().prepareStatement(sql2);
            pst2.setString(1, accnr);
            pst2.setInt(2, u.getU_ID());
            pst2.executeUpdate();

            dbc.executeUpdate(pst2);

        } catch (SQLException ex) {
            Logger.getLogger(DBOperations.class
                    .getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static void updateUser(User u) {
        String sql = "UPDATE `tbl_user` ( name, vorname, strasse, plz, ort, passwort, email, hausnr, geburtsdatum) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?) WHERE (`U_ID`=?)";

        DBController dbc = Control.getInstance().getDbManager();

        try {
            PreparedStatement pst = dbc.getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pst.setString(1, u.getName());
            pst.setString(2, u.getVorname());
            pst.setString(3, u.getStrasse());
            pst.setString(4, u.getPlz());
            pst.setString(5, u.getOrt());
            pst.setString(6, u.getPasswort());
            pst.setString(7, u.getEmail());
            pst.setInt(8, u.getHausnr());
            pst.setDate(9, new java.sql.Date(u.getGeburtsdatum().getTime()));
            pst.setInt(10, u.getU_ID());

            pst.executeUpdate();

            dbc.executeUpdate(pst); //Übernehme userUpdates

        } catch (SQLException ex) {
            Logger.getLogger(DBOperations.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static User getUser(String email) {
        User ergUser = null;

        DBController dbc = Control.getInstance().getDbManager();
        try {
            String sql = "SELECT * FROM tbl_user WHERE email = ?";
            PreparedStatement ps = dbc.getConnection().prepareStatement(sql);

            ps.setString(1, email);

            ResultSet rs = dbc.executeQuery(ps);

            while (rs.next()) {
                String name, vorname, strasse, plz, ort, pw, accnr;
                name = rs.getString("name");
                vorname = rs.getString("surname");
                strasse = rs.getString("street");
                plz = rs.getString("zip_code");
                ort = rs.getString("location");
                pw = rs.getString("password");
                accnr = rs.getString("accountnumber");

                int u_id, hausnr, birthdate;
                u_id = rs.getInt("U_ID");
                hausnr = rs.getInt("street_nr");
                birthdate = rs.getInt("birthdate");

                Date dBirthdate = new Date(birthdate * 1000l);

                ergUser = new User(u_id, name, vorname, strasse, plz, ort, pw, email, accnr, hausnr, dBirthdate, ClassType.T_DVD) {
                };
            }

        } catch (Exception e) {

        }

        return ergUser;
    }

    public static User getUser(int u_id) {
        User ergUser = null;

        DBController dbc = Control.getInstance().getDbManager();
        try {
            String sql = "SELECT * FROM tbl_user WHERE U_ID = ?";
            PreparedStatement ps = dbc.getConnection().prepareStatement(sql);

            ps.setInt(1, u_id);

            ResultSet rs = dbc.executeQuery(ps);

            while (rs.next()) {
                String name, vorname, strasse, plz, ort, pw, accnr, email;
                name = rs.getString("name");
                vorname = rs.getString("surname");
                strasse = rs.getString("street");
                plz = rs.getString("zip_code");
                ort = rs.getString("location");
                pw = rs.getString("password");
                accnr = rs.getString("accountnumber");
                email = rs.getString("email");

                int hausnr, birthdate;
                hausnr = rs.getInt("street_nr");
                birthdate = rs.getInt("birthdate");

                Date dBirthdate = new Date(birthdate * 1000l);

                ergUser = new User(u_id, name, vorname, strasse, plz, ort, pw, email, accnr, hausnr, dBirthdate, ClassType.T_DVD) {
                };
            }

        } catch (Exception e) {

        }

        return ergUser;
    }

    public static ArrayList<Integer> getUserIDs() {
        ArrayList<Integer> erg = new ArrayList();

        String sql = "SELECT U_ID FROM tbl_user;";

        DBController dbc = Control.getInstance().getDbManager();
        try {

            PreparedStatement ps = dbc.getConnection().prepareStatement(sql);

            ResultSet rs = dbc.executeQuery(ps);

            while (rs.next()) {
                erg.add(rs.getInt("U_ID"));
            }

        } catch (SQLException e) {

        }

        return erg;

    }

    public static Mitarbeiter getMitarbeiter(int u_id) {
        Mitarbeiter ma = null;
        User u = getUser(u_id);

        if (u != null) {
            try {
                DBController dbc = Control.getInstance().getDbManager();
                String sql = "SELECT * FROM tbl_mitarbeiter WHERE U_ID = ?";
                PreparedStatement ps = dbc.getConnection().prepareStatement(sql);

                ps.setInt(1, u.getU_ID());

                ResultSet rs = dbc.executeQuery(ps);

                while (rs.next()) {
                    int ma_id = rs.getInt("MA_ID");
                    int perm = rs.getInt("permission");

                    ma = new Mitarbeiter(u_id, u.getName(), u.getVorname(), u.getStrasse(), u.getPlz(), u.getOrt(), u.getPasswort(),
                            u.getEmail(), u.getAccountnummer(), u.getHausnr(), u.getGeburtsdatum(), ma_id, perm);
                }
            } catch (Exception e) {
            }
        }
        return ma;
    }

    public static Kunde getKunde(int u_id) {
        Kunde ku = null;
        User u = getUser(u_id);

        if (u != null) {
            try {
                DBController dbc = Control.getInstance().getDbManager();
                String sql = "SELECT * FROM tbl_kunde WHERE U_ID = ?";
                PreparedStatement ps = dbc.getConnection().prepareStatement(sql);

                ps.setInt(1, u.getU_ID());

                ResultSet rs = dbc.executeQuery(ps);

                while (rs.next()) {
                    int ku_id = rs.getInt("KU_ID");
                    java.sql.Date joining = rs.getDate("date_of_joining");
                    double accountbalance = rs.getDouble("account_balance");

                    ku = new Kunde(u_id, u.getName(), u.getVorname(), u.getStrasse(), u.getPlz(), u.getOrt(), u.getPasswort(),
                            u.getEmail(), u.getAccountnummer(), u.getHausnr(), u.getGeburtsdatum(), ku_id, joining, accountbalance, ClassType.T_KUNDE);
                }
            } catch (Exception e) {
            }
        }
        return ku;
    }

    public static String getFilmProperty(int filmid, String column) {
        String erg = "";

        String sqlstatement = "SELECT " + column + " FROM tbl_film";

        DBController dbc = Control.getInstance().getDbManager();

        ResultSet rs = dbc.executeQuery(sqlstatement);

        try {
            while (rs.next()) {
                erg = rs.getString(column);
            }
            rs.close();
        } catch (SQLException ex) {
        }

        return erg;
    }

    public static void setFilmRemoved(int filmid) {
        String sql = "UPDATE `tbl_film` SET `removed`='1' WHERE (`FI_ID`='" + filmid + "')";

        DBController dbc = Control.getInstance().getDbManager();

        dbc.executeUpdate(sql);
    }

    public static void setNoCover(int filmid) {
        String sql = "UPDATE `tbl_film` SET `cover`='NoCover.jpg' WHERE (`FI_ID`='" + filmid + "')";

        DBController dbc = Control.getInstance().getDbManager();

        dbc.executeUpdate(sql);
    }

    public static int addGenre(String bez) {
        int id = -1;
        String sql = "INSERT INTO `tbl_genre` (`name`) VALUES (?)";

        DBController dbc = Control.getInstance().getDbManager();

        try {
            PreparedStatement pst = dbc.getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pst.setString(1, bez);
            dbc.executeUpdate(pst);
            ResultSet rs = pst.getResultSet();

            while (rs.next()) {
                id = rs.getInt(1);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return id;
    }

    public static int addFilm(Film f, int editor) {
        String sql = "INSERT INTO `tbl_film` (`title`, `subtitle`, `desc`, `fsk`, "
                + "`rating`, `cover`, `trailer`, `actor`, `regie`, `release_date`, "
                + "`duration`, `preis`, `last_edit_by`, `last_edit`) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, '0.5', ?, ?)";

        String sql2 = "INSERT INTO tbl_genre_zuordnung (FI_ID, GE_ID) VALUES (?, ?)";

        DBController dbc = Control.getInstance().getDbManager();

        try {
            PreparedStatement pst = dbc.getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pst.setString(1, f.getS_titel());
            pst.setString(2, f.getS_subtitel());
            pst.setString(3, f.getS_description());
            pst.setInt(4, f.getI_fsk());
            pst.setInt(5, f.getI_rating());
            pst.setString(6, "");
            pst.setString(7, f.getS_trailer());
            pst.setString(8, f.gibActors());
            pst.setString(9, f.getS_regie());
            java.sql.Date sqldate = new java.sql.Date(1000000l); //java.sql.Date.valueOf(f.getRelease_date().toLocaleString());
            pst.setDate(10, sqldate);
            pst.setInt(11, f.getI_duration());
            //Last EDIT!!
            pst.setInt(12, editor);
            //Current Time
            java.util.Date utilDate = new java.util.Date();
            java.sql.Date lastedit = new java.sql.Date(utilDate.getTime());
            pst.setDate(13, lastedit);

            pst.executeUpdate();
            ResultSet rs = pst.getGeneratedKeys();
            while (rs.next()) {
                f.setFILMID(rs.getInt(1));
            }
            //dbc.executeUpdate(pst);
            for (int i = 0; i <= (f.getGenres().size() - 1); i++) {
                Genre g = f.getGenre(i);
                pst = dbc.getConnection().prepareStatement(sql2);
                pst.setInt(1, f.getFILMID());
                pst.setInt(2, g.getGenre_id());
                dbc.executeUpdate(pst);

            }
        } catch (SQLException ex) {
            Logger.getLogger(DBOperations.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        return f.getFILMID();
    }

    public static void setCover(Cover c) {
        String filmname = getFilmProperty(c.getFilm_id(), "title");
        File coverfile = new File("./covers/" + filmname + ".jpg");
        String finalname = filmname + ".jpg";
        int counter = 1;
        while (coverfile.exists()) {
            coverfile = new File("./covers/" + filmname + String.valueOf(counter) + ".jpp");
            finalname = filmname + String.valueOf(counter) + ".jpp";
        }
        try {
            ImageIO.write(JSON_Parser.decodeToImage(c.getCover()), "jpg", coverfile);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        String sql = "UPDATE `tbl_film` SET `cover`=? WHERE (`FI_ID`=?)";
        DBController dbc = Control.getInstance().getDbManager();
        try {
            PreparedStatement pst = dbc.getConnection().prepareStatement(sql);
            pst.setString(1, finalname);
            pst.setInt(2, c.getFilm_id());

        } catch (SQLException ex) {
            Logger.getLogger(DBOperations.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void UpdateFilm(Film f, int u_id) {
        String sql = "UPDATE `tbl_film` SET `title`=?, `subtitle`=? , `desc`=? , fsk=?, rating=?, trailer=?, actor=?, "
                + "regie=?, release_date=?, duration=?, last_edit_by=?, last_edit=? WHERE (`FI_ID`=?)";
        String deleteGenreZuordnung = "DELETE FROM `tbl_genre_zuordnung` WHERE (`GEZ_ID`=?)";
        String sql2 = "INSERT INTO tbl_genre_zuordnung (FI_ID, GE_ID) VALUES (?, ?)";

        DBController dbc = Control.getInstance().getDbManager();

        try {
            PreparedStatement pst = dbc.getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pst.setString(1, f.getS_titel());
            pst.setString(2, f.getS_subtitel());
            pst.setString(3, f.getS_description());
            pst.setInt(4, f.getI_fsk());
            pst.setInt(5, f.getI_rating());

            pst.setString(6, f.getS_trailer());
            pst.setString(7, f.gibActors());
            pst.setString(8, f.getS_regie());
            java.sql.Date sqldate = new java.sql.Date(1000000l); //java.sql.Date.valueOf(f.getRelease_date().toLocaleString());
            pst.setDate(9, sqldate);
            pst.setInt(10, f.getI_duration());
            //Last EDIT!!
            pst.setInt(11, u_id);
            //Current Time
            java.util.Date utilDate = new java.util.Date();
            java.sql.Date lastedit = new java.sql.Date(utilDate.getTime());
            pst.setDate(12, lastedit);

            pst.setInt(13, f.getFILMID());

            dbc.executeUpdate(pst); //Übernehme FilmUpdates

            pst = dbc.getConnection().prepareStatement(deleteGenreZuordnung);
            pst.setInt(1, f.getFILMID());
            dbc.executeUpdate(pst); //Lösche alle Genre Zuordnungen

            for (int i = 0; i <= (f.getGenres().size() - 1); i++) { //Setze die Genrezuordnung neu
                Genre g = f.getGenre(i);
                pst = dbc.getConnection().prepareStatement(sql2);
                pst.setInt(1, f.getFILMID());
                pst.setInt(2, g.getGenre_id());
                dbc.executeUpdate(pst);

            }
        } catch (SQLException ex) {
            Logger.getLogger(DBOperations.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void updateCover(Cover c, String covername) {
        File coverfile = new File("./covers/" + covername);
        try {
            ImageIO.write(JSON_Parser.decodeToImage(c.getCover()), "jpg", coverfile);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static boolean isReserviert(int dvdid) {
        boolean erg = false;

        String sql = "SELECT * FROM tbl_reservierung WHERE gueltig = 1 AND DVD_ID = " + dvdid;

        DBController dbc = Control.getInstance().getDbManager();

        ResultSet rs = dbc.executeQuery(sql);

        try {
            while (rs.next()) {
                erg = true;
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return erg;
    }

    public static boolean isAusgeliehen(int dvdid) {
        boolean erg = false;

        String sql = "SELECT * FROM tbl_ausleihe WHERE returned = 0 AND DVD_ID = " + dvdid + "";

        DBController dbc = Control.getInstance().getDbManager();

        ResultSet rs = dbc.executeQuery(sql);
        try {
            while (rs.next()) {
                erg = true;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return erg;
    }

    public static ArrayList<DVD> getDVDs(int filmid) {
        ArrayList<DVD> erg = new ArrayList<>();

        String sql = "SELECT DVD_ID FROM tbl_dvd WHERE FI_ID = " + filmid;
        DBController dbc = Control.getInstance().getDbManager();
        ResultSet rs = dbc.executeQuery(sql);

        try {
            while (rs.next()) {
                int dvdid = rs.getInt("DVD_ID");
                DVD neueDVD = getDVD(dvdid);
                erg.add(neueDVD);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBOperations.class.getName()).log(Level.SEVERE, null, ex);
        }
        return erg;
    }

    public static DVD getDVD(int dvdid) {
        String sql = "SELECT * FROM tbl_dvd WHERE DVD_ID = " + dvdid;
        DBController dbc = Control.getInstance().getDbManager();
        ResultSet rs = dbc.executeQuery(sql);
        try {
            while (rs.next()) {

                DVD neueDVD = new DVD(dvdid);
                neueDVD.setFilm(getFilm(rs.getInt("FI_ID")));
                neueDVD.setS_artikelnr(String.valueOf(rs.getInt("art_nr")));
                neueDVD.setS_notiz(rs.getString("notice"));
                return neueDVD;

            }
        } catch (SQLException ex) {
            Logger.getLogger(DBOperations.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public static boolean removeDVD(int dvdid) {
        if (!isAusgeliehen(dvdid)) {
            DBController dbc = Control.getInstance().getDbManager();
            //Remove aus Ausgeliehen
            String sql = "DELETE FROM `tbl_dvd` WHERE (`DVD_ID`= " + dvdid + " )";
            dbc.executeUpdate(sql);
            //Remove aus Reserviert
            sql = "DELETE FROM `tbl_ausleihe` WHERE (`DVD_ID`= " + dvdid + " )";
            dbc.executeUpdate(sql);
            //Remove aus DVD
            sql = "DELETE FROM `tbl_reservierung` WHERE (`DVD_ID`= " + dvdid + " )";
            dbc.executeUpdate(sql);
            return true;
        }
        return false;
    }

    public static boolean addDVD(DVD d, int editor) {
        boolean erg = false;
        DBController dbc = Control.getInstance().getDbManager();
        //Test if Artikelnummer vorhanden
        String sql = "SELECT * FROM tbl_DVD WHERE art_nr = " + d.getS_artikelnr();
        ResultSet rs = dbc.executeQuery(sql);

        boolean exist = false;
        try {
            while (rs.next()) {
                exist = true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBOperations.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (exist) {
            return erg;
        }
        String insert = "INSERT INTO `tbl_dvd` (`art_nr`, `FI_ID`, `lent`, `notice`, `last_edit_by`, `last_edit`) VALUES (?, ?, '0', ?, ?, ?)";
        PreparedStatement pst;
        try {
            pst = dbc.getConnection().prepareStatement(insert);

            pst.setInt(1, Integer.parseInt(d.getS_artikelnr()));
            pst.setInt(2, d.getFilm().getFILMID());
            pst.setString(3, d.getS_notiz());
            pst.setInt(4, editor);
            java.util.Date now = new java.util.Date();
            Date sqlDate = new Date(now.getTime());
            pst.setDate(5, sqlDate);

            dbc.executeUpdate(pst);
            erg = true;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return erg;
    }

    public static boolean updateDVD(DVD d, int editor) {
        boolean erg = false;
        DBController dbc = Control.getInstance().getDbManager();
        //Test if Artikelnummer vorhanden
        String sql = "SELECT * FROM tbl_DVD WHERE art_nr = " + d.getS_artikelnr() + " AND NOT DVD_ID = " + d.getDVDID();
        ResultSet rs = dbc.executeQuery(sql);

        boolean exist = false;
        try {
            while (rs.next()) {
                exist = true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBOperations.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (exist) {
            return erg;
        }
        String update = "UPDATE `tbl_dvd` SET `notice`=?, art_nr=?, last_edit_by=?, last_edit=? WHERE (`DVD_ID`=?)";
        PreparedStatement pst;
        try {
            pst = dbc.getConnection().prepareStatement(update);

            pst.setInt(2, Integer.parseInt(d.getS_artikelnr()));
            pst.setString(1, d.getS_notiz());
            pst.setInt(3, editor);
            java.util.Date now = new java.util.Date();
            Date sqlDate = new Date(now.getTime());
            pst.setDate(4, sqlDate);
            pst.setInt(5, d.getDVDID());

            dbc.executeUpdate(pst);
            erg = true;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return erg;
    }

    public static void setReservierungUngueltig(int resid) {
        String sql = "UPDATE `tbl_reservierung` SET `gueltig`='0' WHERE (`RES_ID`=?)";

        DBController dbc = Control.getInstance().getDbManager();

        try {
            PreparedStatement pst = dbc.getConnection().prepareStatement(sql);
            pst.setInt(1, resid);
            dbc.executeUpdate(pst);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public static void updateGueltigkeit() {
        String sql = "SELECT * FROM tbl_reservierung WHERE gueltig = 1";

        DBController dbc = Control.getInstance().getDbManager();

        ResultSet rs = dbc.executeQuery(sql);
        try {
            while (rs.next()) {
                java.util.Date now = new java.util.Date();
                java.util.Date resDate = new java.util.Date(rs.getLong("reservierungsdatum"));
                long diff = now.getTime() - resDate.getTime();
                if (TimeUnit.MILLISECONDS.toMinutes(diff) > 20) {
                    setReservierungUngueltig(rs.getInt("RES_ID"));
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public static boolean ausleihe(int ku_id, java.util.Date ende) {
        boolean erg = false;
        ArrayList<Reservierung> res = getReservierungOfKunde2(ku_id);
        ArrayList<DVD> dvds = getReservierungOfKunde(ku_id);
        DBController dbc = Control.getInstance().getDbManager();
        for (DVD r : dvds) {
            String sql = "INSERT INTO `tbl_ausleihe` (`KU_ID`, `DVD_ID`, `begindate`, `enddate`, `returned`) VALUES (?, ?, ?, ?, '0')";
            try {
                PreparedStatement pst = dbc.getConnection().prepareStatement(sql);
                pst.setInt(1, ku_id);
                pst.setInt(2, r.getDVDID());
                java.util.Date now = new java.util.Date();
                Date sqlnow = new Date(now.getTime());
                pst.setDate(3, sqlnow);
                pst.setDate(4, new Date(ende.getTime()));

                dbc.executeUpdate(pst);
            } catch (SQLException e) {
                erg = false;
                e.printStackTrace();
            }
        }
        for(Reservierung r: res){
            setReservierungUngueltig(r.getResid());
        }
        erg = true;

        return erg;
    }

    public static Ausleihe getAusleihe(int art_nr){
        String sql = "SELECT * FROM tbl_ausleihe a, tbl_dvd d WHERE art_nr = " + art_nr + " AND d.DVD_ID = a.DVD_ID AND returned = 0";
        DBController dbc = Control.getInstance().getDbManager();
        Ausleihe a = new Ausleihe();
        ResultSet rs = dbc.executeQuery(sql);
        try {
            while(rs.next()){
                java.util.Date begin = new Date(rs.getDate("begindate").getTime());
                java.util.Date ende = new Date(rs.getDate("enddate").getTime());
                a = new Ausleihe(rs.getInt("AUS_ID"), -1, begin, ende, getDVD(rs.getInt("DVD_ID")));
                
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBOperations.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return a;
    }
    
    public static void setRueckgabe(int aus_id){
        String sql = "UPDATE `tbl_ausleihe` SET `returned`='1' WHERE (`AUS_ID`=?)";

        DBController dbc = Control.getInstance().getDbManager();

        try {
            PreparedStatement pst = dbc.getConnection().prepareStatement(sql);
            pst.setInt(1, aus_id);
            dbc.executeUpdate(pst);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
    public static boolean reserviereEineDVD(int filmid, int kundenid) {
        ArrayList<DVD> alleDVDs = getDVDs(filmid);
        DBController dbc = Control.getInstance().getDbManager();
        boolean erfolgreich = false;
        if (alleDVDs.size() > 0) {
            DVD dvd = null;
            System.out.println(filmid);
            for (DVD d : alleDVDs) {

                if (!isReserviert(d.getDVDID()) && !isAusgeliehen(d.getDVDID())) {
                    dvd = d;
                    break;
                }
            }
            if (dvd != null) {
                String insert = "INSERT INTO `tbl_reservierung` (`KU_ID`, `DVD_ID`, `reservierungsdatum`, `gueltig`) "
                        + "VALUES (?, ?, ?, '1')";
                try {
                    PreparedStatement pst = dbc.getConnection().prepareStatement(insert);
                    pst.setInt(1, kundenid);
                    pst.setInt(2, dvd.getDVDID());
                    java.util.Date now = new java.util.Date();
                    pst.setLong(3, now.getTime());

                    dbc.executeUpdate(pst);
                    erfolgreich = true;
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        }

        return erfolgreich;
    }
    public static ArrayList<Ausleihe> getAusleihen(int ku_id) {
        ArrayList<Ausleihe> erg = new ArrayList<>();
        String sql = "SELECT * FROM tbl_ausleihe WHERE KU_ID = " + ku_id + " AND gueltig = 1 ORDER BY ausleihdatum ASC";
        DBController dbc = Control.getInstance().getDbManager();

        ResultSet rs = dbc.executeQuery(sql);

        try {
            while (rs.next()) {
                if (isAusgeliehen(rs.getInt("DVD_ID"))) {
                    //erg.add(getDVD(rs.getInt("DVD_ID")));
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBOperations.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        return erg;
    }
    
    public static ArrayList<DVD> getReservierungOfKunde(int ku_id) {
        ArrayList<DVD> erg = new ArrayList<>();
        String sql = "SELECT * FROM tbl_reservierung WHERE KU_ID = " + ku_id + " AND gueltig = 1 ORDER BY reservierungsdatum ASC";
        DBController dbc = Control.getInstance().getDbManager();

        ResultSet rs = dbc.executeQuery(sql);

        try {
            while (rs.next()) {
                if (isReserviert(rs.getInt("DVD_ID"))) {
                    erg.add(getDVD(rs.getInt("DVD_ID")));
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBOperations.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        return erg;
    }

    public static ArrayList<Reservierung> getReservierungOfKunde2(int ku_id) {
        ArrayList<Reservierung> erg = new ArrayList<>();
        String sql = "SELECT * FROM tbl_reservierung WHERE KU_ID = " + ku_id + " AND gueltig = 1";
        DBController dbc = Control.getInstance().getDbManager();

        ResultSet rs = dbc.executeQuery(sql);

        try {
            int counter = 1;
            while (rs.next()) {
                if (isReserviert(rs.getInt("DVD_ID"))) {
                    DVD d = getDVD(rs.getInt("DVD_ID"));
                    String filmname = d.getFilm().getS_titel();
                    if (!d.getFilm().getS_subtitel().equals("")) {
                        filmname = filmname + " - " + d.getFilm().getS_subtitel();
                    }
                    Date gueltigbis = new Date(rs.getLong("reservierungsdatum") + 1200000);
                    Reservierung r = new Reservierung(rs.getInt("RES_ID"), counter, filmname, Integer.parseInt(d.getS_artikelnr()), gueltigbis, d.getFilm().getD_preis());
                    erg.add(r);
                }
                counter++;
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBOperations.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        return erg;
    }

    public static int getAnzahlReservierungZuFilm(int filmid) {
        String sql = "SELECT Count(*) FROM tbl_reservierung r, tbl_dvd d WHERE d.FI_ID = " + filmid + " AND r.DVD_ID = d.DVD_ID AND gueltig = 1";
        DBController dbc = Control.getInstance().getDbManager();
        int erg = 0;
        ResultSet rs = dbc.executeQuery(sql);

        try {
            while (rs.next()) {
                erg = rs.getInt(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBOperations.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        return erg;
    }

    public static int getAnzahlAusleiheZuFilm(int filmid) {
        String sql = "SELECT Count(*) FROM tbl_ausleihe a, tbl_dvd d WHERE d.FI_ID = " + filmid + " AND a.DVD_ID = d.DVD_ID AND returned = 0";
        DBController dbc = Control.getInstance().getDbManager();
        int erg = 0;
        ResultSet rs = dbc.executeQuery(sql);
        try {
            while (rs.next()) {
                erg = rs.getInt(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBOperations.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        return erg;
    }

}
