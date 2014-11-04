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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import se.mbaeumer.fxmessagebox.MessageBox;
import se.mbaeumer.fxmessagebox.MessageBoxResult;
import se.mbaeumer.fxmessagebox.MessageBoxType;

/**
 * FXML Controller class
 *
 * @author Simon
 */
public class Kunde_reservierungenController implements Initializable, MessageReturn {

    @FXML
    private Label kunde_ausleihen_kosten;
    @FXML
    private TableView<Reservierung> tbvw_reservierungen;
    @FXML
    private Button btn_loeschen;

    private ObservableList<Reservierung> reservierungsdata;
    private boolean reservierungenerhalten = false;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        ladeTableView();

        btn_loeschen.setOnMouseClicked((MouseEvent event) -> {
            if (tbvw_reservierungen.getSelectionModel().getSelectedItems().size() == 1) {
                MessageBox mb = new MessageBox("Möchten Sie diese Reservierung wirklich löschen?", MessageBoxType.YES_NO);
                mb.setAlwaysOnTop(true);
                mb.setTitle("Achtung!");
                mb.setResizable(false);
                mb.setWidth(225.0);
                mb.setHeight(175.0);
                mb.showAndWait();
                if (mb.getMessageBoxResult().equals(MessageBoxResult.YES)) {
                    Reservierung r = tbvw_reservierungen.getSelectionModel().getSelectedItem();
                    ConfigManager cfgManager = Control.getControl().getCfgManager();
                    String host = cfgManager.getConfigs().getProperty("ip");
                    int port = Integer.parseInt(cfgManager.getConfigs().getProperty("port"));

                    ClientThread ct = new ClientThread(this, host, port);
                    Message m = new Message("removeReservierung");
                    m.addAdditionalParameter("RES_ID", String.valueOf(r.getResid()));
                    ct.requestToServer(m);
                    tbvw_reservierungen.getItems().remove(r);
                }
            }else{
                MessageBox mb = new MessageBox("Keine Reservierung ausgewählt!", MessageBoxType.OK_ONLY);
                mb.setAlwaysOnTop(true);
                mb.setTitle("Achtung!");
                mb.setResizable(false);
                mb.setWidth(225.0);
                mb.setHeight(175.0);
                mb.showAndWait();
            }
        });
    }

    @Override
    public void returnedMessage(Message m) {
        if (m.getCommand().equalsIgnoreCase("returnReservierungen")) {
            reservierungsdata = FXCollections.observableArrayList();
            for (Sendable s : m.getContent()) {
                if (s instanceof Reservierung) {
                    Reservierung r = (Reservierung) s;
                    reservierungsdata.add(r);
                }
            }
            reservierungenerhalten = true;
        }
    }

    private void ladeTableView() {
        reservierungsdata = FXCollections.observableArrayList();

        //Get here Reservierungen
        ConfigManager cfgManager = Control.getControl().getCfgManager();
        String host = cfgManager.getConfigs().getProperty("ip");
        int port = Integer.parseInt(cfgManager.getConfigs().getProperty("port"));

        ClientThread ct = new ClientThread(this, host, port);
        Message m = new Message("getReservierungen");
        m.addAdditionalParameter("email", Control.getControl().getSession().getEmail());
        ct.requestToServer(m);
        int counter = 0;
        while (!reservierungenerhalten && counter < 20) {
            try {
                counter++;
                Thread.sleep(100l);
            } catch (InterruptedException ex) {
                Logger.getLogger(Kunde_reservierungenController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        String columnname[] = {"#", "Filmname", "Artikelnr.", "gültig bis", "Kosten"};
        String variablename[] = {"listnummer", "filmname", "artikelnr", "gueltigbis", "kosten"};

        TableColumn<Reservierung, Integer> nr = new TableColumn<>(columnname[0]);
        TableColumn<Reservierung, String> filmname = new TableColumn<>(columnname[1]);
        TableColumn<Reservierung, Integer> artnr = new TableColumn<>(columnname[2]);
        TableColumn<Reservierung, String> gueltig = new TableColumn<>(columnname[3]);
        TableColumn<Reservierung, String> kosten = new TableColumn<>(columnname[4]);

        nr.setCellValueFactory(new PropertyValueFactory<>(variablename[0]));
        filmname.setCellValueFactory(new PropertyValueFactory<>(variablename[1]));
        artnr.setCellValueFactory(new PropertyValueFactory<>(variablename[2]));
        gueltig.setCellValueFactory(new PropertyValueFactory<>(variablename[3]));
        kosten.setCellValueFactory(new PropertyValueFactory<>(variablename[4]));

        tbvw_reservierungen.getColumns().clear();
        tbvw_reservierungen.getColumns().addAll(nr, filmname, artnr, gueltig, kosten);
        tbvw_reservierungen.getItems().addAll(reservierungsdata);
    }
}
