/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package de.codekings.client.GUI.Kunden;

import de.codekings.client.Controls.ContentControl;
import de.codekings.client.Controls.Control;
import de.codekings.client.Enum.ContentPageType;
import de.codekings.client.connection.ClientThread;
import de.codekings.client.connection.MessageReturn;
import de.codekings.common.Connection.Message;
import de.codekings.common.config.ConfigManager;
import de.codekings.common.datacontents.Kunde;
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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

/**
 * FXML Controller class
 *
 * @author Simon
 */
public class Kunde_startController implements Initializable, MessageReturn {
    @FXML
    private Label kunde_start_warenkorb;
    @FXML
    private Label kunde_start_titel;
    @FXML
    private Pane kunde_start_warenkorbpane;
    @FXML
    private Button kunde_start_btnausleihe;
    @FXML
    private Pane kunde_start_entlpane;
    @FXML
    private Button kunde_start_btnentl;
    @FXML
    private Label kunde_start_entl;
    @FXML
    private Label kunde_start_guthaben;
    @FXML
    private Pane kunde_start_kontopane;
    @FXML
    private Button kunde_start_kontoverwaltung;

    private Kunde kundendata;

    private boolean datenerhalten = false;
    
    private ObservableList<Reservierung> reservierungsdata;
    
    private boolean reservierungenerhalten = false;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        ladeDaten();
        
        kunde_start_btnausleihe.setOnMouseClicked((MouseEvent event) -> {
            Control.getControl().getContentControl().changeContent(ContentPageType.Kunde_Reservierungen, false);
           
        });
        
        kunde_start_btnentl.setOnMouseClicked((MouseEvent event) -> {
            Control.getControl().getContentControl().changeContent(ContentPageType.Kunde_EntlieheneArt, false);
        });
        
        kunde_start_kontoverwaltung.setOnMouseClicked((MouseEvent event) -> {
            Control.getControl().getContentControl().changeContent(ContentPageType.Kunde_Verwaltung, false);
        });
        
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
            
            kunde_start_titel.setText("Hallo, " + kundendata.getVorname()+ " " + kundendata.getName());
            kunde_start_guthaben.setText("Dein aktuelles Guthaben beträgt " + kundendata.getAccountbalance() + " €.");
            
        }
            //kunde_start_entl.setText();
            int anz = getCountReservierungen();
            kunde_start_warenkorb.setText("Zurzeit sind " + anz + " Artikel von dir reserviert.");
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

    private int getCountReservierungen() {
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
        reservierungenerhalten = false;
        
        int anzahlReservierungen = reservierungsdata.size();
        
        return anzahlReservierungen;
    }
}
