/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.codekings.client.GUI.Katalog;

import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;

/**
 *
 * @author Jan
 */
public class Katalog_ContentView extends Region {

    private Rectangle clip = new Rectangle();
    private VBox liste = new VBox();
    public Katalog_ContentView(Katalogeintrag[] eintraege) {
        setClip(clip);
        // set background gradient using css
        setStyle("-fx-background-color: linear-gradient(to bottom,"
                + " black 60, #141414 60.1%, black 100%);");
        
        for(Katalogeintrag eintrag:eintraege){
            
        }
        
    }

    @Override
    protected void layoutChildren() {
        // update clip to our size
        clip.setWidth(getWidth());
        clip.setHeight(getHeight());
        // keep centered centered
    }

}
