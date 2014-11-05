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
import de.codekings.common.Enumerators.ClassType;
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
import se.mbaeumer.fxmessagebox.MessageBox;
import se.mbaeumer.fxmessagebox.MessageBoxType;

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
    private boolean antworterhalten = false;
    private String serverergebnis = "";
    private ObservableList<DVD> dvdliste;
    private int selectedFilmID;
    private int selectedDVDID;

    private final ConfigManager cfgManager = Control.getControl().getCfgManager();
    private final String host = cfgManager.getConfigs().getProperty("ip");
    private final int port = Integer.parseInt(cfgManager.getConfigs().getProperty("port"));

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        ladeTableView();
        leereDVDView();
        tbvw_filme.setOnMouseClicked((MouseEvent event) -> {
            int klickcount = event.getClickCount();

            if (klickcount == 2) {
                resetValuesAll();
                Film_Client fc = tbvw_filme.getSelectionModel().getSelectedItem();

                selectedFilmID = fc.getFILMID();
                selectedDVDID = -1;
                ladeDVDListe(fc);

            }
        });

        list_dvd.setOnMouseClicked((MouseEvent event) -> {
            int klickcount = event.getClickCount();

            if (klickcount == 2) {
                resetValues();
                DVD d = list_dvd.getSelectionModel().getSelectedItem();

                selectedDVDID = d.getDVDID();
                ladeDVDDaten(d);

            }
        });

        btn_remove_dvd.setOnMouseClicked((MouseEvent event) -> {
            int index = list_dvd.getSelectionModel().getSelectedIndex();

            ClientThread ct = new ClientThread(this, host, port);
            Message m = new Message("removeDVD");
            m.addAdditionalParameter("DVD_ID", String.valueOf(list_dvd.getItems().get(index).getDVDID()));
            ct.requestToServer(m);
            int counter = 0;
            while (!antworterhalten && counter < 20) {
                try {
                    counter++;
                    Thread.sleep(100l);
                } catch (InterruptedException ex) {
                    Logger.getLogger(Kunde_reservierungenController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (antworterhalten) {
                if (serverergebnis.equalsIgnoreCase("success")) {
                    list_dvd.getItems().remove(index);
                }
                if (serverergebnis.equalsIgnoreCase("dvdsout")) {
                    MessageBox mb = new MessageBox("DVD kann nicht gelöscht werden,\n"
                            + "da noch DVDs ausgeliehen sind", MessageBoxType.OK_ONLY);
                    mb.setAlwaysOnTop(true);
                    mb.setTitle("Fehler!");
                    mb.setResizable(false);
                    mb.showAndWait();
                }
            }

        });

        btn_add_dvd.setOnMouseClicked((MouseEvent event) -> {
            if (!txf_artikelnummer.getText().isEmpty()) {
                ClientThread ct = new ClientThread(this, host, port);
                Message m = new Message("addDVD");

                DVD d = new DVD(-1, Control.getControl().getDataManager().getFilm(selectedFilmID).getFilm(), false, ClassType.T_DVD, txf_artikelnummer.getText(), txf_notiz.getText());
                m.addSendable(d);
                m.addAdditionalParameter("email", Control.getControl().getSession().getEmail());
                antworterhalten = false;
                ct.requestToServer(m);
                int counter = 0;
                while (!antworterhalten && counter < 20) {
                    try {
                        counter++;
                        Thread.sleep(100l);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(Kunde_reservierungenController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                if (antworterhalten) {
                    if (serverergebnis.equalsIgnoreCase("success")) {
                        MessageBox mb = new MessageBox("DVD erstellt!", MessageBoxType.OK_ONLY);
                        mb.setAlwaysOnTop(true);
                        mb.setTitle("Information");
                        mb.setResizable(false);
                        mb.showAndWait();
                        ladeDVDListe(tbvw_filme.getItems().get(selectedFilmID));
                    }
                    if (serverergebnis.equalsIgnoreCase("artnrfailed")) {
                        MessageBox mb = new MessageBox("Die Artikelnummer ist bereits vergeben!", MessageBoxType.OK_ONLY);
                        mb.setAlwaysOnTop(true);
                        mb.setTitle("Fehler");
                        mb.setResizable(false);
                        mb.showAndWait();
                    }
                }
            }
        });

        btn_speichern.setOnMouseClicked((MouseEvent event) -> {
            if (!txf_artikelnummer.getText().isEmpty() && selectedDVDID != -1) {
                ClientThread ct = new ClientThread(this, host, port);
                Message m = new Message("saveDVD");

                DVD d = new DVD(selectedDVDID, Control.getControl().getDataManager().getFilm(selectedFilmID).getFilm(), false, ClassType.T_DVD, txf_artikelnummer.getText(), txf_notiz.getText());
                m.addSendable(d);
                m.addAdditionalParameter("email", Control.getControl().getSession().getEmail());
                antworterhalten = false;
                ct.requestToServer(m);
                int counter = 0;
                while (!antworterhalten && counter < 20) {
                    try {
                        counter++;
                        Thread.sleep(100l);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(Kunde_reservierungenController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                if (antworterhalten) {
                    if (serverergebnis.equalsIgnoreCase("success")) {
                        MessageBox mb = new MessageBox("Gespeichert!", MessageBoxType.OK_ONLY);
                        mb.setAlwaysOnTop(true);
                        mb.setTitle("Information");
                        mb.setResizable(false);
                        mb.showAndWait();
                        ladeDVDListe(tbvw_filme.getItems().get(selectedFilmID));
                    }
                    if (serverergebnis.equalsIgnoreCase("artnrfailed")) {
                        MessageBox mb = new MessageBox("Die Artikelnummer ist bereits vergeben!", MessageBoxType.OK_ONLY);
                        mb.setAlwaysOnTop(true);
                        mb.setTitle("Fehler");
                        mb.setResizable(false);
                        mb.showAndWait();
                    }
                }
            }
        });

    }

    @Override
    public void returnedMessage(Message m) {
        if (m.getCommand().equalsIgnoreCase("returnDVDs")) {
            dvdliste = FXCollections.observableArrayList();
            for (Sendable s : m.getContent()) {
                if (s instanceof DVD) {
                    dvdliste.add((DVD) s);
                }
            }
            dvdserhalten = true;
        }
        if (m.getCommand().equalsIgnoreCase("DVDremove")) {
            serverergebnis = m.getAdditionalparameter().get("result");
            antworterhalten = true;
        }
        if (m.getCommand().equalsIgnoreCase("DVDadd")) {
            serverergebnis = m.getAdditionalparameter().get("result");
            antworterhalten = true;
        }
        if (m.getCommand().equalsIgnoreCase("DVDsave")) {
            serverergebnis = m.getAdditionalparameter().get("result");
            antworterhalten = true;
        }
    }

    private void ladeDVDListe(Film_Client fc) {
        dvdliste = FXCollections.observableArrayList();
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
        dvdserhalten = false;
        TableColumn<DVD, String> dvdnr = new TableColumn<>("DVD Artikelnummer");
        dvdnr.setCellValueFactory(new PropertyValueFactory<>("s_artikelnr"));
        dvdnr.setMaxWidth(180.0);
        dvdnr.setMinWidth(180.0);
        list_dvd.getColumns().clear();
        list_dvd.getColumns().add(dvdnr);
        list_dvd.getItems().addAll(dvdliste);
    }

    private void leereDVDView() {

        TableColumn<DVD, String> dvdnr = new TableColumn<>("DVD Artikelnummer");
        dvdnr.setCellValueFactory(new PropertyValueFactory<>("s_artikelnr"));
        dvdnr.setMaxWidth(180.0);
        dvdnr.setMinWidth(180.0);
        list_dvd.getColumns().clear();
        list_dvd.getColumns().add(dvdnr);
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
        titel.setCellValueFactory(new PropertyValueFactory<>(variablename[0]));
        subtitel.setCellValueFactory(new PropertyValueFactory<>(variablename[1]));

        tbvw_filme.getColumns().clear();
        tbvw_filme.getColumns().add(titel);
        tbvw_filme.getColumns().add(subtitel);
        tbvw_filme.getItems().addAll(filmdata);
    }

    private void resetValuesAll() {
        list_dvd.getItems().clear();
        resetValues();
    }

    private void resetValues() {
        txf_artikelnummer.setText("");
        txf_notiz.setText("");
    }

    private void ladeDVDDaten(DVD d) {
        txf_artikelnummer.setText(d.getS_artikelnr());
        txf_notiz.setText(d.getS_notiz());
    }

}
