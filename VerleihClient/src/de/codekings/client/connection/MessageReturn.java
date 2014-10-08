/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.codekings.client.connection;

import de.codekings.common.Connection.Message;

/**
 *
 * @author Jan
 */
public interface MessageReturn {
    
    public abstract void returnedMessage(Message m);
    
}
