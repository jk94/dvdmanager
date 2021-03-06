/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.codekings.client.Controls;

import de.codekings.client.Enum.ContentPageType;
import de.codekings.client.GUI.Login.LoginFormController;
import de.codekings.client.GUI.Login.LoginSession;
import de.codekings.client.GUI.MainFrame.MainApplication;
import de.codekings.client.GUI.MainFrame.TemplateController;
import de.codekings.client.connection.ClientThread;
import de.codekings.client.connection.MessageReturn;
import de.codekings.common.Connection.Message;
import de.codekings.common.config.ConfigManager;
import java.io.File;
import java.io.FileNotFoundException;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import se.mbaeumer.fxmessagebox.MessageBox;
import se.mbaeumer.fxmessagebox.MessageBoxType;

/**
 *
 * @author Jan
 */
public class Control implements MessageReturn {

    private ConfigManager cfgManager;
    private ContentControl contManager;
    private static Control control;
    private boolean loginResultEmpfangen = false, loginResult = false;
    private LoginSession session = new LoginSession("", "");
    private DataManager datamanager;
    private LoginFormController loginform;

    public Control() {
        loadConfig();

        //Teste Connection
        Message m = new Message("");
        ClientThread ct = new ClientThread(this, cfgManager.getConfigs().getProperty("ip"), Integer.parseInt(cfgManager.getConfigs().getProperty("port")));
        if (ct.isOnline()) {
            loadData();
        } else {
            MessageBox mb = new MessageBox("\nVerbindung zum Server nicht möglich!\n\n"
                    + "Der Server ist vermutlich nicht\n"
                    + "gestartet oder offline.\n\n"
                    + "Bitte stellen Sie sicher, dass der\nServer gestartet und online ist.", MessageBoxType.OK_ONLY);
            mb.setTitle("Warnung!");
            mb.setHeight(250.0);
            mb.setWidth(50.0);
            mb.showAndWait();
            System.exit(0);
        }
    }

    public final void loadConfig() {
        try {
            File config = new File("./config/client.cfg");

            cfgManager = new ConfigManager(config);

        } catch (FileNotFoundException ex) {
            cfgManager = new ConfigManager();
            cfgManager.writeDefaultClientConfig();
            System.err.println("DB Config Datei nicht gefunden.\n"
                    + "Default Nachricht erstellt.\n"
                    + "Bitte Server neu starten!");
            System.exit(0);
        }
    }

    public final void loadData() {
        datamanager = new DataManager(this);
    }

    @Override
    public void returnedMessage(Message m) {
        if (m.getCommand().equalsIgnoreCase("loginresult")) {
            Thread t = new Thread(() -> {
                if (m.getAdditionalparameter().get("result").equalsIgnoreCase("success")) {
                    String email = m.getAdditionalparameter().get("email");
                    String hashedpw = m.getAdditionalparameter().get("passwort");
                    int permission = Integer.parseInt(m.getAdditionalparameter().get("permission"));

                    loginResult = true;
                    loginResultEmpfangen = true;
                    session = new LoginSession(email, hashedpw);
                    session.setPermission(permission);
                } else {
                    loginResultEmpfangen = true;
                }
            });
            t.setPriority(Thread.MAX_PRIORITY);
            t.start();
        }
    }

    public ConfigManager getCfgManager() {
        return cfgManager;
    }

    public DataManager getDataManager() {
        return datamanager;
    }

    public static final void setControl(Control c) {
        control = c;
    }

    public static final Control getControl() {
        return control;
    }

    public void setLoginFormControler(LoginFormController lfc) {
        this.loginform = lfc;
    }

    public final void loadContentControl(TemplateController tpc) {
        this.contManager = new ContentControl(datamanager, tpc);
    }

    public final ContentControl getContentControl() {
        return this.contManager;
    }

    public boolean login(String email, String passwort) {
        loginResult = false;
        loginResultEmpfangen = false;
        session = new LoginSession("", "");

        String host = cfgManager.getConfigs().getProperty("ip");
        int port = Integer.parseInt(cfgManager.getConfigs().getProperty("port"));

        Message loginrequest = new Message("login");
        loginrequest.addAdditionalParameter("email", email);
        loginrequest.addAdditionalParameter("passwort", passwort);

        ClientThread loginsession = new ClientThread(this, host, port);
        loginsession.requestToServer(loginrequest);

        int counter = 0;
        while (counter < 50 && !loginResultEmpfangen) {
            counter++;
            try {
                Thread.sleep(500l);
            } catch (InterruptedException e) {
            }
        }
        return loginResult;
    }

    public void openMainFrame() {
        if (session != null) {
            if (!session.getEmail().equals("")) {
                MainApplication mainframe = new MainApplication();
                Stage s = new Stage();
                s.setMaximized(true);

                try {
                    mainframe.start(s);
                } catch (Exception ex) {
                    System.out.println(ex.getMessage());
                }
                loadContentControl(mainframe.getTemplateController());
                mainframe.getTemplateController().initAfterInit();
                switch (session.getPermission()) {
                    case 1:
                        mainframe.setKundenView();
                        break;
                    case 2:
                        mainframe.setMitarbeiterView();
                        break;
                    case 3:
                        mainframe.setAdminView();
                        break;
                    default:
                        mainframe.setKundenView();
                }
            }
            contManager.changeContent(ContentPageType.Katalog_Start, false);
    }
}

public void MainFrameClosed() {
        session = new LoginSession("", "");
        loginform.reset();
        loginform.getStage().show();
    }

    public LoginSession getSession() {
        return session;
    }
}
