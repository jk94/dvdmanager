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
 * @author Heiko
 */
public class ZurueckgebenController implements Initializable {
    @FXML
    private Button ma_return_btnruecknahme;
    @FXML
    private TableView<?> ma_return_table;
    @FXML
    private TextField ma_return_accnr_input;
    @FXML
    private Label ma_return_label;
    @FXML
    private Button ma_return_btnanzeigen;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
    }    
    
}
