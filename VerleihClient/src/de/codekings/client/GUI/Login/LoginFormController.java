/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.codekings.client.GUI.Login;

import de.codekings.client.Controls.Control;
import de.codekings.common.Connection.Hasher;
import java.io.InputStream;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

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
    @FXML
    private ImageView login_headerimg;

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

            //
            class LoadingScreen extends Thread {

                public boolean ende = false;

                public void stopThread() {
                    ende = true;
                }

                @Override
                public void run() {
                    Application a = new Application() {

                        @Override
                        public void start(Stage primaryStage) throws Exception {
                            Group root = new Group();
                            String pfad = "de/codekings/client/GUI/Elements/loading.gif";
                            InputStream is = LoginFormController.class.getClassLoader().getResourceAsStream(pfad);
                            Image img = new Image(is, 100.0, 100.0, true, true);

                            ImageView iv = new ImageView(img);
                            iv.setCursor(Cursor.WAIT);

                            root.getChildren().add(iv);

                            primaryStage.initStyle(StageStyle.TRANSPARENT);

                            Scene scene = new Scene(root, 100, 100, Color.TRANSPARENT);

                            primaryStage.setTitle("");
                            primaryStage.setScene(scene);
                            primaryStage.show();
                        }
                    };
                    Stage s = null;
                    try {
                        s = new Stage();
                        a.start(s);
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    while (!ende) {

                    }
                }

            }
            LoadingScreen loadingscreen = new LoadingScreen();
            loadingscreen.start();
            //
            boolean ergebnis = control.login(email, passwort);
            if (ergebnis) {
                //Logged In
            } else {
                setHinweisVisible(true);
                setHinweis("Anmeldung fehlgeschlagen.\nBitte erneut versuchen.");
            }
            loadingscreen.stopThread();
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
