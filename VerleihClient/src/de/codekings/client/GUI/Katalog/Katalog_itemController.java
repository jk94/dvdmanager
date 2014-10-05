/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.codekings.client.GUI.Katalog;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * FXML Controller class
 *
 * @author Jan
 */
public class Katalog_itemController implements Initializable {

    @FXML
    private ImageView katalog_cover;
    @FXML
    private Label katalog_titel;
    @FXML
    private Label katalog_subtitle;
    @FXML
    private Label katalog_desc;
    @FXML
    private Label katalog_genre;
    @FXML
    private Label katalog_jahr;
    @FXML
    private Label katalog_laufzeit;
    @FXML
    private ImageView katalog_img_verfuegbar;
    @FXML
    private Label katalog_lbl_verfuegbar;
    @FXML
    private CheckBox katalog_cb_ausleihen;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    public void setTitel(String titel) {
        this.katalog_titel.setText(titel);
    }

    public void setCover(Image img){
        katalog_cover.setImage(img);
    }
    
    public void changeDirection(){
        
    }
}
