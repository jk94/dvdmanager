/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.codekings.client.GUI.MainFrame;

import de.codekings.client.Controls.ContentManager;
import de.codekings.client.Enum.ContentPageType;
import de.codekings.client.main.Control;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TitledPane;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
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

    private static TemplateController tc;
    @FXML
    private Button btn_mab_katalogpflege;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        tc = this;
        //Control.getControl().getContentManager().changeContent(content_pane, ContentPageType.MA_KundeRegistrieren);
        //head_btn_back.setDisable(!ContentManager.getVerlaufsManager().canBack());
        //head_btn_forward.setDisable(!ContentManager.getVerlaufsManager().canNext());
        // TODO
        btn_mab_registieren.setOnMouseClicked((MouseEvent event) -> {
            Control.getControl().getContentManager().changeContent(content_base, ContentPageType.MA_KundeRegistrieren);
            head_btn_back.setDisable(!ContentManager.getVerlaufsManager().canBack());
            head_btn_forward.setDisable(!ContentManager.getVerlaufsManager().canNext());
        });
        btn_mab_katalogpflege.setOnMouseClicked((MouseEvent event) -> {
            Control.getControl().getContentManager().changeContent(content_base, ContentPageType.MA_CreateFilm);
            head_btn_back.setDisable(!ContentManager.getVerlaufsManager().canBack());
            head_btn_forward.setDisable(!ContentManager.getVerlaufsManager().canNext());
        });
        head_btn_back.setOnMouseClicked((MouseEvent event) -> {
            Control.getControl().getContentManager().changeContent(content_base, ContentPageType.BACK);
            head_btn_back.setDisable(!ContentManager.getVerlaufsManager().canBack());
            head_btn_forward.setDisable(!ContentManager.getVerlaufsManager().canNext());
        });
        head_btn_forward.setOnMouseClicked((MouseEvent event) -> {
            Control.getControl().getContentManager().changeContent(content_base, ContentPageType.FORWARD);
            head_btn_back.setDisable(!ContentManager.getVerlaufsManager().canBack());
            head_btn_forward.setDisable(!ContentManager.getVerlaufsManager().canNext());
        });
    }

    public Pane getContentPane() {
        return this.content_pane;
    }

    public static TemplateController getTemplateController() {
        return tc;
    }

}
