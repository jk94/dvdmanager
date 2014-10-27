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

    private Button btn_start;
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
    private TitledPane menu_adminbereich;
    private Button btn_mab_registieren;
    @FXML
    private ScrollPane content_base;
    private Pane content_pane;

    private static TemplateController tc;
    @FXML
    private Button btn_mab_katalogpflege;
    private Button kunde_btn_start;
    private Button kunde_btn_ausleihen;
    private Button kunde_btn_entliehen;
    private Button kunde_btn_bewertung;
    private Button kunde_btn_kontoverwaltung;
    @FXML
    private BorderPane bp_mainframe;
    @FXML
    private Button head_btn_search;
    @FXML
    private ImageView head_btn_search_img;
    @FXML
    private ImageView header_logo;
    @FXML
    private Button btn_katalog_start;
    @FXML
    private Button btn_katalog_titelsuche;
    @FXML
    private Button btn_katalog_genresuche;
    @FXML
    private Button btn_katalog_neuheiten;
    @FXML
    private Button btn_kunde_start;
    @FXML
    private Button btn_kunde_ausleihen;
    @FXML
    private Button btn_kunde_entliehen;
    @FXML
    private Button btn_kunde_bewertung;
    @FXML
    private Button btn_kunde_kontoverwaltung;
    @FXML
    private Button btn_mab_return;
    @FXML
    private Button btn_mab_kontoverwaltung;
    @FXML
    private Button btn_admin_kontenverwaltung;
    @FXML
    private Button btn_admin_destruct;

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
        header_logo.setImage(new Image(TemplateController.class.getClassLoader().getResourceAsStream("de/codekings/client/GUI/Elements/cover.png")));
// TODO
        btn_katalog_start.setOnMouseClicked((MouseEvent event) -> {
            Control.getControl().getContentControl().changeContent(content_base, ContentPageType.Katalog_Start, false);
            head_btn_back.setDisable(!ContentControl.getVerlaufsManager().canBack());
            head_btn_forward.setDisable(!ContentControl.getVerlaufsManager().canNext());
        });
        btn_kunde_start.setOnMouseClicked((MouseEvent event) -> {
            Control.getControl().getContentControl().changeContent(content_base, ContentPageType.Kunde_Start, false);
            head_btn_back.setDisable(!ContentControl.getVerlaufsManager().canBack());
            head_btn_forward.setDisable(!ContentControl.getVerlaufsManager().canNext());
        });
        btn_kunde_ausleihen.setOnMouseClicked((MouseEvent event) -> {
            Control.getControl().getContentControl().changeContent(content_base, ContentPageType.Kunde_Ausleihen, false);
            head_btn_back.setDisable(!ContentControl.getVerlaufsManager().canBack());
            head_btn_forward.setDisable(!ContentControl.getVerlaufsManager().canNext());
        });
        btn_kunde_entliehen.setOnMouseClicked((MouseEvent event) -> {
            Control.getControl().getContentControl().changeContent(content_base, ContentPageType.Kunde_EntlieheneArt, false);
            head_btn_back.setDisable(!ContentControl.getVerlaufsManager().canBack());
            head_btn_forward.setDisable(!ContentControl.getVerlaufsManager().canNext());
        });
        btn_kunde_kontoverwaltung.setOnMouseClicked((MouseEvent event) -> {
            Control.getControl().getContentControl().changeContent(content_base, ContentPageType.Kunde_Verwaltung, false);
            head_btn_back.setDisable(!ContentControl.getVerlaufsManager().canBack());
            head_btn_forward.setDisable(!ContentControl.getVerlaufsManager().canNext());
        });
        btn_mab_kontoverwaltung.setOnMouseClicked((MouseEvent event) -> {
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
        
        btn_kunde_bewertung.setOnMouseClicked((MouseEvent event) -> {
            Control.getControl().getContentControl().changeContent(content_base, ContentPageType.Kunde_BewAbg, true);
            head_btn_back.setDisable(!ContentControl.getVerlaufsManager().canBack());
            head_btn_forward.setDisable(!ContentControl.getVerlaufsManager().canNext());
        });
    }

    public Pane getContentPane() {
        return this.content_pane;
    }

    public Accordion getMenu(){
        return this.menu_accordion;
    }
    
}
