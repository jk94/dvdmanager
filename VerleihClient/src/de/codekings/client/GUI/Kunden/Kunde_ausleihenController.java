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
import javafx.scene.control.TableView;

/**
 * FXML Controller class
 *
 * @author Simon
 */
public class Kunde_ausleihenController implements Initializable {
    @FXML
    private TableView<?> kunde_ausleihen_tabelle;
    @FXML
    private Label kunde_ausleihen_titel;
    @FXML
    private Button kunde_ausleihen_btnloeschen;
    @FXML
    private Button kunde_ausleihen_btnausleihen;
    @FXML
    private Label kunde_ausleihen_kosten;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
