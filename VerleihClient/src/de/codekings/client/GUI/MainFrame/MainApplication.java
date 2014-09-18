/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.codekings.client.GUI.MainFrame;

import java.io.IOException;
import java.util.Arrays;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author Jan
 */
public class MainApplication extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root=null;
        try {
            root = FXMLLoader.load(getClass().getResource("template.fxml"));
        } catch (IOException e) {
            System.out.println(e.getMessage());
            System.out.println(Arrays.toString(e.getSuppressed()));
        }

        Scene scene = new Scene(root, 300, 300);
        primaryStage.setTitle("DVD Verleih");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /*public void main(String[] args){
     try {
     start(new Stage());
     } catch (Exception ex) {
     Logger.getLogger(MainApplication.class.getName()).log(Level.SEVERE, null, ex);
     }
     }*/
}
