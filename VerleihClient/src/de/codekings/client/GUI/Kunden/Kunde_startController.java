/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package de.codekings.client.GUI.Kunden;

import de.codekings.client.Controls.ContentControl;
import de.codekings.client.Controls.Control;
import de.codekings.client.Enum.ContentPageType;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

/**
 * FXML Controller class
 *
 * @author Simon
 */
public class Kunde_startController implements Initializable {
    @FXML
    private Label kunde_start_warenkorb;
    @FXML
    private Label kunde_start_titel;
    @FXML
    private Pane kunde_start_warenkorbpane;
    @FXML
    private Button kunde_start_btnausleihe;
    @FXML
    private Pane kunde_start_entlpane;
    @FXML
    private Button kunde_start_btnentl;
    @FXML
    private Label kunde_start_entl;
    @FXML
    private Label kunde_start_guthaben;
    @FXML
    private Pane kunde_start_kontopane;
    @FXML
    private Button kunde_start_kontoverwaltung;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
        
        kunde_start_btnausleihe.setOnMouseClicked((MouseEvent event) -> {
            // Ändere Szene zu kunde_ausleihen
        });
        
        kunde_start_btnentl.setOnMouseClicked((MouseEvent event) -> {
            // Ändere Szene zu kunde_entleihen
        });
        
        kunde_start_kontoverwaltung.setOnMouseClicked((MouseEvent event) -> {
            // Ändere Szene zu kunde_kontoverwaltung
        });
        
        /*
        
        String vorname = getVorname();
        kunde_start_titel.setText("Hallo " + vorname + "!");
        
        int guthaben = getGuthaben();
        kunde_start_guthaben.setText("Dein Guthaben beträgt " + guthaben + "€.");
        
        int anzEntliehene = getEntlieheneMedien();
        kunde_start_entl.setText("Zurzeit hast du " + anzEntliehene + " entliehene Medien.");
        
        int anzWarenkorb = getAnzahlWarenkorb();
        kunde_start_warenkorb.setText("Du hast " + anzWarenkorb + " Medien im Warenkorb.);
        */
        
        
        
    }    
    
}
