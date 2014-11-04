/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.codekings.common.datacontents;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import de.codekings.common.Enumerators.ClassType;

/**
 *
 * @author Jan
 */
@JsonDeserialize(as=DVD.class)
public class DVD extends Sendable {

    private int DVDID;
    private String s_artikelnr = "", s_notiz = "";
    private Film film;
    private boolean ausgeliehen;

    public DVD(int id) {
        super(ClassType.T_DVD);
        DVDID = id;
    }
    
    public DVD(){
        super(ClassType.T_DVD);
    }
    
    public DVD(int DVDID, Film film, boolean ausgeliehen, ClassType t, String artnr, String notiz) {
        super(t);
        this.DVDID = DVDID;
        this.film = film;
        this.ausgeliehen = ausgeliehen;
        this.s_artikelnr = artnr;
        this.s_notiz = notiz;
    }
    
    public int getDVDID() {
        return DVDID;
    }

    public String getS_artikelnr() {
        return s_artikelnr;
    }

    public void setS_artikelnr(String s_artikelnr) {
        this.s_artikelnr = s_artikelnr;
    }

    public String getS_notiz() {
        return s_notiz;
    }

    public void setS_notiz(String s_notiz) {
        this.s_notiz = s_notiz;
    }

    public Film getFilm() {
        return film;
    }

    public void setFilm(Film film) {
        this.film = film;
    }

    public boolean isAusgeliehen() {
        return ausgeliehen;
    }

    public void setAusgeliehen(boolean ausgeliehen) {
        this.ausgeliehen = ausgeliehen;
    }

}
