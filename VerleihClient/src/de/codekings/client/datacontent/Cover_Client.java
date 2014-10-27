/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.codekings.client.datacontent;

import de.codekings.common.datacontents.Cover;
import javafx.scene.image.Image;

/**
 *
 * @author Jan
 */
public class Cover_Client extends Cover {

    private Image imgCover;

    public Cover_Client(int film_id, Image cover) {
        super(film_id);
        this.imgCover = cover;
    }

    @Override
    public Image gibCoverImage() {
        return imgCover;
    }

    public void setCoverImage(Image cover) {
        this.imgCover = cover;
    }

    @Override
    public void setCover(String cover) {
        super.setCover(cover);
    }

    @Override
    public String getCover() {
        return super.getCover(); //To change body of generated methods, choose Tools | Templates.
    }
    
    

}
