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
import javafx.scene.input.MouseEvent;
import se.mbaeumer.fxmessagebox.MessageBox;
import se.mbaeumer.fxmessagebox.MessageBoxType;

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
        
        user_btn_create.setOnMouseClicked((MouseEvent event) -> {
            check_Input();
            // schreibe eingaben in db
        });
        
        user_btnspeichern.setOnMouseClicked((MouseEvent event) -> {
            check_Input();
            // ändere db nach eingabe
        });
        user_btn_verwerfen.setOnMouseClicked((MouseEvent event) -> {
            //lade Seite neu, ohne eingaben zu behalten
        });
        
    }
    
    public void check_Input () {
        String vorname = user_input_vorname.getText();
        if (!vorname.matches("[a-zA-Z]")){ 
            MessageBox error = new MessageBox("Vorname darf nur aus Buchstaben bestehen.",MessageBoxType.OK_ONLY);
            error.showAndWait();
        }
        String name = user_input_nachname.getText();
         if (!name.matches("[a-zA-Z]")){ 
            MessageBox error = new MessageBox("Name darf nur aus Buchstaben bestehen.",MessageBoxType.OK_ONLY);
            error.showAndWait();
        }
        String email = user_input_email.getText();
         if (!email.matches("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
		+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$")){
            MessageBox error = new MessageBox("Email entspricht nicht vorgegebenem Format.",MessageBoxType.OK_ONLY);
            error.showAndWait();
         }
        String passwort = user_passwort.getText();
        if (!passwort.matches("((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{6,20})")){
            MessageBox error = new MessageBox("Passwortkriterien nicht erfüllt.",MessageBoxType.OK_ONLY);
            error.showAndWait();
         }
        String passwort_check = user_passwort_check.getText();
        if (!passwort_check.equals(passwort_check))
        {
            MessageBox error = new MessageBox("Passwörter stimmen nicht überein!",MessageBoxType.OK_ONLY);
            error.showAndWait();
        }
        
        //  TODO   Welcher Datentyp?? geburtstag = user_datum.getValue();
        
        String plz = user_input_plz.getText();
        if (!vorname.matches("[0-9]")){ 
            MessageBox error = new MessageBox("Postleitzahl darf nur aus Zahlen bestehen.",MessageBoxType.OK_ONLY);
            error.showAndWait();
        }
        String ort = user_input_ort.getText();
        if (!vorname.matches("[a-zA-Z]")){ 
            MessageBox error = new MessageBox("Ort darf nur aus Buchstaben bestehen.",MessageBoxType.OK_ONLY);
            error.showAndWait();
        }
        String str = user_input_adresse.getText();
        if (!vorname.matches("(?=.*\\d)(?=.*[a-zA-Z])")){ 
            MessageBox error = new MessageBox("Adresse muss Hausnummer enthalten.",MessageBoxType.OK_ONLY);
            error.showAndWait();
        }
        
    }    
    
    
}
