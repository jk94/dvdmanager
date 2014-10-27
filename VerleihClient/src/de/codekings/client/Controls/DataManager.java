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
import de.codekings.common.datacontents.Genre;
import de.codekings.common.datacontents.Sendable;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import javafx.scene.image.Image;

/**
 *
 * @author Jan
 */
public class DataManager implements MessageReturn {

    ArrayList<Film_Client> li_filme = new ArrayList<>();
    ArrayList<Genre> li_genre = new ArrayList<>();
    private final int updateTime;
    private Control control;
    private final Timer updateTimer;
    private ConfigManager cfgManager;
    private final String host;
    private final int port;
    private final TimerTask updateTimerTask = new TimerTask() {

        @Override
        public void run() {
            System.out.println("timer");
            updateData();
            updateTimer.schedule(updateTimerTask, updateTime);
        }
    };

    public DataManager(Control c) {
        this.cfgManager = c.getCfgManager();
        host = cfgManager.getConfigs().getProperty("ip");
        port = Integer.parseInt(cfgManager.getConfigs().getProperty("port"));
        if (cfgManager.getConfigs().containsKey("updateTime")) {
            this.updateTime = Integer.parseInt(c.getCfgManager().getConfigs().getProperty("updateTime"));
        } else {
            updateTime = 600000;
        }
        System.out.println(updateTime);
        updateData();
        updateTimer = new Timer(true);
        //updateTimer.schedule(updateTimerTask, updateTime);
    }

    @Override
    public void returnedMessage(Message m) {
        DataManager instance = this;
        if (m.getCommand().equalsIgnoreCase("returnFilms")) {
            li_filme.clear();
            Thread t = new Thread(new Runnable() {

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
            });
            t.setDaemon(true);
            t.setPriority(Thread.MIN_PRIORITY);
            t.start();
        }

        if (m.getCommand().equalsIgnoreCase("returnCover")) {
            Thread t = new Thread(() -> {
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
            });
            t.setDaemon(true);
            t.setPriority(Thread.MIN_PRIORITY);
            t.start();
        }
        if (m.getCommand().equalsIgnoreCase("returnGenres")) {
            li_genre.clear();
            for (Sendable s : m.getContent()) {
                if (s instanceof Genre) {
                    Genre g = (Genre) s;
                    li_genre.add(g);
                }
            }
        }
    }

    public final void updateData() {
        System.out.println("Update Start");
        //Update Filme
        Message FilmRequest = new Message("getFilms");
        new ClientThread(this, host, port).requestToServer(FilmRequest);

        Message GenreRequest = new Message("getGenres");
        new ClientThread(this, host, port).requestToServer(GenreRequest);
    }

    public ArrayList<Film_Client> getFilme() {
        return li_filme;
    }

    public ArrayList<Genre> getGenres() {
        return li_genre;
    }

}
