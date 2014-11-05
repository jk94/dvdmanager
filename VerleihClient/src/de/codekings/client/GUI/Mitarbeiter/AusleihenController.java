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
import de.codekings.common.datacontents.Kunde;
import de.codekings.common.datacontents.Reservierung;
import de.codekings.common.datacontents.Sendable;
import java.net.URL;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
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
public class AusleihenController implements Initializable, MessageReturn {

    @FXML
    private Button btn_anzeigen;
    @FXML
    private TextField txf_accountnr;
    @FXML
    private Button btn_submit;
    @FXML
    private DatePicker datepicker;
    @FXML
    private TableView<Reservierung> tbvw_reservierungen;
    @FXML
    private Button btn_remove;

    private ObservableList<Reservierung> reservierungsdata;
    private boolean nachrichterhalten = false;
    private String messagereturn = "";
    private String accnr = "0";
    private Kunde kunde = null;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ladeTableView();
        btn_remove.setOnMouseClicked((MouseEvent event) -> {
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
            } else {
                MessageBox mb = new MessageBox("Keine Reservierung ausgewählt!", MessageBoxType.OK_ONLY);
                mb.setAlwaysOnTop(true);
                mb.setTitle("Achtung!");
                mb.setResizable(false);
                mb.setWidth(225.0);
                mb.setHeight(175.0);
                mb.showAndWait();
            }
        });
        btn_anzeigen.setOnMouseClicked((MouseEvent event) -> {
            ladeReservierungen(Integer.parseInt(txf_accountnr.getText()));
            accnr = txf_accountnr.getText();
        });
        btn_submit.setOnMouseClicked((MouseEvent event) -> {
            if (tbvw_reservierungen.getItems().size() > 0 && Integer.parseInt(accnr) > 0 && datepicker.getValue() != null) {
                ConfigManager cfgManager = Control.getControl().getCfgManager();
                String host = cfgManager.getConfigs().getProperty("ip");
                int port = Integer.parseInt(cfgManager.getConfigs().getProperty("port"));

                Message getkunde = new Message("getKunde");
                getkunde.addAdditionalParameter("KU_ID", accnr);

                ClientThread c = new ClientThread(this, host, port);
                c.requestToServer(getkunde);
                kunde = null;
                nachrichterhalten = false;
                int counter = 0;
                while (counter < 20 && !nachrichterhalten) {
                    counter++;
                    try {
                        Thread.sleep(100l);
                    } catch (InterruptedException e) {
                    }
                }
                if (kunde != null) {
                    double kosten = 0.0;
                    for (Reservierung r : tbvw_reservierungen.getItems()) {
                        kosten = kosten + r.getKosten();
                    }
                    if (kosten > kunde.getAccountbalance()) {
                        MessageBox mb = new MessageBox("Kosten übersteigen das Guthaben\nBitte zuerst Guthaben aufladen", MessageBoxType.OK_ONLY);
                        mb.setResizable(false);
                        mb.setTitle("Warnung!");
                        mb.setAlwaysOnTop(true);
                        mb.show();
                    } else {
                        submit(kosten);
                    }
                }
            }
        });
    }

    public void submit(double kosten) {
        ConfigManager cfgManager = Control.getControl().getCfgManager();
        String host = cfgManager.getConfigs().getProperty("ip");
        int port = Integer.parseInt(cfgManager.getConfigs().getProperty("port"));

        LocalDate date = datepicker.getValue();
        Instant instant = date.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant();
        Date res = Date.from(instant);
        Message m = new Message("submitAusleihe");
        m.addAdditionalParameter("accnr", accnr);
        m.addAdditionalParameter("date", String.valueOf(res.getTime()));
        m.addAdditionalParameter("kosten", String.valueOf(kosten));

        ClientThread ct = new ClientThread(this, host, port);
        ct.requestToServer(m);
        nachrichterhalten = false;
        int counter = 0;
        while (counter < 20 && !nachrichterhalten) {
            counter++;
            try {
                Thread.sleep(100l);
            } catch (InterruptedException e) {
            }
        }

        if (messagereturn.equalsIgnoreCase("success")) {
            MessageBox mb = new MessageBox("Medien entliehen!", MessageBoxType.OK_ONLY);
            mb.setAlwaysOnTop(true);
            mb.setTitle("Information!");
            mb.setResizable(false);
            mb.showAndWait();
        } else {
            MessageBox mb = new MessageBox("Ausleihen fehlgeschlagen!\n\nBitte überprüfen Sie die Ausleihe.", MessageBoxType.OK_ONLY);
            mb.setAlwaysOnTop(true);
            mb.setTitle("Warnung!");
            mb.setResizable(false);
            mb.showAndWait();
        }
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
            nachrichterhalten = true;
        }
        if (m.getCommand().equalsIgnoreCase("returnsubmitAusleihe")) {
            messagereturn = m.getAdditionalparameter().get("result");
            nachrichterhalten = true;
        }
        if (m.getCommand().equalsIgnoreCase("returnKunde")) {
            for (Sendable s : m.getContent()) {
                if (s instanceof Kunde) {
                    kunde = (Kunde) s;
                    break;
                }
                nachrichterhalten = true;
            }
        }
    }

    private void ladeReservierungen(int accnr) {
        reservierungsdata = FXCollections.observableArrayList();

        //Get here Reservierungen
        ConfigManager cfgManager = Control.getControl().getCfgManager();
        String host = cfgManager.getConfigs().getProperty("ip");
        int port = Integer.parseInt(cfgManager.getConfigs().getProperty("port"));

        ClientThread ct = new ClientThread(this, host, port);
        Message m = new Message("getReservierungen");
        m.addAdditionalParameter("accnr", String.valueOf(accnr));
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
        ladeTableView();
        tbvw_reservierungen.getItems().clear();
        tbvw_reservierungen.getItems().addAll(reservierungsdata);
    }

    private void ladeTableView() {
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
    }

}
