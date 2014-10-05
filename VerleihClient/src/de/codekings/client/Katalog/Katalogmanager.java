/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.codekings.client.Katalog;

import java.util.ArrayList;
import javafx.scene.Parent;

/**
 *
 * @author Jan
 */
public class Katalogmanager {

    private ArrayList<Katalogeintrag> li_eintraege;

    public Katalogmanager() {
        li_eintraege = new ArrayList<>();
    }

    public void addEintrag(Katalogeintrag e) {
        if (!li_eintraege.contains(e)) {
            li_eintraege.add(e);
        }
    }

    public void removeEintrag(Katalogeintrag e) {
        if (li_eintraege.contains(e)) {
            li_eintraege.remove(e);
        }
    }

    public void removeEintrag(int index) {
        if (index >= 0 && index < li_eintraege.size()) {
            li_eintraege.remove(index);
        }
    }

    public Katalogeintrag getEintrag(int index) {
        if (index >= 0 && index < li_eintraege.size()) {
            return li_eintraege.get(index);
        } else {
            return null;
        }
    }

    public Parent getContentView() {
        Parent p = new Parent() {
        };
        
        
        return p;
    }

}
