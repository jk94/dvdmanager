/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.codekings.server.controls;

import de.codekings.common.datacontents.Cover;
import de.codekings.common.datacontents.Film;
import java.util.ArrayList;

/**
 *
 * @author Jan
 */
public class CoverManager {

    private ArrayList<Cover> coverlist;

    public CoverManager() {
        //FirstLoad
        reloadCovers();
    }

    public Cover getCover(int filmid) {
        Cover c = null;

        for (Cover cover : coverlist) {
            if(cover.getFilm_id() == filmid){
                c = cover;
                break;
            }
        }
        if(c != null){
            
        }else{
            //Lade Cover nach
            c = DBOperations.getCover(filmid);
            coverlist.add(c);
        }
        return c;
    }
    
    public final void reloadCovers(){
        Thread t = new Thread(() ->{
        coverlist = new ArrayList<>();
        ArrayList<Film> filme = DBOperations.getFilme();
        for (Film f : filme) {
            coverlist.add(DBOperations.getCover(f.getFILMID()));
            System.out.println("Cover " + f.getFILMID() + " loaded!");
        }
        });
        t.start();
    }

    public void updateCover(int filmid){
        //Find Cover
        int index = -1;
        for(int i = 0; i<coverlist.size();i++){
            if(coverlist.get(i).getFilm_id() == filmid){
                index = i;
                break;
            }
        }
        if(index>0){
            coverlist.remove(index);
            Cover c = DBOperations.getCover(filmid);
            coverlist.add(c);
        }
    }
    
}
