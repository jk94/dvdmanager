/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.codekings.client.GUI.Login;

import java.text.DateFormat;
import java.util.Date;

/**
 *
 * @author Jan
 */
public class LoginSession {

    private String email = "", hashedpw = "";
    private Date logintime = null;
    private int permission = 0;
    private int fsk = 0;

    public LoginSession(String email, String hashedpw) {
        this.email = email;
        this.hashedpw = hashedpw;
        this.logintime = new Date();
    }

    public String getEmail() {
        return email;
    }

    public String getHashedpw() {
        return hashedpw;
    }

    public int getPermission() {
        return permission;
    }

    public int getFSK(){
        return fsk;
    }
    
    public void setFSK(int fsk){
        this.fsk = fsk;
    }
    
    public void setPermission(int value) {
        this.permission = value;
    }

    public DateFormat getLoginTime() {

        return null;
    }
}
