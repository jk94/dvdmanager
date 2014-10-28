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
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import se.mbaeumer.fxmessagebox.MessageBox;
import se.mbaeumer.fxmessagebox.MessageBoxResult;
import se.mbaeumer.fxmessagebox.MessageBoxType;

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
    private Stage ownstage;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

        login_headerimg.setImage(new Image(LoginFormController.class.getClassLoader().getResourceAsStream("de/codekings/client/GUI/Elements/cover.png")));

        login_exit.setOnMouseClicked((MouseEvent event) -> {
            MessageBox mb = new MessageBox("Möchten Sie wirklich beenden?", MessageBoxType.YES_NO);
            mb.setAlwaysOnTop(true);
            mb.setTitle("Beenden");
            mb.setResizable(false);
            mb.showAndWait();
            MessageBoxResult result = mb.getMessageBoxResult();
            if (result == MessageBoxResult.YES) {
                System.exit(0);
            }
        });

        login_email.setOnKeyPressed((KeyEvent event) -> {
            if (event.getCode().equals(KeyCode.ENTER)) {
                go4Login();
            }
        });

        login_passwort.setOnKeyPressed((KeyEvent event) -> {
            if (event.getCode().equals(KeyCode.ENTER)) {
                go4Login();
            }
        });

        login_login.setOnKeyPressed((KeyEvent event) -> {
            if (event.getCode().equals(KeyCode.ENTER)) {
                go4Login();
            }
        });

        login_login.setOnMouseClicked((MouseEvent event) -> {
            go4Login();
        });

        this.setHinweis("Melden Sie sich bitte mit Ihren Anmeldedaten an!");
        this.setHinweisVisible(true);

    }

    public void go4Login() {
        setHinweis("Bitte warten...");
        Hasher h = Hasher.getInstance();
        String email = login_email.getText();
        String passwort = h.ToMD5(login_passwort.getText());

        boolean ergebnis = control.login(email, passwort);
        if (ergebnis) {
            //Logged In
            setHinweis("Anmeldung erfolgreich!");
            setHinweisVisible(true);
            try {
                Thread.sleep(1500l);
            } catch (InterruptedException ex) {
                System.out.println(ex.getMessage());
            }
            control.openMainFrame();
            ownstage.hide();
        } else {
            setHinweisVisible(true);
            setHinweis("Anmeldung fehlgeschlagen.\nBitte erneut versuchen.");
        }
    }

    public void reset() {
        this.setHinweis("Melden Sie sich bitte mit Ihren Anmeldedaten an!");
        this.setHinweisVisible(true);

        login_email.setText("");
        login_passwort.setText("");
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

    public void setVisible(boolean value) {
        if (value) {

        } else {

        }
    }

    public void setStage(Stage ps) {
        this.ownstage = ps;
    }

    public Stage getStage() {
        return ownstage;
    }
}
