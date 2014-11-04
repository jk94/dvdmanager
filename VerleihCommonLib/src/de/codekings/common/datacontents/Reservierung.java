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
public class Reservierung extends Sendable{

    private int listnummer, artikelnr, resid;
    private String filmname = "";
    private Date gueltigbis;
    double kosten;
    
    public Reservierung(int resid, int nr, String filmname, int artnr, Date gueltig, double preis){
        super(ClassType.T_RESERVIERUNG);
        this.resid = resid;
        this.artikelnr = artnr;
        this.listnummer = nr;
        this.filmname = filmname;
        this.gueltigbis = gueltig;
        this.kosten = preis;
    }
    
    public Reservierung(){
        super(ClassType.T_RESERVIERUNG);
    }

    public int getResid() {
        return resid;
    }

    public void setResid(int resid) {
        this.resid = resid;
    }
    
    public int getListnummer() {
        return listnummer;
    }

    public void setListnummer(int listnummer) {
        this.listnummer = listnummer;
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

    public Date getGueltigbis() {
        return gueltigbis;
    }

    public void setGueltigbis(Date gueltigbis) {
        this.gueltigbis = gueltigbis;
    }

    public double getKosten() {
        return kosten;
    }

    public void setKosten(double kosten) {
        this.kosten = kosten;
    }
    
    

}
