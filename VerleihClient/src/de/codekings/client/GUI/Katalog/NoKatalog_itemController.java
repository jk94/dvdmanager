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
import javafx.scene.control.Label;

/**
 * FXML Controller class
 *
 * @author Jan
 */
public class NoKatalog_itemController implements Initializable {

    @FXML
    private Label lbl_Anzeige;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        

        // TODO
    }

    public void setSuchbefehl(String suche) {
        this.lbl_Anzeige.setText("Leider keine Treffer zur Suche: '"+  suche + "' :(");
    }
}
