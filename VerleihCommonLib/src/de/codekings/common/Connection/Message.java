/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package de.codekings.common.Connection;

import de.codekings.common.datacontents.Sendable;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author Jan
 */
public class Message {
    
    private ArrayList<Sendable> content;
    private String command;
    private HashMap<String, String> additionalparameter;
    
    public Message(String command){
        content = new ArrayList<>();
        this.command = command;
         additionalparameter = new HashMap<>();
    }
    
    public Message(){
        content = new ArrayList<>();
        additionalparameter = new HashMap<>();
    }
    
    public Message(String command, ArrayList<Sendable> content, HashMap<String, String> additionalparameter){
        this.content = content;
        this.command = command;
        this.additionalparameter = additionalparameter;
    }

    public ArrayList<Sendable> getContent() {
        return content;
    }

    public void setContent(ArrayList<Sendable> content) {
        this.content = content;
    }

    public HashMap<String, String> getAdditionalparameter() {
        return additionalparameter;
    }

    public void setAdditionalparameter(HashMap<String, String> additionalparameter) {
        this.additionalparameter = additionalparameter;
    }
    
    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }
    
    public void addAdditionalParameter(String key, String value){
            additionalparameter.put(key, value);
    }
    
    public void removeAdditionalParameter(String key){
        if(additionalparameter.containsKey(key)){
            additionalparameter.remove(key);
        }
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
