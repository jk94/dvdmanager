/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.codekings.client.gui.login;

import java.io.IOException;
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

    @Override
    public void start(Stage primaryStage) {

        Parent root = null;

        try {
            root = FXMLLoader.load(getClass().getClassLoader().getResource("de/codekings/client/gui/login/loginform.fxml"));
        } catch (IOException e) {

        }

        Scene scene = new Scene(root, 300, 250);

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
