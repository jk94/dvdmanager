/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package dvdverleih;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.SubScene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author Jan
 */
public class TemplateController implements Initializable {
    @FXML
    private VBox master_base;
    @FXML
    private AnchorPane header_base;
    @FXML
    private ImageView head_backbtn;
    @FXML
    private ImageView head_forwardbtn;
    @FXML
    private TextField head_title;
    @FXML
    private Button search_icon;
    @FXML
    private ImageView head_searchicon;
    @FXML
    private TextField head_searchbox;
    @FXML
    private AnchorPane body_base;
    @FXML
    private AnchorPane menu_base;
    @FXML
    private Label label_katalog;
    @FXML
    private Button btn_start;
    @FXML
    private Button btn_titelsuche;
    @FXML
    private Button btn_genresuche;
    @FXML
    private Button btn_neuheiten;
    @FXML
    private Label label_kundenbereich;
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
    private AnchorPane content_base;
    @FXML
    private SubScene subscene_content;
    @FXML
    private ImageView head_homebtn;
    @FXML
    private Button head_btn_home;

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
        
    }    
    
}
