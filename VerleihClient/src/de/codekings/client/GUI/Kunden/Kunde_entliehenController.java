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
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;



/**
 * FXML Controller class
 *
 * @author Simon
 */
public class Kunde_entliehenController implements Initializable, MessageReturn {
    @FXML
    private Label kunde_entl_titel;
    @FXML
    private TableView<?> kunde_entliehen_tabelle;

    private boolean ausleihenerhalten = false;
    private ObservableList<Ausleihe> ausleihdata;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
       ladeTableView();
    }    
    
    @Override
    public void returnedMessage(Message m) {
        if (m.getCommand().equalsIgnoreCase("returnAusleihen")) {
            ausleihdata = FXCollections.observableArrayList();
            for (Sendable s : m.getContent()) {
                if (s instanceof Ausleihe) {
                    Ausleihe a = (Ausleihe) s;
                    ausleihdata.add(a);
                }
            }
            ausleihenerhalten = true;
        }
    }
    
    private void ladeTableView() {
        ausleihdata = FXCollections.observableArrayList();

        //Get here Ausleihen
        ConfigManager cfgManager = Control.getControl().getCfgManager();
        String host = cfgManager.getConfigs().getProperty("ip");
        int port = Integer.parseInt(cfgManager.getConfigs().getProperty("port"));

        ClientThread ct = new ClientThread(this, host, port);
        Message m = new Message("getAusleihen");
        m.addAdditionalParameter("email", Control.getControl().getSession().getEmail());
        ct.requestToServer(m);
        int counter = 0;
        while (!ausleihenerhalten && counter < 20) {
            try {
                counter++;
                Thread.sleep(100l);
            } catch (InterruptedException ex) {
                Logger.getLogger(Kunde_entliehenController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        ausleihenerhalten = false;

        String columnname[] = {"#", "Filmname", "Artikelnr.", "Beginn", "Ende"};
        String variablename[] = {"listnummer", "filmname", "artikelnr", "beginn", "ende"};

        TableColumn<Ausleihe, Integer> nr = new TableColumn<>(columnname[0]);
        TableColumn<Ausleihe, String> filmname = new TableColumn<>(columnname[1]);
        TableColumn<Ausleihe, Integer> artnr = new TableColumn<>(columnname[2]);
        //TableColumn<Ausleihe, Date> beginn = new TableColumn<>(columnname[3]);
        //TableColumn<Ausleihe, Date> ende = new TableColumn<>(columnname[4]);

        nr.setCellValueFactory(new PropertyValueFactory<>(variablename[0]));
        filmname.setCellValueFactory(new PropertyValueFactory<>(variablename[1]));
        artnr.setCellValueFactory(new PropertyValueFactory<>(variablename[2]));
        //beginn.setCellValueFactory(new PropertyValueFactory<>(variablename[3]));
        //ende.setCellValueFactory(new PropertyValueFactory<>(variablename[4]));

        kunde_entliehen_tabelle.getColumns().clear();
        //kunde_entliehen_tabelle.getColumns().addAll(nr, filmname, artnr, beginn, ende);
        //kunde_entliehen_tabelle.getItems().addAll();
    }
}
