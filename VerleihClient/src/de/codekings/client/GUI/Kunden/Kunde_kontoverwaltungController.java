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
import de.codekings.common.datacontents.Kunde;
import de.codekings.common.datacontents.Sendable;
import de.codekings.common.datacontents.User;
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
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author Simon
 */
public class Kunde_kontoverwaltungController implements Initializable, MessageReturn {

    @FXML
    private Label kunde_konto_name;
    @FXML
    private Label kunde_konto_email;
    @FXML
    private Label kunde_konto_vorname;
    @FXML
    private Label kunde_konto_adresse;
    @FXML
    private Label kunde_konto_hausnr;
    @FXML
    private Label kunde_konto_plz;
    @FXML
    private Label kunde_konto_ort;
    @FXML
    private Label kunde_konto_guthaben;

    private Kunde kundendata;

    private boolean datenerhalten = false;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        ladeDaten();

    }

    public void ladeDaten() {
        String email = Control.getControl().getSession().getEmail();

        ConfigManager cfgManager = Control.getControl().getCfgManager();
        String host = cfgManager.getConfigs().getProperty("ip");
        int port = Integer.parseInt(cfgManager.getConfigs().getProperty("port"));

        Message request = new Message("getKunde");
        request.addAdditionalParameter("email", email);
        ClientThread loginsession = new ClientThread(this, host, port);
        loginsession.requestToServer(request);
        datenerhalten = false;
        int counter = 0;
        while (!datenerhalten && counter < 20) {
            try {
                counter++;
                Thread.sleep(100l);
            } catch (InterruptedException ex) {
                Logger.getLogger(Kunde_reservierungenController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        if (kundendata != null) {
            kunde_konto_vorname.setText(kundendata.getVorname());
            kunde_konto_name.setText(kundendata.getName());
            kunde_konto_email.setText(kundendata.getEmail());
            kunde_konto_adresse.setText(kundendata.getStrasse());
            kunde_konto_hausnr.setText(String.valueOf(kundendata.getHausnr()));
            kunde_konto_plz.setText(kundendata.getPlz());
            kunde_konto_ort.setText(kundendata.getOrt());
            kunde_konto_guthaben.setText(String.valueOf(kundendata.getAccountbalance()));
        }
    }

    @Override
    public void returnedMessage(Message m) {
        if (m.getCommand().equalsIgnoreCase("returnKunde")) {

            for (Sendable s : m.getContent()) {
                if (s instanceof Kunde) {
                    kundendata = (Kunde) s;
                }
            }

            datenerhalten = true;
        }
    }
}
