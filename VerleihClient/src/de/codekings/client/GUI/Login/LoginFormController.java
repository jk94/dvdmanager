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
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

/**
 * FXML Controller class
 *
 * @author Jan
 */
public class LoginFormController implements Initializable {
    @FXML
    private ImageView login_headerimg;
    @FXML
    private Button login_exit;
    @FXML
    private Button login_login;
    @FXML
    private TextField login_email;
    @FXML
    private Label login_hinweis;
    @FXML
    private PasswordField login_passwort;
    
    private Control control;

    /**
     * Initializes the controller class.
     */
   @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
        login_headerimg.setImage(new Image(LoginFormController.class.getClassLoader().getResourceAsStream("de/codekings/client/GUI/Elements/cover.png")));

        login_exit.setOnMouseClicked((MouseEvent event) -> {
            System.exit(0);
        });

        login_login.setOnMouseClicked((MouseEvent event) -> {
            Hasher h = Hasher.getInstance();
            String email = login_email.getText();
            String passwort = h.ToMD5(login_passwort.getText());
            
            boolean ergebnis = control.login(email, passwort);
            if (ergebnis) {
                //Logged In
                setHinweis("Anmeldung erfolgreich!");
                setHinweisVisible(true);
            } else {
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

    public void setControl(Control c) {
        this.control = c;
    }
}
