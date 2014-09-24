/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package de.codekings.common.datacontents;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import de.codekings.common.Enumerators.ClassType;
import java.util.Date;

/**
 *
 * @author Jan
 */
@JsonDeserialize(as=Kunde.class)
public class Kunde extends User{

    private Date joiningDate;
    private double accountbalance;
    
    
    public Kunde(int uid, ClassType t) {
        super(uid, t);
    }

    public Kunde(){
        super(ClassType.T_KUNDE);
    }
    
    public Kunde(int uid, String name, String vorname, String strasse, String plz, String ort, String pw, String email, String accnr, int hausnr, Date birthdate, Date joiningDate, double accountbalance,  ClassType t) {
        super(uid, "", vorname, strasse, plz, ort, pw, email, accnr, hausnr, birthdate, t);
        this.joiningDate = joiningDate;
        this.accountbalance = accountbalance;
    }

    public Date getJoiningDate() {
        return joiningDate;
    }

    public void setJoiningDate(Date joiningDate) {
        this.joiningDate = joiningDate;
    }

    public double getAccountbalance() {
        return accountbalance;
    }

    public void setAccountbalance(double accountbalance) {
        this.accountbalance = accountbalance;
    }
    
    
    
    
}
