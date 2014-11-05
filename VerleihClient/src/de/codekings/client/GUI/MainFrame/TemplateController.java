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
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

/**
 * FXML Controller class
 *
 * @author User
 */
public class TemplateController implements Initializable {

    //private static TemplateController tc;
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
    @FXML
    private ScrollPane content_base;
    @FXML
    private Button btn_mab_katalogpflege;
    @FXML
    private BorderPane bp_mainframe;
    @FXML
    private Button head_btn_search;
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
    private Button btn_kunde_reservierungen;
    @FXML
    private Button btn_kunde_entliehen;
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
    @FXML
    private AnchorPane head_left;
    @FXML
    private AnchorPane head_center;
    @FXML
    private AnchorPane head_right;
    @FXML
    private TextField head_search_field;
    @FXML
    private ImageView head_btn_search_img;
    @FXML
    private Button btn_mab_ausleihe;
    @FXML
    private Button btn_mab_dvdmanager;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //tc = this;
        head_btn_back_img.setImage(new Image(TemplateController.class.getClassLoader().getResourceAsStream("de/codekings/client/GUI/Elements/arrowleft.png")));
        head_btn_forward_img.setImage(new Image(TemplateController.class.getClassLoader().getResourceAsStream("de/codekings/client/GUI/Elements/arrowright.png")));
        head_btn_home_img.setImage(new Image(TemplateController.class.getClassLoader().getResourceAsStream("de/codekings/client/GUI/Elements/homebutton.png")));
        head_btn_search_img.setImage(new Image(TemplateController.class.getClassLoader().getResourceAsStream("de/codekings/client/GUI/Elements/searchicon.png")));
        header_logo.setImage(new Image(TemplateController.class.getClassLoader().getResourceAsStream("de/codekings/client/GUI/Elements/cover.png")));
// TODO

        head_btn_search.setOnMouseClicked((MouseEvent event) -> {
            Control.getControl().getContentControl().changeContent(head_search_field.getText());
            head_btn_back.setDisable(!ContentControl.getVerlaufsManager().canBack());
            head_btn_forward.setDisable(!ContentControl.getVerlaufsManager().canNext());
        });
        head_search_field.setOnKeyPressed((KeyEvent event) -> {
            if (event.getCode().equals(KeyCode.ENTER)) {
                Control.getControl().getContentControl().changeContent(head_search_field.getText());
                head_btn_back.setDisable(!ContentControl.getVerlaufsManager().canBack());
                head_btn_forward.setDisable(!ContentControl.getVerlaufsManager().canNext());
            }
        });
        btn_katalog_start.setOnMouseClicked((MouseEvent event) -> {
            Control.getControl().getContentControl().changeContent(ContentPageType.Katalog_Start, false);
            head_btn_back.setDisable(!ContentControl.getVerlaufsManager().canBack());
            head_btn_forward.setDisable(!ContentControl.getVerlaufsManager().canNext());
        });
        btn_kunde_start.setOnMouseClicked((MouseEvent event) -> {
            Control.getControl().getContentControl().changeContent(ContentPageType.Kunde_Start, false);
            head_btn_back.setDisable(!ContentControl.getVerlaufsManager().canBack());
            head_btn_forward.setDisable(!ContentControl.getVerlaufsManager().canNext());
        });
        btn_kunde_reservierungen.setOnMouseClicked((MouseEvent event) -> {
            Control.getControl().getContentControl().changeContent(ContentPageType.Kunde_Reservierungen, false);
            head_btn_back.setDisable(!ContentControl.getVerlaufsManager().canBack());
            head_btn_forward.setDisable(!ContentControl.getVerlaufsManager().canNext());
        });
        btn_kunde_entliehen.setOnMouseClicked((MouseEvent event) -> {
            Control.getControl().getContentControl().changeContent(ContentPageType.Kunde_EntlieheneArt, false);
            head_btn_back.setDisable(!ContentControl.getVerlaufsManager().canBack());
            head_btn_forward.setDisable(!ContentControl.getVerlaufsManager().canNext());
        });
        btn_kunde_kontoverwaltung.setOnMouseClicked((MouseEvent event) -> {
            Control.getControl().getContentControl().changeContent(ContentPageType.Kunde_Verwaltung, false);
            head_btn_back.setDisable(!ContentControl.getVerlaufsManager().canBack());
            head_btn_forward.setDisable(!ContentControl.getVerlaufsManager().canNext());
        });
        btn_mab_kontoverwaltung.setOnMouseClicked((MouseEvent event) -> {
            Control.getControl().getContentControl().changeContent(ContentPageType.MA_KundeRegistrieren, false);
            head_btn_back.setDisable(!ContentControl.getVerlaufsManager().canBack());
            head_btn_forward.setDisable(!ContentControl.getVerlaufsManager().canNext());
        });
        btn_mab_katalogpflege.setOnMouseClicked((MouseEvent event) -> {
            Control.getControl().getContentControl().changeContent(ContentPageType.MA_CreateFilm, false);
            head_btn_back.setDisable(!ContentControl.getVerlaufsManager().canBack());
            head_btn_forward.setDisable(!ContentControl.getVerlaufsManager().canNext());
        });
        head_btn_back.setOnMouseClicked((MouseEvent event) -> {
            Control.getControl().getContentControl().changeContent(ContentPageType.BACK, true);
            head_btn_back.setDisable(!ContentControl.getVerlaufsManager().canBack());
            head_btn_forward.setDisable(!ContentControl.getVerlaufsManager().canNext());
        });
        head_btn_forward.setOnMouseClicked((MouseEvent event) -> {
            Control.getControl().getContentControl().changeContent(ContentPageType.FORWARD, true);
            head_btn_back.setDisable(!ContentControl.getVerlaufsManager().canBack());
            head_btn_forward.setDisable(!ContentControl.getVerlaufsManager().canNext());
        });
        btn_mab_dvdmanager.setOnMouseClicked((MouseEvent event) -> {
            Control.getControl().getContentControl().changeContent(ContentPageType.MA_MANAGEDVD, false);
            head_btn_back.setDisable(!ContentControl.getVerlaufsManager().canBack());
            head_btn_forward.setDisable(!ContentControl.getVerlaufsManager().canNext());
        });
        btn_mab_ausleihe.setOnMouseClicked((MouseEvent event) -> {
            Control.getControl().getContentControl().changeContent(ContentPageType.MA_Ausleihe, false);
            head_btn_back.setDisable(!ContentControl.getVerlaufsManager().canBack());
            head_btn_forward.setDisable(!ContentControl.getVerlaufsManager().canNext());
        });
        head_btn_home.setOnMouseClicked((MouseEvent event) -> {
            Control.getControl().getContentControl().changeContent(ContentPageType.Katalog_Start, false);
            head_btn_back.setDisable(!ContentControl.getVerlaufsManager().canBack());
            head_btn_forward.setDisable(!ContentControl.getVerlaufsManager().canNext());
        });
        btn_mab_return.setOnMouseClicked((MouseEvent event) -> {
            Control.getControl().getContentControl().changeContent(ContentPageType.MA_Return, false);
            head_btn_back.setDisable(!ContentControl.getVerlaufsManager().canBack());
            head_btn_forward.setDisable(!ContentControl.getVerlaufsManager().canNext());
        });
    }

    public Accordion getMenu() {
        return this.menu_accordion;
    }

    public ScrollPane getContent_base() {
        return content_base;
    }

    public void initAfterInit() {
        head_btn_back.setDisable(!ContentControl.getVerlaufsManager().canBack());
        head_btn_forward.setDisable(!ContentControl.getVerlaufsManager().canNext());
    }

}
