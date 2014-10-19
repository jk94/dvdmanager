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
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
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
    private TextField txf_titel;
    @FXML
    private TextArea txf_beschreibung;
    @FXML
    private RadioButton rbtn_fsk0;
    @FXML
    private ToggleGroup fskbuttons;
    @FXML
    private RadioButton rbtn_fsk6;
    @FXML
    private RadioButton rbtn_fsk12;
    @FXML
    private RadioButton rbtn_fsk16;
    @FXML
    private RadioButton rbtn_fsk18;
    @FXML
    private TextField txf_trailer;
    @FXML
    private TextField txf_schauspieler;
    @FXML
    private TextField txf_regisseur;
    @FXML
    private TextField txf_subtitel;
    @FXML
    private TableColumn<?, ?> tblc_filme;
    @FXML
    private Button create_btn1;
    @FXML
    private ComboBox<?> cb_genre;
    @FXML
    private ListView<?> li_genre_added;
    @FXML
    private Button btn_genre_add;
    @FXML
    private Button btn_genre_remove;
    @FXML
    private Button create_btn2;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
