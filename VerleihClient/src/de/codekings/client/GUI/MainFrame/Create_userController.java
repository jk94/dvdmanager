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
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author Simon
 */
public class Create_userController implements Initializable {
    @FXML
    private TextField user_name;
    @FXML
    private PasswordField user_passwort;
    @FXML
    private PasswordField user_passwort_check;
    @FXML
    private TextField user_email;
    @FXML
    private Button user_btnspeichern;
    @FXML
    private DatePicker user_datum;
    @FXML
    private ListView<?> user_list;
    @FXML
    private RadioButton user_btnkunde;
    @FXML
    private RadioButton user_btnma;
    @FXML
    private RadioButton user_btnadmin;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
