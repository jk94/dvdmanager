/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.codekings.client.GUI.Mitarbeiter;

import de.codekings.client.Controls.Control;
import de.codekings.client.connection.ClientThread;
import de.codekings.client.connection.MessageReturn;
import de.codekings.client.datacontent.Film_Client;
import de.codekings.common.Connection.Message;
import de.codekings.common.config.ConfigManager;
import de.codekings.common.datacontents.Genre;
import de.codekings.common.datacontents.Sendable;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author Simon
 */
public class Create_filmController implements Initializable, MessageReturn {

    @FXML
    private AnchorPane create_anchor;
    @FXML
    private TextField txf_titel;
    @FXML
    private TextArea txf_beschreibung;
    @FXML
    private RadioButton rbtn_fsk0;
    @FXML
    private ToggleGroup fskbuttons;
    @FXML
    private RadioButton rbtn_fsk6;
    @FXML
    private RadioButton rbtn_fsk12;
    @FXML
    private RadioButton rbtn_fsk16;
    @FXML
    private RadioButton rbtn_fsk18;
    @FXML
    private TextField txf_trailer;
    @FXML
    private TextField txf_schauspieler;
    @FXML
    private TextField txf_regisseur;
    @FXML
    private TextField txf_subtitel;
    @FXML
    private ComboBox<String> cb_genre;
    @FXML
    private ListView<String> li_genre_added;
    @FXML
    private Button btn_genre_add;
    @FXML
    private Button btn_genre_remove;
    @FXML
    private ImageView img_cover;
    @FXML
    private TableView<Film_Client> tbvw_filme;

    private ObservableList<Film_Client> filmdata;

    @FXML
    private Button btn_create;
    @FXML
    private Button btn_reset;
    @FXML
    private Button btn_update;
    @FXML
    private Button btn_remove;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        ladeTableView();
        ladeGenreList();
        btn_genre_add.setOnMouseClicked((MouseEvent event) -> {

        });

        btn_reset.setOnMouseClicked((MouseEvent event) -> {
            txf_beschreibung.setText("");
            txf_regisseur.setText("");
            txf_schauspieler.setText("");
            txf_subtitel.setText("");
            txf_titel.setText("");
            txf_trailer.setText("");
            cb_genre.setValue("");
            ladeGenreList();
            li_genre_added.getItems().clear();
        });

        btn_genre_add.setOnMouseClicked((MouseEvent event) -> {
            ArrayList<Genre> genres = (ArrayList<Genre>) Control.getControl().getDataManager().getGenres().clone();
            String genreinput = cb_genre.getValue();
            boolean vorhanden = false;
            for (Genre genre : genres) {
                if (genre.getGenrebezeichnung().equals(genreinput)) {
                    vorhanden = true;
                    break;
                }
            }
            if (!vorhanden) {
                ConfigManager cfgManager = Control.getControl().getCfgManager();
                String host = cfgManager.getConfigs().getProperty("ip");
                int port = Integer.parseInt(cfgManager.getConfigs().getProperty("port"));

                ClientThread ct = new ClientThread(this, host, port);

                Message m = new Message("addGenre");
                m.addAdditionalParameter("bez", genreinput);

                ct.requestToServer(m);
                //Genre dem Server hinzufügen!!
            }
            li_genre_added.getItems().add(genreinput);
            
            
//Aus der CB löschen..
        });

        btn_genre_remove.setOnMouseClicked((MouseEvent event) -> {
            int index = li_genre_added.getSelectionModel().getSelectedIndex();
            li_genre_added.getItems().remove(index);
            //der Combobox hinzufügen
        });

    }

    @Override
    public void returnedMessage(Message m) {
        if (m.getCommand().equalsIgnoreCase("addedGenre")) {
            for (Sendable s : m.getContent()) {
                if (s instanceof Genre) {

                }
            }
        }
    }

    private void ladeGenreList() {
        cb_genre.getItems().clear();
        ArrayList<Genre> genres = (ArrayList<Genre>) Control.getControl().getDataManager().getGenres().clone();
        Collections.sort(genres);
        for (Genre g : genres) {
            cb_genre.getItems().add(g.getGenrebezeichnung());
        }   
    }

    private void ladeTableView() {
        filmdata = FXCollections.observableArrayList();
        ArrayList<Film_Client> filme = (ArrayList<Film_Client>) Control.getControl().getDataManager().getFilme().clone();
        for (Film_Client fc : filme) {
            filmdata.add(fc);
        }

        String columnname[] = {"Titel", "Subtitel"};
        String variablename[] = {"s_titel", "s_subtitel"};

        TableColumn<Film_Client, String> titel = new TableColumn<>(columnname[0]);
        TableColumn<Film_Client, String> subtitel = new TableColumn<>(columnname[1]);
        titel.setCellValueFactory(new PropertyValueFactory<Film_Client, String>(variablename[0]));
        subtitel.setCellValueFactory(new PropertyValueFactory<Film_Client, String>(variablename[1]));

        tbvw_filme.getColumns().clear();
        tbvw_filme.getColumns().add(titel);
        tbvw_filme.getColumns().add(subtitel);
        tbvw_filme.getItems().addAll(filmdata);
    }

}
