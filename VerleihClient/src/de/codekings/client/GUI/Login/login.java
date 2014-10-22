/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.codekings.client.GUI.Login;

import de.codekings.client.Controls.Control;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author Jan
 */
public class login extends Application {

    private Control c;

    @Override
    public void start(Stage primaryStage) {
        c = new Control();
        Control.setControl(c);
        Parent root = null;
        LoginFormController lfc = null;
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            root = fxmlLoader.load(getClass().getClassLoader().getResource("de/codekings/client/GUI/Login/loginform.fxml").openStream());

            lfc = (LoginFormController) fxmlLoader.getController();
            lfc.setControl(c);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.exit(0);
        }

        Scene scene = new Scene(root, 450, 270);

        primaryStage.initStyle(StageStyle.TRANSPARENT);
        primaryStage.setTitle("");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
