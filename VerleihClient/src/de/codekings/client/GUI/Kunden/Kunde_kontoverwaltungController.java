/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package de.codekings.client.GUI.Kunden;

import de.codekings.client.Controls.Control;
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
    private Label kunde_konto_email;
    @FXML
    private Label kunde_konto_vorname;
    @FXML
    private Label kunde_konto_adresse;
    @FXML
    private Label kunde_konto_hausnr;
    @FXML
    private Label kunde_konto_plz;
    @FXML
    private Label kunde_konto_ort;
    @FXML
    private Label kunde_konto_guthaben;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        /*
        
        ...get UserInfo
        String email= Control.getControl().getSession().getEmail();
        String vorname = Control.getControl().getSession().getVorname();
        String name
        String adresse
        String hausnr
        String plz
        String ort
        String guthaben
        
        set UserInfo
        
        
        
        kunde_konto_vorname.setText(vorname);
        kunde_konto_name.setText(name);
        kunde_konto_email.setText(email);
        kunde_konto_adresse.setText(adresse);
        kunde_konto_hausnr.setText(hausnr);
        kunde_konto_pls.setText(plz);
        kunde_konto_ort.setText(ort);
        kunde_konto_guthaen.setText(guthaben);
        
        */        
                
     }    
    
}
