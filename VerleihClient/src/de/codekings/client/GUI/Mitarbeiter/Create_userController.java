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
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
<<<<<<< HEAD
 * @author Jan
=======
 * @author Simon
>>>>>>> 2029bd59d8ee9cd4c7598f08be7dc35e14732263
 */
public class Create_userController implements Initializable {
    @FXML
    private PasswordField user_passwort;
    @FXML
    private PasswordField user_passwort_check;
    @FXML
    private Button user_btnspeichern;
    @FXML
    private DatePicker user_datum;
    @FXML
    private ListView<?> user_list;
    @FXML
    private Button user_btnspeichern1;
    @FXML
    private Button user_btnspeichern11;
    @FXML
    private TextField user_input_email;
    @FXML
    private TextField user_input_vorname;
    @FXML
    private TextField user_input_nachname;
    @FXML
    private TextField user_input_adresse;
    @FXML
    private TextField user_table_head;
    @FXML
    private TextField user_input_ort;
    @FXML
    private TextField user_input_plz;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        String vorname = user_input_vorname.getText();
        if (!vorname.matches("[a-zA-Z]")){ 
            //TODO setHinweisVisible(true);
            //     setHinweis("Vorname darf nur aus Buchstaben bestehen.");
        }
        String name = user_input_nachname.getText();
         if (!name.matches("[a-zA-Z]")){ 
            //TODO setHinweisVisible(true);
            //     setHinweis("Name darf nur aus Buchstaben bestehen.");
        }
        String email = user_input_email.getText();
         if (!name.matches("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
		+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$")){
             //TODO setHinweisVisible(true);
            //     setHinweis("Email nicht validiert."); 
         }
        String passwort = user_passwort.getText();
        if (!name.matches("((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{6,20})")){
             //TODO setHinweisVisible(true);
            //     setHinweis("Passwort Kriterien nicht erfüllt."); 
         }
        String passwort_check = user_passwort_check.getText();
        if (!passwort.equals(passwort_check))
        {
            //TODO setHinweisVisible(true);
            //     setHinweis("Passwörter stimmen nicht überein!");
        }
        
        //  TODO   Welcher Datentyp?? geburtstag = user_datum.getValue();
        
        String plz = user_input_plz.getText();
        if (!vorname.matches("[0-9]")){ 
            //TODO setHinweisVisible(true);
            //     setHinweis("Postleitzahl darf nur aus Zahlen bestehen.");
        }
        String ort = user_input_ort.getText();
        if (!vorname.matches("[a-zA-Z]")){ 
            //TODO setHinweisVisible(true);
            //     setHinweis("Ort darf nur aus Buchstaben bestehen.");
        }
        String str = user_input_adresse.getText();
        if (!vorname.matches("(?=.*\\d)(?=.*[a-zA-Z])")){ 
            //TODO setHinweisVisible(true);
            //     setHinweis("Adresse muss Hausnummer enthalten.");
        }
    }    
    
}
