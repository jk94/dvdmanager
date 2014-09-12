/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package contents;

import Enumerators.LogEnum;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import javax.imageio.ImageIO;
import verleihserver.main;

/**
 *
 * @author Jan
 */
public class Film {

    private final int FILMID;
    private String s_titel = "", s_subtitel = "", s_description = "";
    private String s_trailer = "", s_regie = "", s_FSK = "";
    private int i_rating = -1, i_duration = -1, i_fsk = 0;
    private double d_preis = 0.0;
    private Date release_date;
    private final ArrayList<String> li_actors = new ArrayList<>(), li_awards = new ArrayList<>();
    private final ArrayList<String> li_genre = new ArrayList<>();
    private BufferedImage cover;

    public Film(int fid) {
        FILMID = fid;
    }

    public BufferedImage getCover() {
        return cover;
    }

    public void setCover(BufferedImage cover) {
        this.cover = cover;
    }

    public ArrayList<String> getLi_genre() {
        return li_genre;
    }

    public int getFILMID() {
        return FILMID;
    }

    public String getS_FSK() {
        return s_FSK;
    }

    public void setS_FSK(String s_FSK) {
        this.s_FSK = s_FSK;
    }

    public String getS_regie() {
        return s_regie;
    }

    public void setS_regie(String s_regie) {
        this.s_regie = s_regie;
    }

    public int getI_fsk() {
        return i_fsk;
    }

    public void setI_fsk(int i_fsk) {
        this.i_fsk = i_fsk;
    }

    public ArrayList<String> getActors() {
        return li_actors;
    }

    public String getActor(int i) {
        if (i < li_actors.size() && i >= 0) {
            return li_actors.get(i);
        }
        return "";
    }

    public void addActor(String name) {
        if (!li_actors.contains(name)) {
            li_actors.add(name);
        }
    }

    public void addGenre(String name) {
        if (!li_genre.contains(name)) {
            li_genre.add(name);
        }
    }

    public ArrayList<String> getGenres() {
        return li_genre;
    }

    public String getGenre(int i) {
        if (i < li_genre.size() && i >= 0) {
            return li_genre.get(i);
        }
        return "";
    }

    public ArrayList<String> getAwards() {
        return li_awards;
    }

    public String getAward(int i) {
        if (i < li_awards.size() && i >= 0) {
            return li_awards.get(i);
        }
        return "";
    }

    public void addAward(String name) {
        if (!li_awards.contains(name)) {
            li_awards.add(name);
        }
    }

    public String getS_titel() {
        return s_titel;
    }

    public void setS_titel(String s_titel) {
        this.s_titel = s_titel;
    }

    public String getS_subtitel() {
        return s_subtitel;
    }

    public void setS_subtitel(String s_subtitel) {
        this.s_subtitel = s_subtitel;
    }

    public String getS_description() {
        return s_description;
    }

    public void setS_description(String s_description) {
        this.s_description = s_description;
    }

    public String getS_trailer() {
        return s_trailer;
    }

    public void setS_trailer(String s_trailer) {
        this.s_trailer = s_trailer;
    }

    public int getI_rating() {
        return i_rating;
    }

    public void setI_rating(int i_rating) {
        this.i_rating = i_rating;
    }

    public int getI_duration() {
        return i_duration;
    }

    public void setI_duration(int i_duration) {
        this.i_duration = i_duration;
    }

    public double getD_preis() {
        return d_preis;
    }

    public void setD_preis(double d_preis) {
        this.d_preis = d_preis;
    }

    public Date getRelease_date() {
        return release_date;
    }

    public void setRelease_date(Date release_date) {
        this.release_date = release_date;
    }

    public boolean loadFilmFromDatabase(ResultSet rs) {
        try {
            setS_titel(rs.getString("title"));
            setS_subtitel(rs.getString("subtitle"));
            setS_description(rs.getString("desc"));
            setS_trailer(rs.getString("trailer"));
            setS_FSK(rs.getString("bez"));
            setI_fsk(rs.getInt("alter"));

            try {
                setCover(ImageIO.read(new File("../covers/" + rs.getString("cover"))));
            } catch (IOException ex) {
                main.log(LogEnum.ERROR, ex.getMessage(), main.class);
            }

        } catch (SQLException ex) {
            main.log(LogEnum.ERROR, ex.getMessage(), this);
        }
        return true;
    }

}
