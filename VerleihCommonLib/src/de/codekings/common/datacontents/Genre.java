/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.codekings.common.datacontents;

/**
 *
 * @author Jan
 */
public class Genre {
    
    private int genre_id;
    private String genrebezeichnung;

    public Genre(int genre_id, String genrebezeichnung) {
        this.genre_id = genre_id;
        this.genrebezeichnung = genrebezeichnung;
    }

    public Genre() {
    }

    public int getGenre_id() {
        return genre_id;
    }

    public void setGenre_id(int genre_id) {
        this.genre_id = genre_id;
    }

    public String getGenrebezeichnung() {
        return genrebezeichnung;
    }

    public void setGenrebezeichnung(String genrebezeichnung) {
        this.genrebezeichnung = genrebezeichnung;
    }
    
    
    
    
}
