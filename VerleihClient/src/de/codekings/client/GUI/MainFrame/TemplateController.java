/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.codekings.client.GUI.MainFrame;

import de.codekings.client.Controls.ContentControl;
import de.codekings.client.Enum.ContentPageType;
import de.codekings.client.Controls.Control;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TitledPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
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
    private Accordion menu_accordion;
    @FXML
    private TitledPane menu_dvdkatalog;
    @FXML
    private TitledPane menu_kundenbereich;
    @FXML
    private TitledPane menu_mitarbeiterbereich;
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
    private Pane content_pane;

    private static TemplateController tc;
    @FXML
    private Button btn_mab_katalogpflege;
    @FXML
    private Button kunde_btn_start;
    @FXML
    private Button kunde_btn_ausleihen;
    @FXML
    private Button kunde_btn_entliehen;
    @FXML
    private Button kunde_btn_bewertung;
    @FXML
    private Button kunde_btn_kontoverwaltung;
    @FXML
    private BorderPane bp_mainframe;
    @FXML
    private Button head_btn_search;
    @FXML
    private ImageView head_btn_search_img;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        tc = this;
        head_btn_back_img.setImage(new Image(TemplateController.class.getClassLoader().getResourceAsStream("de/codekings/client/GUI/Elements/arrowleft.png")));
        head_btn_forward_img.setImage(new Image(TemplateController.class.getClassLoader().getResourceAsStream("de/codekings/client/GUI/Elements/arrowright.png")));
        head_btn_home_img.setImage(new Image(TemplateController.class.getClassLoader().getResourceAsStream("de/codekings/client/GUI/Elements/homebutton.png")));
        head_btn_search_img.setImage(new Image(TemplateController.class.getClassLoader().getResourceAsStream("de/codekings/client/GUI/Elements/searchicon.png")));
        
// TODO
        btn_start.setOnMouseClicked((MouseEvent event) -> {
            Control.getControl().getContentControl().changeContent(content_base, ContentPageType.Katalog_Start, false);
            head_btn_back.setDisable(!ContentControl.getVerlaufsManager().canBack());
            head_btn_forward.setDisable(!ContentControl.getVerlaufsManager().canNext());
        });
        kunde_btn_start.setOnMouseClicked((MouseEvent event) -> {
            Control.getControl().getContentControl().changeContent(content_base, ContentPageType.Kunde_Start, false);
            head_btn_back.setDisable(!ContentControl.getVerlaufsManager().canBack());
            head_btn_forward.setDisable(!ContentControl.getVerlaufsManager().canNext());
        });
        kunde_btn_ausleihen.setOnMouseClicked((MouseEvent event) -> {
            Control.getControl().getContentControl().changeContent(content_base, ContentPageType.Kunde_Ausleihen, false);
            head_btn_back.setDisable(!ContentControl.getVerlaufsManager().canBack());
            head_btn_forward.setDisable(!ContentControl.getVerlaufsManager().canNext());
        });
        kunde_btn_entliehen.setOnMouseClicked((MouseEvent event) -> {
            Control.getControl().getContentControl().changeContent(content_base, ContentPageType.Kunde_EntlieheneArt, false);
            head_btn_back.setDisable(!ContentControl.getVerlaufsManager().canBack());
            head_btn_forward.setDisable(!ContentControl.getVerlaufsManager().canNext());
        });
        kunde_btn_kontoverwaltung.setOnMouseClicked((MouseEvent event) -> {
            Control.getControl().getContentControl().changeContent(content_base, ContentPageType.Kunde_Verwaltung, false);
            head_btn_back.setDisable(!ContentControl.getVerlaufsManager().canBack());
            head_btn_forward.setDisable(!ContentControl.getVerlaufsManager().canNext());
        });
        btn_mab_registieren.setOnMouseClicked((MouseEvent event) -> {
            Control.getControl().getContentControl().changeContent(content_base, ContentPageType.MA_KundeRegistrieren, false);
            head_btn_back.setDisable(!ContentControl.getVerlaufsManager().canBack());
            head_btn_forward.setDisable(!ContentControl.getVerlaufsManager().canNext());
        });
        btn_mab_katalogpflege.setOnMouseClicked((MouseEvent event) -> {
            Control.getControl().getContentControl().changeContent(content_base, ContentPageType.MA_CreateFilm, false);
            head_btn_back.setDisable(!ContentControl.getVerlaufsManager().canBack());
            head_btn_forward.setDisable(!ContentControl.getVerlaufsManager().canNext());
        });
        head_btn_back.setOnMouseClicked((MouseEvent event) -> {
            Control.getControl().getContentControl().changeContent(content_base, ContentPageType.BACK, true);
            head_btn_back.setDisable(!ContentControl.getVerlaufsManager().canBack());
            head_btn_forward.setDisable(!ContentControl.getVerlaufsManager().canNext());
        });
        head_btn_forward.setOnMouseClicked((MouseEvent event) -> {
            Control.getControl().getContentControl().changeContent(content_base, ContentPageType.FORWARD, true);
            head_btn_back.setDisable(!ContentControl.getVerlaufsManager().canBack());
            head_btn_forward.setDisable(!ContentControl.getVerlaufsManager().canNext());
        });
        
        kunde_btn_bewertung.setOnMouseClicked((MouseEvent event) -> {
            Control.getControl().getContentControl().changeContent(content_base, ContentPageType.Kunde_BewAbg, true);
            head_btn_back.setDisable(!ContentControl.getVerlaufsManager().canBack());
            head_btn_forward.setDisable(!ContentControl.getVerlaufsManager().canNext());
        });
    }

    public Pane getContentPane() {
        return this.content_pane;
    }


}
