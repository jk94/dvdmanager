/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.codekings.client.GUI.MainFrame;

import de.codekings.client.GUI.Katalog.CoverFlowAnzeige;
import de.codekings.client.Controls.Control;
import java.io.IOException;
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

    Control c;
    
    @Override
    public void start(Stage primaryStage) throws Exception {
        c = new Control();
        Control.setControl(c);
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("template.fxml"));
        } catch (IOException e) {
            System.out.println("loading failed");
        }
        Scene scene = new Scene(root);
        primaryStage.setTitle("DVD Verleih");
        primaryStage.setScene(scene);
        
        primaryStage.show();
        CoverFlowAnzeige ca = new CoverFlowAnzeige();
        //ca.start(new Stage());
    }

}
