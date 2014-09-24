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
public class Kunde extends User{

    private Date joiningDate;
    private double accountbalance;
    
    
    public Kunde(int uid, ClassType t) {
        super(uid, t);
    }

    public Kunde(Date joiningDate, double accountbalance, int uid, ClassType t) {
        super(uid, t);
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
