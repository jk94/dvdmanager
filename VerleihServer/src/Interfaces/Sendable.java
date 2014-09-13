/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interfaces;

import Enumerators.ClassType;

/**
 *
 * @author Jan
 */
public interface Sendable {

    String cmd = "";
    
    Sendable();
    
    public ClassType getClassType();

    public String getProtocolCommand();

    /**
     *
     * @param cmd
     */
    void setProtocolCommand(String cmd);

}
