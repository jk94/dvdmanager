/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.codekings.client.datacontent;

import javafx.scene.image.Image;

/**
 *
 * @author Jan
 */
public class Cover_Client {

    private int film_id;
    private Image cover;

    public Cover_Client(int film_id, Image cover) {
        this.film_id = film_id;
        this.cover = cover;
    }

    public Image getCover() {
        return cover;
    }

    public void setCover(Image cover) {
        this.cover = cover;
    }

    public int getFilm_id() {
        return film_id;
    }

    public void setFilm_id(int film_id) {
        this.film_id = film_id;
    }

}
