/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package de.codekings.client.GUI.Kunden;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

/**
 * FXML Controller class
 *
 * @author Simon
 */
public class Kunde_startController implements Initializable {
    @FXML
    private Label kunde_start_titel;
    @FXML
    private Pane kunde_start_warenkorbpane;
    @FXML
    private Button kunde_start_btnausleihe;
    @FXML
    private Label kunde_start_warenkorb;
    @FXML
    private Pane kunde_start_entlpane;
    @FXML
    private Button kunde_start_btnentl;
    @FXML
    private Label kunde_start_entl;
    @FXML
    private Label kunde_start_guthaben;
    @FXML
    private Pane kunde_start_coverflow;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
