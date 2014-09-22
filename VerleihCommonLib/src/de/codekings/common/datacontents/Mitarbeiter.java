/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package de.codekings.common.datacontents;

import de.codekings.common.Enumerators.ClassType;

/**
 *
 * @author Jan
 */
public class Mitarbeiter extends User{
    
    private int ma_id;
    private int permission;
    
    public Mitarbeiter(int u_id){
        super(u_id, ClassType.T_MITARBEITER);
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
