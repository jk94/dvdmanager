/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.codekings.client.GUI.Mitarbeiter;

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
 * @author Heiko
 */
public class ZurueckgebenController implements Initializable {
    @FXML
    private TableView<?> mitarbeiter_zurueckgeben_tabelle;
    @FXML
    private Label mitarbeiter_zurueckgeben_titel;
    @FXML
    private Button mitarbeiter_zurueckgeben_btnloeschen;
    @FXML
    private Button mitarbeiter_zurueckgeben_btnausleihen;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
    }    
    
}
