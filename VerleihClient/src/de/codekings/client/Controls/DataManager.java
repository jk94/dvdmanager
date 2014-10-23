/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.codekings.client.Controls;

import de.codekings.client.GUI.Login.LoginFormController;
import de.codekings.client.connection.ClientThread;
import de.codekings.client.connection.MessageReturn;
import de.codekings.client.datacontent.Cover_Client;
import de.codekings.client.datacontent.Film_Client;
import de.codekings.common.Connection.Message;
import de.codekings.common.config.ConfigManager;
import de.codekings.common.datacontents.Cover;
import de.codekings.common.datacontents.Film;
import de.codekings.common.datacontents.Sendable;
import de.codekings.common.json.JSON_Parser;
import java.awt.image.BufferedImage;
import java.awt.image.IndexColorModel;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;

/**
 *
 * @author Jan
 */
public class DataManager implements MessageReturn {

    ArrayList<Film_Client> li_filme = new ArrayList<>();
    private final int updateTime;
    private Control control;
    private final Timer updateTimer;
    private ConfigManager cfgManager;
    private final String host;
    private final int port;

    public DataManager(Control c) {
        this.cfgManager = c.getCfgManager();
        host = cfgManager.getConfigs().getProperty("ip");
        port = Integer.parseInt(cfgManager.getConfigs().getProperty("port"));
        if (cfgManager.getConfigs().containsKey("updateTime")) {
            this.updateTime = Integer.parseInt(c.getCfgManager().getConfigs().getProperty("updateTime"));
        } else {
            updateTime = 600000;
        }
        updateData();
        updateTimer = new Timer();
        updateTimer.schedule(new TimerTask() {

            @Override
            public void run() {
                updateData();
            }
        }, updateTime);

    }

    @Override
    public void returnedMessage(Message m) {
        DataManager instance = this;
        if (m.getCommand().equalsIgnoreCase("returnFilms")) {

            new Thread(new Runnable() {

                @Override
                public void run() {
                    ArrayList<Film_Client> filme = new ArrayList();

                    String pfad = "de/codekings/client/GUI/Elements/loading.gif";
                    InputStream is = LoginFormController.class.getClassLoader().getResourceAsStream(pfad);
                    Image img = new Image(is, 200.0, 250.0, true, true);

                    m.getContent().stream().filter((s) -> (s instanceof Film)).forEach((s) -> {
                        Film f = (Film) s;
                        Film_Client fc = new Film_Client(f, new Cover_Client(f.getFILMID(), img));
                        filme.add(fc);
                        ClientThread getCoverThread = new ClientThread(instance, host, port);
                        Message covermessage = new Message("getCover");
                        covermessage.addAdditionalParameter("FILM_ID", String.valueOf(f.getFILMID()));
                        getCoverThread.requestToServer(covermessage);
                    });
                    li_filme = filme;
                }
            }).start();

        }

        if (m.getCommand().equalsIgnoreCase("returnCover")) {
            new Thread(() -> {
                Cover c = null;
                for (Sendable s : m.getContent()) {
                    if (s instanceof Cover) {
                        c = (Cover) s;
                    }
                }
                if (c != null) {
                    if (!c.getCover().equals("")) {
                        for (Film_Client fi : li_filme) {
                            try {
                                if (fi.getFILMID() == c.getFilm_id()) {
                                    fi.setCover(new Cover_Client(c.getFilm_id(), c.gibCoverImage()));
                                }
                            } catch (Exception e) {
                                System.out.println(e.getMessage());
                            }
                        }
                    } else {
                        //Cover not Found anzeigen
                    }
                } else {
                    //Cover not Found anzeigen
                }
            }).start();
        }
    }

    public final void updateData() {

        //Update Filme
        Message FilmRequest = new Message("getFilms");
        new ClientThread(this, host, port).requestToServer(FilmRequest);
    }

    public ArrayList<Film_Client> getFilme() {
        return li_filme;
    }

}
