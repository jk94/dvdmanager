/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package de.codekings.client.GUI.MainFrame;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author Simon
 */
public class Create_filmController implements Initializable {
    @FXML
    private AnchorPane create_anchor;
    @FXML
    private Button create_btn;
    @FXML
    private TextField creeate_titel;
    @FXML
    private TextField create_genre;
    @FXML
    private TextArea create_beschreibung;
    @FXML
    private RadioButton create_fsk6;
    @FXML
    private RadioButton create_fsk12;
    @FXML
    private RadioButton create_fsk16;
    @FXML
    private RadioButton create_fsk18;
    @FXML
    private TextField create_trailer;
    @FXML
    private TextField create_schauspieler;
    @FXML
    private TextField create_regisseur;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
