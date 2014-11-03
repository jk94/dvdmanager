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
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author Simon
 */
public class AusleihenController implements Initializable {
    @FXML
    private Button ma_ausleihen_btnanzeigen;
    @FXML
    private Label ma_ausleihen_label;
    @FXML
    private TextField ma_ausleihen_accnr_input;
    @FXML
    private TableView<?> ma_ausleihen_table;
    @FXML
    private Button ma_ausleihen_btnausleihe;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
