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
import de.codekings.common.Connection.Message;
import de.codekings.common.config.ConfigManager;
import de.codekings.common.datacontents.Ausleihe;
import de.codekings.common.datacontents.Reservierung;
import de.codekings.common.datacontents.Sendable;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import se.mbaeumer.fxmessagebox.MessageBox;
import se.mbaeumer.fxmessagebox.MessageBoxType;

/**
 * FXML Controller class
 *
 * @author Heiko
 */
public class ZurueckgebenController implements Initializable, MessageReturn {

    @FXML
    private Label ma_return_label;
    @FXML
    private Button btn_abschließen;
    @FXML
    private TableView<Ausleihe> tbvw_filme;
    @FXML
    private TextField txf_artikelnummer;
    @FXML
    private Button btn_suchen;

    private ObservableList<Ausleihe> ausleihliste = FXCollections.observableArrayList();
    private boolean nachrichterhalten;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        ausleihliste.clear();
        ladeTableView();

        btn_suchen.setOnMouseClicked((MouseEvent event) -> {
            if (!bereitsInListe(txf_artikelnummer.getText())) {
                ConfigManager cfgManager = Control.getControl().getCfgManager();
                String host = cfgManager.getConfigs().getProperty("ip");
                int port = Integer.parseInt(cfgManager.getConfigs().getProperty("port"));

                ClientThread ct = new ClientThread(this, host, port);
                Message m = new Message("getAusleihung");
                m.addAdditionalParameter("art_nr", txf_artikelnummer.getText());
                nachrichterhalten = false;
                ct.requestToServer(m);
                int counter = 0;
                while (!nachrichterhalten && counter < 20) {
                    try {
                        counter++;
                        Thread.sleep(100l);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(Kunde_reservierungenController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                nachrichterhalten = false;
                updateItems();
            } else {
                MessageBox mb = new MessageBox("Bereits in der Liste vorhanden.", MessageBoxType.OK_ONLY);
                mb.setAlwaysOnTop(true);
                mb.setTitle("Information!");
                mb.setResizable(false);
                mb.showAndWait();
            }
        });

        btn_abschließen.setOnMouseClicked((MouseEvent event) -> {

            ConfigManager cfgManager = Control.getControl().getCfgManager();
            String host = cfgManager.getConfigs().getProperty("ip");
            int port = Integer.parseInt(cfgManager.getConfigs().getProperty("port"));

            ClientThread ct = new ClientThread(this, host, port);
            Message m = new Message("removeAusleihung");
            for (Ausleihe a : ausleihliste) {
                m.addSendable(a);
            }
            ct.requestToServer(m);
            ausleihliste.clear();
            updateItems();
            MessageBox mb = new MessageBox("Medien wurden zurückgegeben!", MessageBoxType.OK_ONLY);
            mb.setAlwaysOnTop(true);
            mb.setTitle("Information!");
            mb.setResizable(false);
            mb.showAndWait();

        });

    }

    @Override
    public void returnedMessage(Message m) {
        if (m.getCommand().equalsIgnoreCase("returnAusleihe")) {
            for (Sendable s : m.getContent()) {
                if (s instanceof Ausleihe) {
                    Ausleihe ausleihe = (Ausleihe) s;
                    ausleihe.setNr(ausleihliste.size() + 1);
                    ausleihliste.add(ausleihe);
                    updateItems();
                }
            }
        }
    }

    private void updateItems() {
        tbvw_filme.getItems().clear();
        tbvw_filme.getItems().addAll(ausleihliste);
    }

    private void ladeTableView() {
        tbvw_filme.getItems().clear();

        String columnname[] = {"#", "Filmname", "Artikelnr.", "Beginn", "Ende",};
        String variablename[] = {"nr", "filmname", "artikelnr", "beginn", "ende"};

        TableColumn<Ausleihe, Integer> nr = new TableColumn<>(columnname[0]);
        TableColumn<Ausleihe, String> filmname = new TableColumn<>(columnname[1]);
        TableColumn<Ausleihe, Integer> artnr = new TableColumn<>(columnname[2]);
        TableColumn<Ausleihe, String> beginn = new TableColumn<>(columnname[3]);
        TableColumn<Ausleihe, String> ende = new TableColumn<>(columnname[4]);

        nr.setCellValueFactory(new PropertyValueFactory<>(variablename[0]));
        filmname.setCellValueFactory(new PropertyValueFactory<>(variablename[1]));
        artnr.setCellValueFactory(new PropertyValueFactory<>(variablename[2]));
        beginn.setCellValueFactory(new PropertyValueFactory<>(variablename[3]));
        ende.setCellValueFactory(new PropertyValueFactory<>(variablename[4]));

        tbvw_filme.getColumns().clear();
        tbvw_filme.getColumns().addAll(nr, filmname, artnr, beginn, ende);
    }

    private boolean bereitsInListe(String artnr) {
        boolean erg = false;
        for (Ausleihe a : ausleihliste) {
            if (a.getArtikelnr() == Integer.parseInt(artnr)) {
                erg = true;
                break;
            }
        }
        return erg;
    }
}
