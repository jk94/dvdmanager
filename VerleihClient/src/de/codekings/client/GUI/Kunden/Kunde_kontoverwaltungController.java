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
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author Simon
 */
public class Kunde_kontoverwaltungController implements Initializable {
    @FXML
    private Label kunde_konto_name;
    @FXML
    private TextField kunde_konto_email_neu;
    @FXML
    private TextField kunde_konto_name_neu;
    @FXML
    private Label kunde_konto_email;
    @FXML
    private TextField kunde_konto_pass_neu;
    @FXML
    private TextField kunde_konto_pass_neu_contr;
    @FXML
    private Button kunde_konto_verwerfen;
    @FXML
    private Button kunde_konto_aendern;
    @FXML
    private TextField kunde_konto_pass_akt;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
