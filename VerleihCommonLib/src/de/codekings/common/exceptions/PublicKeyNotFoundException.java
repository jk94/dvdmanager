/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package de.codekings.common.exceptions;

/**
 *
 * @author Jan
 */
public class PublicKeyNotFoundException extends Exception{

    @Override
    public String getMessage() {
        return "PublicKey not Found";
    }
    
    
    
}
