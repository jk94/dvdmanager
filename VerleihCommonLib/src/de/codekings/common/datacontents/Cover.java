/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.codekings.common.datacontents;

import de.codekings.common.Enumerators.ClassType;
import de.codekings.common.json.JSON_Parser;
import java.awt.image.BufferedImage;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;

/**
 *
 * @author Jan
 */
public class Cover extends Sendable {

    private int film_id;
    private String cover;

    public Cover() {
        super(ClassType.T_COVER);
        cover = "";
    }
    
    public Cover(int filmid){
        super(ClassType.T_COVER);
        cover = "";
        film_id = filmid;
    }

    public Cover(int film_id, String cover) {
        super(ClassType.T_COVER);
        this.film_id = film_id;
        this.cover = cover;
    }

    public Image gibCoverImage() {
        if (!cover.equals("")) {
            BufferedImage bi = JSON_Parser.decodeToImage(this.cover);
            WritableImage returnIt = new WritableImage(bi.getWidth(), bi.getHeight());
            SwingFXUtils.toFXImage(bi, returnIt);
            return returnIt;
        } else {
            return null;
        }
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public int getFilm_id() {
        return film_id;
    }

    public void setFilm_id(int film_id) {
        this.film_id = film_id;
    }

}
