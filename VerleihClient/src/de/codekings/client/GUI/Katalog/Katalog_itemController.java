/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.codekings.client.GUI.Katalog;

import de.codekings.client.Controls.Control;
import de.codekings.client.connection.ClientThread;
import de.codekings.client.connection.MessageReturn;
import de.codekings.common.Connection.Message;
import de.codekings.common.config.ConfigManager;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

/**
 * FXML Controller class
 *
 * @author Jan
 */
public class Katalog_itemController implements Initializable, MessageReturn {

    @FXML
    private ImageView katalog_cover;
    @FXML
    private Label katalog_titel;
    @FXML
    private Label katalog_subtitle;
    @FXML
    private Label katalog_desc;
    @FXML
    private Label katalog_genre;
    @FXML
    private Label katalog_jahr;
    @FXML
    private Label katalog_laufzeit;
    @FXML
    private Label katalog_lbl_verfuegbar;
    @FXML
    private ImageView katalog_img_verfuegbar;
    @FXML
    private Button katalog_btn_auswaehlen;

    private int film_id = -1;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ConfigManager cfgManager = Control.getControl().getCfgManager();
        String host = cfgManager.getConfigs().getProperty("ip");
        int port = Integer.parseInt(cfgManager.getConfigs().getProperty("port"));

        katalog_btn_auswaehlen.setDisable(true);
        katalog_btn_auswaehlen.setOnMouseClicked((MouseEvent event) -> {
            katalog_btn_auswaehlen.setText("Wird reserviert..");

            ClientThread ct = new ClientThread(this, host, port);
            Message m = new Message("reserveFilm");
            m.addAdditionalParameter("FI_ID", String.valueOf(film_id));
            m.addAdditionalParameter("email", Control.getControl().getSession().getEmail());

            ct.requestToServer(m);
            katalog_btn_auswaehlen.setDisable(true);
        });

        // TODO
    }

    @Override
    public void returnedMessage(Message m) {
        if (m.getCommand().equalsIgnoreCase("filmReserving")) {
            Platform.runLater(new Runnable() {

                @Override
                public void run() {
                    if (m.getAdditionalparameter().get("result").equalsIgnoreCase("ownreserved")) {
                        katalog_btn_auswaehlen.setText("Reserviert!");
                        katalog_btn_auswaehlen.setDisable(true);
                    }
                    if (m.getAdditionalparameter().get("result").equalsIgnoreCase("ok")) {
                        katalog_btn_auswaehlen.setDisable(false);
                        katalog_btn_auswaehlen.setText("Auswählen");
                    }
                    if (m.getAdditionalparameter().get("result").equalsIgnoreCase("noDVD")) {
                        katalog_btn_auswaehlen.setDisable(true);
                        katalog_btn_auswaehlen.setText("Keine DVDs auf Lager");
                    }
                    if (m.getAdditionalparameter().get("result").equalsIgnoreCase("noMore")) {
                        katalog_btn_auswaehlen.setDisable(true);
                        katalog_btn_auswaehlen.setText("Keine DVDs verfügbar");
                    }
                    if (m.getAdditionalparameter().get("result").equalsIgnoreCase("reserved")) {
                        katalog_btn_auswaehlen.setText("Reserviert!");
                        katalog_btn_auswaehlen.setDisable(true);
                    }
                    if (m.getAdditionalparameter().get("result").equalsIgnoreCase("failed")) {
                        katalog_btn_auswaehlen.setDisable(false);
                        katalog_btn_auswaehlen.setText("Auswählen");
                    }
                }
            });

        }
    }

    public void loadAfterInit() {
        ConfigManager cfgManager = Control.getControl().getCfgManager();
        String host = cfgManager.getConfigs().getProperty("ip");
        int port = Integer.parseInt(cfgManager.getConfigs().getProperty("port"));

        //Check ob schon reserviert
        ClientThread ct = new ClientThread(this, host, port);
        Message m = new Message("isFilmReserved");
        m.addAdditionalParameter("id", String.valueOf(film_id));
        m.addAdditionalParameter("email", Control.getControl().getSession().getEmail());
        ct.requestToServer(m);
    }

    public void setFilmID(int id) {
        this.film_id = id;
    }

    public void setTitel(String titel) {
        this.katalog_titel.setText(titel);
    }

    public void setCover(Image img) {
        katalog_cover.setImage(img);
    }

    public void setSubtitle(String subtitle) {
        this.katalog_subtitle.setText(subtitle);
    }

    public void setDescription(String desc) {
        this.katalog_desc.setText(desc);
    }

    public void setGenre(String genre) {
        this.katalog_genre.setText(genre);
    }

    public void setJahr(String jahr) {
        this.katalog_jahr.setText(jahr);
    }

    public void setLaufzeit(String laufzeit) {
        this.katalog_laufzeit.setText(laufzeit);
    }

    public void setVerfuegbar(boolean v) {
        if (v) {
            this.katalog_lbl_verfuegbar.setTextFill(Color.web("#FFFF00"));
            this.katalog_lbl_verfuegbar.setText("verfügbar");
            Image img = new Image(getClass().getClassLoader().getResource("de/codekings/client/GUI/Elements/verfuegbar.png").toExternalForm(), false);
        } else {
            this.katalog_lbl_verfuegbar.setTextFill(Color.web("#FF0000"));
            this.katalog_lbl_verfuegbar.setText("nicht verfügbar");
            Image img = new Image(getClass().getClassLoader().getResource("de/codekings/client/GUI/Elements/nverfuegbar.png").toExternalForm(), false);

        }
    }
}
