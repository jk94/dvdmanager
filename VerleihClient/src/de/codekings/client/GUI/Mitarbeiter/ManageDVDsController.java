/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.codekings.client.GUI.Mitarbeiter;

import de.codekings.client.Controls.Control;
import de.codekings.client.GUI.Kunden.Kunde_reservierungenController;
import de.codekings.client.connection.ClientThread;
import de.codekings.client.connection.MessageReturn;
import de.codekings.client.datacontent.Film_Client;
import de.codekings.common.Connection.Message;
import de.codekings.common.config.ConfigManager;
import de.codekings.common.datacontents.DVD;
import de.codekings.common.datacontents.Sendable;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

/**
 * FXML Controller class
 *
 * @author Jan
 */
public class ManageDVDsController implements Initializable, MessageReturn {

    @FXML
    private TableView<Film_Client> tbvw_filme;
    @FXML
    private TableView<DVD> list_dvd;
    @FXML
    private TextField txf_artikelnummer;
    @FXML
    private TextArea txf_notiz;
    @FXML
    private Button btn_speichern;
    @FXML
    private Button btn_add_dvd;
    @FXML
    private Button btn_remove_dvd;

    private ObservableList<Film_Client> filmdata;
    private boolean dvdserhalten = false;
    private ObservableList<DVD> dvdliste;
    private int selectedFilmID;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        ladeTableView();

        tbvw_filme.setOnMouseClicked((MouseEvent event) -> {
            int klickcount = event.getClickCount();

            if (klickcount == 2) {
                resetValues();
                Film_Client fc = tbvw_filme.getSelectionModel().getSelectedItem();

                selectedFilmID = fc.getFILMID();
                ladeDVDListe(fc);

            }
        });
    }

    @Override
    public void returnedMessage(Message m) {
        if (m.getCommand().equalsIgnoreCase("returnDVDs")) {
            for(Sendable s : m.getContent()){
                if(s instanceof DVD){
                    dvdliste.add((DVD) s);
                }
            }
            dvdserhalten = true;
        }
    }

    private void ladeDVDListe(Film_Client fc) {
        ConfigManager cfgManager = Control.getControl().getCfgManager();
        String host = cfgManager.getConfigs().getProperty("ip");
        int port = Integer.parseInt(cfgManager.getConfigs().getProperty("port"));

        ClientThread ct = new ClientThread(this, host, port);
        Message m = new Message("getDVDs");
        m.addAdditionalParameter("FI_ID", String.valueOf(fc.getFILMID()));
        ct.requestToServer(m);
        int counter = 0;
        while (!dvdserhalten && counter < 20) {
            try {
                counter++;
                Thread.sleep(100l);
            } catch (InterruptedException ex) {
                Logger.getLogger(Kunde_reservierungenController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        TableColumn<DVD, String> dvdnr = new TableColumn<>("DVD Artikelnummer");
        dvdnr.setCellValueFactory(new PropertyValueFactory<>("s_artikelnr"));

        list_dvd.getColumns().add(dvdnr);
        list_dvd.getItems().addAll(dvdliste);
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

    private void resetValues() {
        list_dvd.getItems().clear();

    }

}
