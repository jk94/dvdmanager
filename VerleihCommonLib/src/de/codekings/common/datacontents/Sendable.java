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
public abstract class Sendable {

    private ClassType cltype;
    
    public final ClassType getClassType(){
        return  cltype;
    }
    
    public final void setClassType(ClassType cltype){
        this.cltype = cltype;
    }

    public Sendable(ClassType t){
        this.cltype = t;
    }
}
