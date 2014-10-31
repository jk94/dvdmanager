package de.codekings.server.controls;

import de.codekings.common.Enumerators.ClassType;
import de.codekings.common.datacontents.Cover;
import de.codekings.common.datacontents.Film;
import de.codekings.common.datacontents.Genre;
import de.codekings.common.datacontents.Mitarbeiter;
import de.codekings.common.datacontents.User;
import de.codekings.common.json.JSON_Parser;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.image.Image;
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
        return liste;
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
                    nocover = ImageIO.read(DBOperations.class.getClassLoader().getResourceAsStream("de/codekings/server/main/NoCover.jpg"));
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

    public static Genre addGenre(String bez) {
        Genre erg = null;

        String sql = "INSERT INTO `tbl_genre` (`name`) VALUES (?)";

        DBController dbc = Control.getInstance().getDbManager();

        try {
            PreparedStatement pst = dbc.getConnection().prepareStatement(sql);
            pst.setString(1, bez);
            dbc.executeUpdate(pst);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        String sqlstatement = "SELECT * FROM tbl_genre WHERE name = ?";

        try {
            PreparedStatement pst = dbc.getConnection().prepareStatement(sqlstatement);
            pst.setString(0, bez);
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

}
