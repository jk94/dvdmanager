/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package de.codekings.common.Connection;

import de.codekings.common.datacontents.Sendable;
import java.util.ArrayList;

/**
 *
 * @author Jan
 */
public class Message {
    
    private ArrayList<Sendable> content;
    private String command;
    
    public Message(String command){
        content = new ArrayList<>();
        this.command = command;
    }

    public ArrayList<Sendable> getContent() {
        return content;
    }

    public void setContent(ArrayList<Sendable> content) {
        this.content = content;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }
    
    public void addSendable(Sendable s){
        if(!content.contains(s)){
            content.add(s);
        }
    }
    
    public void removeSendable(Sendable s){
        if(content.contains(s)){
            content.remove(s);
        }
    }
    
    
}
