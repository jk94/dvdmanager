/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.codekings.client.GUI.MainFrame;

import de.codekings.client.Controls.Control;
import java.io.IOException;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TitledPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 *
 * @author Jan
 */
public class MainApplication extends Application {

    private TemplateController templateController = null;
    private Control c;

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = null;
        try {

            FXMLLoader fxmlLoader = new FXMLLoader();
            //root = fxmlLoader.load(getClass().getClassLoader().getResource("de/codekings/client/GUI/MainFrame/template.fxml").openStream());
            root = fxmlLoader.load(MainApplication.class.getClassLoader().getResource("de/codekings/client/GUI/MainFrame/template.fxml").openStream());
            templateController = (TemplateController) fxmlLoader.getController();

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("loading failed");
        }
        Scene scene = new Scene(root);
        primaryStage.setTitle("DVD Verleih");
        primaryStage.setScene(scene);

        primaryStage.setOnCloseRequest((WindowEvent event) -> {
            Control.getControl().MainFrameClosed();
        });

        primaryStage.show();

    }

    public static void main(String[] args) {
        MainApplication.launch(args);
    }

    public TemplateController getTemplateController() {
        return templateController;
    }

    public void setKundenView() {
        ObservableList<TitledPane> tp = templateController.getMenu().getPanes();
        tp.remove(2);
        tp.remove(2);
    }

    public void setMitarbeiterView() {
        ObservableList<TitledPane> tp = templateController.getMenu().getPanes();
        tp.remove(3);
    }

    public void setAdminView() {

    }

}
