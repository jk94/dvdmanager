/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI.MainFrame;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

/**
 * FXML Controller class
 *
 * @author Jan
 */
public class TemplateController implements Initializable {

    @FXML
    private TextField head_title;
    @FXML
    private ImageView head_searchicon;
    @FXML
    private TextField head_searchbox;
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
    private Button btn_ausleihen;
    @FXML
    private Button btn_entliehen;
    @FXML
    private Button btn_kontoverwaltung;
    @FXML
    private Button btn_bewertung;
    @FXML
    private Button head_btn_home;
    @FXML
    private ImageView head_btn_home_img;
    @FXML
    private ImageView head_btn_back_img;
    @FXML
    private ImageView head_btn_forward_img;
    @FXML
    private Button btn_search;
    @FXML
    private Button head_btn_back;
    @FXML
    private Button head_btn_forward;
    @FXML
    private Accordion menu_accordion;
    @FXML
    private ScrollPane scrollpane;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        btn_start.setOnAction((ActionEvent event) -> {
            System.out.println("Ausgabe Start:");
        });
        head_btn_home.setOnAction((ActionEvent event) -> {
            System.out.println("Ausgabe head home");
        });
        head_btn_home.setOnMouseEntered((MouseEvent event) -> {
            head_btn_back.setDisable(true);
        });
        head_btn_home.setOnMouseExited((MouseEvent event) -> {
            head_btn_back.setDisable(false);
        });

    }
}
