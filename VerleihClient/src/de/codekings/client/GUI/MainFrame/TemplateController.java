/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.codekings.client.GUI.MainFrame;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

/**
 * FXML Controller class
 *
 * @author User
 */
public class TemplateController implements Initializable {

    @FXML
    private Button btn_start;
    @FXML
    private Button btn_titelsuche;
    @FXML
    private Button btn_genresuche;
    @FXML
    private Button btn_neuheiten;
    @FXML
    private Button btn_uebersicht;
    @FXML
    private TextField head_title;
    @FXML
    private Button btn_ausleihen;
    @FXML
    private Button btn_entliehen;
    @FXML
    private Button btn_kontoverwaltung;
    @FXML
    private Button btn_bewertung;
    @FXML
    private ImageView head_searchicon;
    @FXML
    private Button head_btn_home;
    @FXML
    private ImageView head_btn_home_img;
    @FXML
    private Button head_btn_back;
    @FXML
    private ImageView head_btn_back_img;
    @FXML
    private Button head_btn_forward;
    @FXML
    private ImageView head_btn_forward_img;
    @FXML
    private Button btn_search;
    @FXML
    private Accordion menu_accordion;
    @FXML
    private TitledPane menu_dvdkatalog;
    @FXML
    private TitledPane menu_kundenbereich;
    @FXML
    private TitledPane menu_mitarbeiterbereich;
    @FXML
    private Button btn_uebersicht2;
    @FXML
    private Button btn_ausleihen2;
    @FXML
    private Button btn_entliehen2;
    @FXML
    private Button btn_kontoverwaltung2;
    @FXML
    private TitledPane menu_adminbereich;
    @FXML
    private Button btn_uebersicht21;
    @FXML
    private Button btn_ausleihen21;
    @FXML
    private Button btn_mab_registieren;
    @FXML
    private ScrollPane content_base;
    @FXML
    private Pane content_pane;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        btn_mab_registieren.setOnMouseClicked((javafx.scene.input.MouseEvent event) -> {
            System.out.println("Start show createUser");
            try {
                Parent p = FXMLLoader.load(getClass().getResource("create_user.fxml"));
                content_pane.getChildren().clear();
                content_pane.getChildren().add(p);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        });
    }

}
