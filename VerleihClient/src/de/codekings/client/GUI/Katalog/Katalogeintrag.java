/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.codekings.client.GUI.Katalog;

import de.codekings.common.datacontents.Film;
import javafx.scene.Parent;

/**
 *
 * @author Jan
 */
class Katalogeintrag {

    private Parent content;
    private Katalog_itemController contentcontroller;
    private Film film;

    public Katalogeintrag(Parent c, Katalog_itemController cc, Film f) {
        this.content = c;
        this.contentcontroller = cc;
        this.film = f;
        contentcontroller.setFilmID(f.getFILMID());
    }

    public Parent getContent() {
        return content;
    }

    public void setContent(Parent content) {
        this.content = content;
    }

    public Katalog_itemController getContentcontroller() {
        return contentcontroller;
    }

    public void setContentcontroller(Katalog_itemController contentcontroller) {
        this.contentcontroller = contentcontroller;
    }

    public Film getFilm() {
        return film;
    }

    public void setFilm(Film film) {
        this.film = film;
    }

}
