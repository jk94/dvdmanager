/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.codekings.client.Controls;

import de.codekings.client.GUI.Login.LoginSession;
import de.codekings.client.connection.ClientThread;
import de.codekings.client.connection.MessageReturn;
import de.codekings.common.Connection.Krypter;
import de.codekings.common.Connection.Message;
import de.codekings.common.config.ConfigManager;
import de.codekings.common.datacontents.SendablePublicKey;
import java.io.File;
import java.io.FileNotFoundException;

/**
 *
 * @author Jan
 */
public class Control implements MessageReturn {

    private ConfigManager cfgManager;
    private Krypter krypter;
    private ContentControl contManager;
    private static Control control;
    private boolean pubKeyEmpfangen = false;
    private LoginSession session = new LoginSession("", "");

    public Control() {
        loadConfig();
        loadKrypter();
        loadContentControl();
        //getPublicKey();

    }

    public final void loadConfig() {
        try {
            File config = new File("./config/client.cfg");

            cfgManager = new ConfigManager(config);

            /*new ClientThread(null).HeartBeat(cfgManager.getConfigs().getProperty("ip"),
             Integer.parseInt(cfgManager.getConfigs().getProperty("standardport")));*/
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
        if (m.getCommand().equalsIgnoreCase("returnPublicKey")) {
            m.getContent().stream().filter((s) -> (s instanceof SendablePublicKey)).map((s) -> (SendablePublicKey) s).forEach((spk) -> {
                krypter.setForeignPublicKey(spk.generatePublicKey());
            });
        }
        if (m.getCommand().equalsIgnoreCase("returnlogin")) {
            boolean success = Boolean.getBoolean(m.getAdditionalparameter().get("success"));

        }
    }

    public final void loadKrypter() {
        krypter = new Krypter();
        if (cfgManager.getConfigs().containsKey("generatenewkeys")) {
            boolean generate = Boolean.parseBoolean(cfgManager.getConfigs().getProperty("generatenewkeys"));
            if (generate) {
                krypter.generateKeyPair();
            } else {
                if (!krypter.loadKeyPair()) {
                    System.out.println("Laden der KeyPairs fehlgeschlagen. Erzeuge neue Keys!");
                    krypter.generateKeyPair();
                }
            }
        }

    }

    public ConfigManager getCfgManager() {
        return cfgManager;
    }

    public Krypter getKrypter() {
        return krypter;
    }

    public static final void setControl(Control c) {
        control = c;
    }

    public static final Control getControl() {
        return control;
    }

    public final boolean requestPublicKey() {
        String host = cfgManager.getConfigs().getProperty("ip");
        int port = Integer.parseInt(cfgManager.getConfigs().getProperty("standardport"));

        Message pkrequest = new Message("getPublicKey");
        ClientThread ct = new ClientThread(this, host, port, krypter);

        pubKeyEmpfangen = false;
        ct.requestToServer(pkrequest, false);

        int counter = 0;
        while (counter < 10 || !pubKeyEmpfangen) {
            try {
                counter++;
                Thread.sleep(1000l);
            } catch (InterruptedException ex) {
                System.out.println(ex.getMessage());
            }
        }
        return pubKeyEmpfangen;
    }

    public final void loadContentControl() {
        this.contManager = new ContentControl();
    }

    public final ContentControl getContentControl() {
        return this.contManager;
    }

    public boolean login(String email, String passwort) {
        String host = cfgManager.getConfigs().getProperty("ip");
        int port = Integer.parseInt(cfgManager.getConfigs().getProperty("secureport"));

        Message loginrequest = new Message("login");
        loginrequest.addAdditionalParameter("email", email);
        loginrequest.addAdditionalParameter("passwort", passwort);
        int i = 1;
        if (i > 0) {
            return false;
        }
        ClientThread loginsession;
        if (krypter.hasForeignPublicKey()) {

            loginsession = new ClientThread(this, host, port, krypter);
            loginsession.requestToServer(loginrequest, true);

            //return true;
        } else {
            if (requestPublicKey()) {
                loginsession = new ClientThread(this, host, port, krypter);
                loginsession.requestToServer(loginrequest, true);
            } else {

            }
            return false;

        }

        return false;
    }
}
