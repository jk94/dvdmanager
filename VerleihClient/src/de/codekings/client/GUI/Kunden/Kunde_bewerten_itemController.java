/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.codekings.client.GUI.Kunden;

import de.codekings.client.Controls.Control;
import de.codekings.client.connection.ClientThread;
import de.codekings.client.connection.MessageReturn;
import de.codekings.common.Connection.Message;
import de.codekings.common.config.ConfigManager;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

/**
 * FXML Controller class
 *
 * @author Jan
 */
public class Kunde_bewerten_itemController implements Initializable, MessageReturn {

    @FXML
    private Label bewerten_filmtitel;
    @FXML
    private ImageView empfehlung_cover;
    @FXML
    private Label empfehlung_frage;

    private int FILM_ID;

    private Button empehlung_positiv;
    @FXML
    private Label bewerten_subtitle;
    @FXML
    private Button bewerten_positiv;
    @FXML
    private ImageView bewerten_positiv_img;
    @FXML
    private Button bewerten_negativ;
    @FXML
    private ImageView bewerten_negativ_img;

    @Override
    public void returnedMessage(Message m) {
        if (m.getCommand().equalsIgnoreCase("bewertungresponse")) {
            empfehlung_frage.setText("Bewertung abgegeben!");
            bewerten_negativ.setDisable(true);
            bewerten_positiv.setDisable(true);
        }
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        bewerten_positiv_img.setImage(new Image(Kunde_bewerten_itemController.class.getClassLoader().getResourceAsStream("de/codekings/client/GUI/Elements/bewertung_up.png")));
        bewerten_negativ_img.setImage(new Image(Kunde_bewerten_itemController.class.getClassLoader().getResourceAsStream("de/codekings/client/GUI/Elements/bewertung_down.png")));

        this.bewerten_positiv.setOnMouseClicked((MouseEvent event) -> {
            ConfigManager cfgManager = Control.getControl().getCfgManager();
            String host = cfgManager.getConfigs().getProperty("ip");
            int port = Integer.parseInt(cfgManager.getConfigs().getProperty("port"));

            ClientThread bewertung_conn = new ClientThread(null, host, port);
            Message bewertung = new Message("bewertung");
            bewertung.addAdditionalParameter("FILM_ID", String.valueOf(FILM_ID));
            bewertung.addAdditionalParameter("type", "+");

            bewertung_conn.requestToServer(bewertung);

            bewerten_positiv.setDisable(true);
        });

        this.bewerten_negativ.setOnMouseClicked((MouseEvent event) -> {
            ConfigManager cfgManager = Control.getControl().getCfgManager();
            String host = cfgManager.getConfigs().getProperty("ip");
            int port = Integer.parseInt(cfgManager.getConfigs().getProperty("port"));

            ClientThread bewertung_conn = new ClientThread(null, host, port);
            Message bewertung = new Message("bewertung");
            bewertung.addAdditionalParameter("FILM_ID", String.valueOf(FILM_ID));
            bewertung.addAdditionalParameter("type", "-");

            bewertung_conn.requestToServer(bewertung);
            bewerten_negativ.setDisable(true);
        });
        // TODO
    }

    public void setTitel(String titel) {
        this.bewerten_filmtitel.setText(titel);
    }

    public void setCover(Image img) {
        this.empfehlung_cover.setImage(img);
    }

    public void setFilmID(int id) {
        this.FILM_ID = id;
    }

    public void setSubtitle(String subtitle) {
        this.bewerten_subtitle.setText(subtitle);
    }

}
