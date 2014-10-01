package de.codekings.client.Controls;

import de.codekings.client.Enum.ContentPageType;
import java.util.ArrayList;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Jan
 */
public class Verlaufsmanager {

    private ArrayList<ContentPageType> liste;
    private int pageindex = -1;

    public Verlaufsmanager() {
        liste = new ArrayList<>();
    }

    public void addPage(ContentPageType pt) {
        ArrayList<ContentPageType> temp_liste = new ArrayList<>();
        for(int i = 0; i<=pageindex;i++){
            temp_liste.add(liste.get(i));
        }
        temp_liste.add(pt);
        liste = temp_liste;
        pageindex = liste.size() - 1;
    }

    public ContentPageType nextPage(ContentPageType pt) {
        ContentPageType t;
        t = liste.get(pageindex + 1);
        return t;
    }

    public ContentPageType backPage() {
        ContentPageType t = liste.get(pageindex - 1);
        return t;
    }
    
    public void increaseIndex(){
        pageindex++;
    }
    
    public void decreaseIndex(){
        pageindex--;
    }

    public boolean canNext() {
        return pageindex < liste.size() - 1;
    }

    public boolean canBack() {
        return pageindex > 0;
    }

    public ContentPageType getPage() {
        if (liste.size() > 0) {
            ContentPageType t = liste.get(pageindex);
            return t;
        } else {
            return null;
        }
    }

    public int getPageIndex() {
        return pageindex;
    }

}
