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
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

/**
 * FXML Controller class
 *
 * @author Jan
 */
public class Filmbewerten_itemController implements Initializable {

    private ImageView katalog_cover;
    private Label katalog_titel;
    private Label katalog_subtitle;
    private Label katalog_desc;
    private Label katalog_genre;
    private Label katalog_jahr;
    private Label katalog_laufzeit;
    private Label katalog_lbl_verfuegbar;
    @FXML
    private ImageView bewerten_cover;
    @FXML
    private Label empfehlen_titel;
    @FXML
    private Label empehlen_subtitle;
    @FXML
    private Label empfehlen_frage;
    @FXML
    private Button bewerten_positiv_button;

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

    public void setCover(Image img) {
        katalog_cover.setImage(img);
    }

    public void setSubtitle(String subtitle) {
        this.katalog_subtitle.setText(subtitle);
    }

    public void setDescription(String desc) {
        this.katalog_desc.setText(desc);
    }

    public void setGenre(String genre) {
        this.katalog_genre.setText(genre);
    }

    public void setJahr(String jahr) {
        this.katalog_jahr.setText(jahr);
    }

    public void setLaufzeit(String laufzeit) {
        this.katalog_laufzeit.setText(laufzeit);
    }

    public void setVerfuegbar(boolean v) {
        if (v) {
            this.katalog_lbl_verfuegbar.setTextFill(Color.web("#FFFF00"));
            this.katalog_lbl_verfuegbar.setText("verfügbar");
            Image img = new Image(getClass().getClassLoader().getResource("de/codekings/client/GUI/Elements/verfuegbar.png").toExternalForm(), false);
        } else {
            this.katalog_lbl_verfuegbar.setTextFill(Color.web("#FF0000"));
            this.katalog_lbl_verfuegbar.setText("nicht verfügbar");
            Image img = new Image(getClass().getClassLoader().getResource("de/codekings/client/GUI/Elements/nverfuegbar.png").toExternalForm(), false);

        }
    }
}
