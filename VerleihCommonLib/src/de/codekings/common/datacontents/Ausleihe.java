/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.codekings.common.datacontents;

import de.codekings.common.Enumerators.ClassType;
import java.util.Date;

/**
 *
 * @author Jan
 */
public class Ausleihe extends Sendable {

    private DVD dvd;
    private int nr, ausleihid;
    private Date beginn, ende;
    private int artikelnr;
    private String filmname = "";

    public Ausleihe(int aus_id, int nr, Date beginn, Date ende, DVD dvd) {
        super(ClassType.T_AUSLEIHE);
        this.ausleihid = aus_id;
        this.nr = nr;
        this.beginn = beginn;
        this.ende = ende;
        this.dvd = dvd;
        this.artikelnr = Integer.parseInt(dvd.getS_artikelnr());
        this.filmname = dvd.getFilm().getS_titel();
        if (!dvd.getFilm().getS_subtitel().equals("")) {
            this.filmname = this.filmname + " - " + dvd.getFilm().getS_subtitel();
        }
    }

    public Ausleihe() {
        super(ClassType.T_AUSLEIHE);
    }

    public int getArtikelnr() {
        return artikelnr;
    }

    public void setArtikelnr(int artikelnr) {
        this.artikelnr = artikelnr;
    }

    public String getFilmname() {
        return filmname;
    }

    public void setFilmname(String filmname) {
        this.filmname = filmname;
    }

    public DVD getDvd() {
        return dvd;
    }

    public void setDvd(DVD dvd) {
        this.dvd = dvd;
    }

    public int getNr() {
        return nr;
    }

    public void setNr(int nr) {
        this.nr = nr;
    }

    public int getAusleihid() {
        return ausleihid;
    }

    public void setAusleihid(int ausleihid) {
        this.ausleihid = ausleihid;
    }

    public Date getBeginn() {
        return beginn;
    }

    public void setBeginn(Date beginn) {
        this.beginn = beginn;
    }

    public Date getEnde() {
        return ende;
    }

    public void setEnde(Date ende) {
        this.ende = ende;
    }

}
