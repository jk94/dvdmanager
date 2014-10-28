/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.codekings.common.datacontents;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import de.codekings.common.Enumerators.ClassType;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author Jan
 */
@JsonDeserialize(as = Film.class)
public class Film extends Sendable implements Comparable<Film> {

    private int FILMID;
    private String s_titel = "", s_subtitel = "", s_description = "";
    private String s_trailer = "", s_regie = "", s_FSK = "";
    private int i_rating = -1, i_duration = -1, i_fsk = 0;
    private double d_preis = 0.0;
    private Date release_date;
    private ArrayList<String> li_actors;
    private ArrayList<Genre> li_genre;

    public Film(int fid) {
        super(ClassType.T_FILM);
        FILMID = fid;
        li_actors = new ArrayList<>();
        li_genre = new ArrayList<>();
    }

    public Film() {
        super(ClassType.T_FILM);
    }

    public Film(int FILMID, Date release_date, ArrayList<String> li_actors,
            ArrayList<Genre> li_genre, String ti, String suti, String des, String tr,
            String re, String fsk, int rat, int dur, int fsk_i, double p, ClassType t) {
        super(t);
        this.FILMID = FILMID;
        this.release_date = release_date;
        this.li_actors = li_actors;
        this.li_genre = li_genre;
        this.s_trailer = tr;
        this.s_titel = ti;
        this.s_FSK = fsk;
        this.s_description = des;
        this.s_regie = re;
        this.s_subtitel = suti;
        this.i_duration = dur;
        this.i_fsk = fsk_i;
        this.i_rating = rat;
    }

    public ArrayList<Genre> getLi_genre() {
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
    
    public void setActors(ArrayList<String> actors) {
        this.li_actors = actors;
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

    public void addGenre(Genre genre) {
        if (!li_genre.contains(genre)) {
            li_genre.add(genre);
        }
    }

    public ArrayList<Genre> getGenres() {
        return li_genre;
    }

    public Genre getGenre(int i) {
        if (i < li_genre.size() && i >= 0) {
            return li_genre.get(i);
        }
        return null;
    }

    public void setFILMID(int FILMID) {
        this.FILMID = FILMID;
    }

    public void setLi_actors(ArrayList<String> li_actors) {
        this.li_actors = li_actors;
    }

    public void setLi_genre(ArrayList<Genre> li_genre) {
        this.li_genre = li_genre;
    }

    public ArrayList<String> getLi_actors() {
        return li_actors;
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

   @Override
    public int compareTo(Film t) {
        return this.getS_titel().compareTo(t.getS_titel());
    }
}
