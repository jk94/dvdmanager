/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package contents;

/**
 *
 * @author Jan
 */
public class DVD {

    private final int DVDID;
    private String s_artikelnr = "", s_notiz = "", s_lastedit = "", s_lasteditdate = "";
    private Film film;
    private boolean ausgeliehen;

    public DVD(int id) {
        DVDID = id;
    }

    public int getDVDID() {
        return DVDID;
    }
    
    public String getS_lastedit() {
        return s_lastedit;
    }

    public void setS_lastedit(String s_lastedit) {
        this.s_lastedit = s_lastedit;
    }

    public String getS_lasteditdate() {
        return s_lasteditdate;
    }

    public void setS_lasteditdate(String s_lasteditdate) {
        this.s_lasteditdate = s_lasteditdate;
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
