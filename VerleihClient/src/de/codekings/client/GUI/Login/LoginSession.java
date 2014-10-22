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

    private String email = "", hashedpw = "", sessionhash = "";
    private Date logintime = null;
    private int permission = 0;

    public LoginSession(String email, String hashedpw, String sessionhash) {
        this.email = email;
        this.hashedpw = hashedpw;
        this.sessionhash = sessionhash;
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

    public void setPermission(int value) {
        this.permission = value;
    }

    public DateFormat getLoginTime() {
        
        
        return null;
    }

    public String getSessionhash() {
        return sessionhash;
    }
}
