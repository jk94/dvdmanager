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
@JsonDeserialize(as=Mitarbeiter.class)
public class Mitarbeiter extends User{
    
    private int ma_id;
    private int permission;
    
    public Mitarbeiter(int u_id){
        super(u_id, ClassType.T_MITARBEITER);
    }
    
    public Mitarbeiter(){
        super(ClassType.T_MITARBEITER);
    }

    public Mitarbeiter(int ma_id, int permission, int uid, ClassType t) {
        super(uid, t);
        this.ma_id = ma_id;
        this.permission = permission;
    }
    
    public Mitarbeiter(int U_ID, String name, String vorname, String strasse, String plz, String ort, String passwort, String email, String accountnummer, int hausnr, Date geburtsdatum, int ma_id, int permission) {
        super(U_ID, name, vorname, strasse, plz, ort, passwort, email, accountnummer, hausnr, geburtsdatum, ClassType.T_MITARBEITER);
        this.ma_id = ma_id;
        this.permission = permission;
    }

    public int getMa_id() {
        return ma_id;
    }

    public void setMa_id(int ma_id) {
        this.ma_id = ma_id;
    }

    public int getPermission() {
        return permission;
    }

    public void setPermission(int permission) {
        this.permission = permission;
    }
    
    
}
