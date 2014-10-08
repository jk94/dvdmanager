package de.codekings.server.controls;

import de.codekings.common.datacontents.Cover;
import de.codekings.common.datacontents.Film;
import de.codekings.common.datacontents.Genre;
import de.codekings.common.json.JSON_Parser;
import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
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
                + "AND g.GE_ID = gz.GE_ID AND f.fsk = fk.FSK_ID ORDER BY f.title, f.subtitle, f.FI_ID ASC";

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

                String actorsunsplitted = rs.getString("actor"), awardsunsplitted = rs.getString("awards");
                String[] actors = new String[0];
                String[] awards = new String[0];
                if (awardsunsplitted.contains(";")) {
                    awards = awardsunsplitted.split(";");
                }
                if (actorsunsplitted.contains(";")) {
                    actors = actorsunsplitted.split(";");
                }
                for (String actor : actors) {
                    neuerFilm.addActor(actor.trim());
                }
                for (String award : awards) {
                    neuerFilm.addActor(award.trim());
                }
                liste.add(neuerFilm);
            }
            //dbc.closeConnection();
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
            //rs.close();
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
                try {
                    File coverfile = new File("../covers/" + covername);

                    cover.setCover(JSON_Parser.encodeToString(ImageIO.read(coverfile), "png"));
                } catch (IOException ex) {
                }
            }
            //rs.close();
        } catch (SQLException ex) {
        }
        return cover;
    }

}
