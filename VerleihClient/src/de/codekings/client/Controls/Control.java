/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.codekings.client.Controls;

import de.codekings.client.GUI.Login.LoginSession;
import de.codekings.client.connection.ClientThread;
import de.codekings.client.connection.MessageReturn;
import de.codekings.common.Connection.Message;
import de.codekings.common.config.ConfigManager;
import java.io.File;
import java.io.FileNotFoundException;

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

    public Control() {
        loadConfig();
        loadContentControl();
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

    @Override
    public void returnedMessage(Message m) {
        if (m.getCommand().equalsIgnoreCase("loginresult")) {

            if (m.getAdditionalparameter().get("result").equalsIgnoreCase("success")) {
                String email = m.getAdditionalparameter().get("email");
                String hashedpw = m.getAdditionalparameter().get("passwort");
                int permission = Integer.parseInt(m.getAdditionalparameter().get("permission"));

                class SessionCreater implements Runnable {

                    String email = m.getAdditionalparameter().get("email");
                    String hashedpw = m.getAdditionalparameter().get("passwort");
                    int permission = Integer.parseInt(m.getAdditionalparameter().get("permission"));

                    @Override
                    public void run() {
                        loginResult = true;
                        loginResultEmpfangen = true;
                        session = new LoginSession(email, hashedpw);
                        session.setPermission(permission);
                    }

                }
                loginResult = true;
                loginResultEmpfangen = true;
                session = new LoginSession(email, hashedpw);
                session.setPermission(permission);
                //Platform.runLater(new SessionCreater());
            } else {
                loginResultEmpfangen = true;
            }
        }
    }

    public ConfigManager getCfgManager() {
        return cfgManager;
    }

    public static final void setControl(Control c) {
        control = c;
    }

    public static final Control getControl() {
        return control;
    }

    public final void loadContentControl() {
        this.contManager = new ContentControl();
    }

    public final ContentControl getContentControl() {
        return this.contManager;
    }

    public boolean login(String email, String passwort) {
        String host = cfgManager.getConfigs().getProperty("ip");
        int port = Integer.parseInt(cfgManager.getConfigs().getProperty("port"));

        Message loginrequest = new Message("login");
        loginrequest.addAdditionalParameter("email", email);
        loginrequest.addAdditionalParameter("passwort", passwort);

        ClientThread loginsession = new ClientThread(this, host, port);
        loginsession.requestToServer(loginrequest, false);

        int counter = 0;
        while (counter < 10 && !loginResultEmpfangen)
        {
            counter++;
            try {
                Thread.sleep(500l);
            } catch (InterruptedException e) {
            }
        }
        return loginResult;
    }
}
