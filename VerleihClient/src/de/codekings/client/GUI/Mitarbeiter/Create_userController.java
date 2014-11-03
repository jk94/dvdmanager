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
import de.codekings.common.Connection.Hasher;
import de.codekings.common.Connection.Message;
import de.codekings.common.config.ConfigManager;
import de.codekings.common.datacontents.Sendable;
import de.codekings.common.datacontents.User;
import java.net.URL;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import se.mbaeumer.fxmessagebox.MessageBox;
import se.mbaeumer.fxmessagebox.MessageBoxType;

/**
 * FXML Controller class
 *
 * @author Jan
 *
 */
public class Create_userController implements Initializable, MessageReturn {

    @FXML
    private PasswordField user_passwort;
    @FXML
    private PasswordField user_passwort_check;
    @FXML
    private DatePicker user_datum;
    @FXML
    private ListView<?> user_list;
    @FXML
    private TextField user_input_email;
    @FXML
    private TextField user_input_vorname;
    @FXML
    private TextField user_input_nachname;
    @FXML
    private TextField user_input_adresse;
    @FXML
    private TextField user_table_head;
    @FXML
    private TextField user_input_ort;
    @FXML
    private TextField user_input_plz;
    @FXML
    private Button user_btn_create;
    @FXML
    private CheckBox user_check_kunde;
    @FXML
    private CheckBox user_check_mitarbeiter;
    @FXML
    private CheckBox user_check_admin;
    @FXML
    private Button user_btn_verwerfen;
    @FXML
    private Button user_btnspeichern;

    private boolean emailOkay = false, emailvalidation = false;

    private ObservableList<User> userdata;

    private boolean usererhalten = false;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        user_btn_create.setOnMouseClicked((MouseEvent event) -> {
            //variablen definieren
            String vorname = user_input_vorname.getText();
            String name = user_input_nachname.getText();
            String email = user_input_email.getText();
            String passwort = user_passwort.getText();

            LocalDate geburtstag = user_datum.getValue();
            Instant datum = Instant.from(geburtstag.atStartOfDay(ZoneId.systemDefault()));
            Date d_geb = Date.from(datum);

            String plz = user_input_plz.getText();
            String ort = user_input_ort.getText();
            String str = user_input_adresse.getText();

            check_Input();

            ConfigManager cfgManager = Control.getControl().getCfgManager();
            String host = cfgManager.getConfigs().getProperty("ip");
            int port = Integer.parseInt(cfgManager.getConfigs().getProperty("port"));

            ClientThread emailv = new ClientThread(this, host, port);
            Message request = new Message("CreateUser");
            String hpw = Hasher.getInstance().ToMD5(user_passwort.getText());

            //Kunde Kunde_neu = new Kunde(0, vorname, str, plz, ort, hpw, email, 0, hausnr, datum, null, null, port, ClassType.T_KUNDE);
            emailv.requestToServer(request);

            // schreibe eingaben in db
        });

        user_btnspeichern.setOnMouseClicked((MouseEvent event) -> {
            check_Input();
            // ändere db nach eingabe
        });
        user_btn_verwerfen.setOnMouseClicked((MouseEvent event) -> {
            //lade Seite neu, ohne eingaben zu behalten
        });

    }

    @Override
    public void returnedMessage(Message m) {
        if (m.getCommand().equalsIgnoreCase("ReturnEmailValidation")) {

            boolean okay = Boolean.getBoolean(m.getAdditionalparameter().get("status"));
            emailOkay = okay;
            emailvalidation = true;
        }
        if (m.getCommand().equalsIgnoreCase("ReturnUserData")) {

            for (Sendable s : m.getContent()) {
                if (s instanceof User) {
                    userdata.add((User) s);
                }
            }

            usererhalten = true;
        }
    }

    public void check_Input() {
        String vorname = user_input_vorname.getText();
        if (!vorname.matches("[a-zA-Z]")) {
            MessageBox error = new MessageBox("Vorname darf nur aus Buchstaben bestehen.", MessageBoxType.OK_ONLY);
            error.showAndWait();
        }
        String name = user_input_nachname.getText();
        if (!name.matches("[a-zA-Z]")) {
            MessageBox error = new MessageBox("Name darf nur aus Buchstaben bestehen.", MessageBoxType.OK_ONLY);
            error.showAndWait();
        }
        String email = user_input_email.getText();
        if (!email.matches("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$")) {
            ConfigManager cfgManager = Control.getControl().getCfgManager();
            String host = cfgManager.getConfigs().getProperty("ip");
            int port = Integer.parseInt(cfgManager.getConfigs().getProperty("port"));

            ClientThread emailv = new ClientThread(this, host, port);
            Message request = new Message("checkEmail");
            request.addAdditionalParameter("email", email);
            emailv.requestToServer(request);
            while (!emailvalidation) {
                try {
                    Thread.sleep(1000l);
                } catch (InterruptedException ex) {
                    Logger.getLogger(Create_userController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (emailOkay) {

            } else {

            }
            emailvalidation = false;
            MessageBox error = new MessageBox("Email entspricht nicht vorgegebenem Format.", MessageBoxType.OK_ONLY);
            error.showAndWait();
        }
        String passwort = user_passwort.getText();
        if (!passwort.matches("((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{6,20})")) {
            MessageBox error = new MessageBox("Passwortkriterien nicht erfüllt.", MessageBoxType.OK_ONLY);
            error.showAndWait();
        }
        String passwort_check = user_passwort_check.getText();
        if (!passwort_check.equals(passwort_check)) {
            MessageBox error = new MessageBox("Passwörter stimmen nicht überein!", MessageBoxType.OK_ONLY);
            error.showAndWait();
        }

        LocalDate geburtstag = user_datum.getValue();
        Instant datum = Instant.from(geburtstag.atStartOfDay(ZoneId.systemDefault()));
        Date d_geb = Date.from(datum);

        //System.out.println(d_geb.toLocaleString());
        String plz = user_input_plz.getText();
        if (!vorname.matches("[0-9]")) {
            MessageBox error = new MessageBox("Postleitzahl darf nur aus Zahlen bestehen.", MessageBoxType.OK_ONLY);
            error.showAndWait();
        }
        String ort = user_input_ort.getText();
        if (!vorname.matches("[a-zA-Z]")) {
            MessageBox error = new MessageBox("Ort darf nur aus Buchstaben bestehen.", MessageBoxType.OK_ONLY);
            error.showAndWait();
        }
        String str = user_input_adresse.getText();
        if (!vorname.matches("(?=.*\\d)(?=.*[a-zA-Z])")) {
            MessageBox error = new MessageBox("Adresse muss Hausnummer enthalten.", MessageBoxType.OK_ONLY);
            error.showAndWait();
        }

        int permission = 0;
        if (user_check_kunde.isSelected()) {
            permission = 1;
        }
        if (user_check_mitarbeiter.isSelected()) {
            permission = 2;
        }
        if (user_check_admin.isSelected()) {
            permission = 3;
        }

    }

    private void ladeTableView() {
        userdata = FXCollections.observableArrayList();

        ConfigManager cfgManager = Control.getControl().getCfgManager();
        String host = cfgManager.getConfigs().getProperty("ip");
        int port = Integer.parseInt(cfgManager.getConfigs().getProperty("port"));

        Message request = new Message("getUsers");

        ClientThread loginsession = new ClientThread(this, host, port);
        loginsession.requestToServer(request);

        int counter = 0;
        while (counter < 50 && !usererhalten) {
            counter++;
            try {
                Thread.sleep(500l);
            } catch (InterruptedException e) {
            }
        }

        usererhalten = false;

        String columnname[] = {"Name", "Vorname", "Accountnr."};
        String variablename[] = {"name", "vorname" , "accountnummer"};

        TableColumn<User, String> titel = new TableColumn<>(columnname[0]);
        TableColumn<User, String> subtitel = new TableColumn<>(columnname[1]);
        
        titel.setCellValueFactory(new PropertyValueFactory<User, String>(variablename[0]));
        subtitel.setCellValueFactory(new PropertyValueFactory<User, String>(variablename[1]));

/*        tbvw_filme.getColumns().clear();
        tbvw_filme.getColumns().add(titel);
        tbvw_filme.getColumns().add(subtitel);
        tbvw_filme.getItems().addAll(userdata);*/
    }

}
