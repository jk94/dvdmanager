/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package de.codekings.client.GUI.Admin;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author Simon
 */
public class Admin_maverwaltungController implements Initializable {
    @FXML
    private ListView<?> user_list;
    @FXML
    private Button user_btnspeichern;
    @FXML
    private PasswordField user_passwort_check;
    @FXML
    private PasswordField user_passwort;
    @FXML
    private DatePicker user_datum;
    @FXML
    private Label user_labelpers;
    @FXML
    private Label user_labeladr;
    @FXML
    private Label user_label_pw2;
    @FXML
    private Label user_labelpw;
    @FXML
    private TextField user_input_plz;
    @FXML
    private TextField user_input_ort;
    @FXML
    private TextField user_table_head;
    @FXML
    private Button user_btn_verwerfen;
    @FXML
    private CheckBox user_check_admin;
    @FXML
    private CheckBox user_check_ma;
    @FXML
    private CheckBox user_check_kunde;
    @FXML
    private Button user_btn_create;
    @FXML
    private TextField user_input_adresse;
    @FXML
    private TextField user_input_nachname;
    @FXML
    private TextField user_input_vorname;
    @FXML
    private TextField user_input_email;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
