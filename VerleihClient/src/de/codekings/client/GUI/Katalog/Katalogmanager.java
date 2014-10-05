/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.codekings.client.GUI.Katalog;

import java.io.IOException;
import java.util.ArrayList;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Orientation;
import javafx.scene.Parent;
import javafx.scene.control.Separator;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

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
        VBox p = new VBox();
        for (int i = 1; i < 5; i++) {
            Pane pa = null;
            try {
                FXMLLoader fxmlLoader = new FXMLLoader();
                pa = fxmlLoader.load(getClass().getClassLoader().getResource("de/codekings/client/GUI/Katalog/katalog_item.fxml").openStream());

                Katalog_itemController kic = (Katalog_itemController) fxmlLoader.getController();
                kic.setTitel("Crank " + i);
                kic.setCover(new Image( CoverFlowAnzeige.class.getResource("cover"+i+".png").toExternalForm(),false));
                if(i%2==0){
                    kic.setVerfuegbar(true);
                }else{
                    kic.setVerfuegbar(false);
                }
            } catch (IOException e) {
                System.out.println(e.getCause());
            }
            p.getChildren().add(pa);
            
        }
        p.autosize();
        
        return p;
    }

}
