/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.codekings.client.GUI.Login;

import de.codekings.client.Controls.Control;
import de.codekings.common.Connection.Hasher;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

/**
 * FXML Controller class
 *
 * @author Jan
 */
public class LoginFormController implements Initializable {

    @FXML
    private Button login_exit;
    @FXML
    private Button login_login;
    @FXML
    private TextField login_email;
    @FXML
    private TextField login_passwort;
    @FXML
    private Label login_hinweis;

    private Control control;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

        login_exit.setOnMouseClicked((MouseEvent event) -> {
            System.exit(0);
        });

        login_login.setOnMouseClicked((MouseEvent event) -> {
            Hasher h = Hasher.getInstance();
            String email = login_email.getText();
            String passwort = h.ToMD5(login_passwort.getText());
            if(control.login(email, passwort)){
                //Logged In
            }else{
                setHinweisVisible(true);
                setHinweis("Anmeldung fehlgeschlagen.\nBitte erneut versuchen.");
            }
        });

        this.setHinweis("Melden Sie sich bitte mit Ihren Anmeldedaten an!");
        this.setHinweisVisible(true);

    }

    public void setHinweisVisible(boolean value) {
        login_hinweis.setVisible(value);
    }

    public void setHinweis(String text) {
        login_hinweis.setText(text);
    }

    public void setConrol(Control c) {
        this.control = c;
    }

}
