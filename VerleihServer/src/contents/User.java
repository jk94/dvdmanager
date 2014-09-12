/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package contents;

import java.util.Date;

/**
 *
 * @author Jan
 */
public abstract class User {
    
    private final int U_ID;
    private String name, vorname, strasse, plz, ort;
    private String passwort, email, accountnummer;
    private int hausnr;
    private Date geburtsdatum;
      
    public User(int uid){
        this.U_ID = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVorname() {
        return vorname;
    }

    public void setVorname(String vorname) {
        this.vorname = vorname;
    }

    public String getStrasse() {
        return strasse;
    }

    public void setStrasse(String strasse) {
        this.strasse = strasse;
    }

    public String getPlz() {
        return plz;
    }

    public void setPlz(String plz) {
        this.plz = plz;
    }

    public String getOrt() {
        return ort;
    }

    public void setOrt(String ort) {
        this.ort = ort;
    }

    public String getPasswort() {
        return passwort;
    }

    public void setPasswort(String passwort) {
        this.passwort = passwort;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAccountnummer() {
        return accountnummer;
    }

    public void setAccountnummer(String accountnummer) {
        this.accountnummer = accountnummer;
    }

    public int getHausnr() {
        return hausnr;
    }

    public void setHausnr(int hausnr) {
        this.hausnr = hausnr;
    }

    public Date getGeburtsdatum() {
        return geburtsdatum;
    }

    public void setGeburtsdatum(Date geburtsdatum) {
        this.geburtsdatum = geburtsdatum;
    }

    public int getU_ID() {
        return U_ID;
    }
    
    public boolean authUser(String email, String pw){
        if(email.equalsIgnoreCase(this.email)){
            if(pw.equals(this.passwort)){
                return true;
            }
        }
        return false;
    }
}
