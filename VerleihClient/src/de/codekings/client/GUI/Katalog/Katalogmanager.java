/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.codekings.client.GUI.Katalog;

import de.codekings.client.connection.ClientThread;
import de.codekings.client.connection.MessageReturn;
import de.codekings.client.main.Control;
import de.codekings.common.Connection.Message;
import de.codekings.common.datacontents.Cover;
import de.codekings.common.datacontents.Film;
import de.codekings.common.datacontents.Sendable;
import java.io.IOException;
import java.util.ArrayList;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Orientation;
import javafx.scene.Parent;
import javafx.scene.control.Separator;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

/**
 *
 * @author Jan
 */
public class Katalogmanager implements MessageReturn {

    private ArrayList<Katalogeintrag> li_eintraege;
    private boolean contentready = false;
    private VBox content = new VBox();

    public Katalogmanager() {
        li_eintraege = new ArrayList<>();
        Control c = Control.getControl();
        String host = c.getCfgManager().getConfigs().getProperty("ip");
        int port = Integer.parseInt(c.getCfgManager().getConfigs().getProperty("standardport"));

        ClientThread clth = new ClientThread(this, host, port, c.getKrypter());

        Message m = new Message("getFilms");
        clth.requestToServer(m, false);
    }

    @Override
    public void returnedMessage(Message m) {
        System.out.println(m.getCommand());
        if (m.getCommand().equalsIgnoreCase("returnFilms")) {
            ArrayList<Film> filme = new ArrayList();

            m.getContent().stream().filter((s) -> (s instanceof Film)).forEach((s) -> {
                filme.add((Film) s);
            });
            VBox vboxliste = new VBox();
            for (Film f : filme) {
                Pane pa = null;
                try {
                    FXMLLoader fxmlLoader = new FXMLLoader();
                    pa = fxmlLoader.load(getClass().getClassLoader().getResource("de/codekings/client/GUI/Katalog/katalog_item.fxml").openStream());

                    Katalog_itemController kic = (Katalog_itemController) fxmlLoader.getController();
                    kic.setTitel(f.getS_titel());
                    //kic.setCover((Image) f.getCoverImage());
                    //TODO Cover!!
                    kic.setDescription(f.getS_description());
                    //kic.setJahr("" + f.getRelease_date().getYear());
                    kic.setLaufzeit("" + f.getI_duration() + " min");
                    kic.setSubtitle(f.getS_subtitel());

                    Katalogeintrag k = new Katalogeintrag(pa, kic, f);
                    addEintrag(k);

                    vboxliste.getChildren().add(pa);
                    //Separator sep = new Separator(Orientation.HORIZONTAL);
                    //sep.setBorder(Border.);
                    //sep.setId("katalog-seperator");

                    //vboxliste.getChildren().add(sep);
                } catch (IOException e) {
                    System.out.println(e.getCause());
                }
            }
            content = vboxliste;
            contentready = true;

            Control c = Control.getControl();
            String host = c.getCfgManager().getConfigs().getProperty("ip");
            int port = Integer.parseInt(c.getCfgManager().getConfigs().getProperty("standardport"));

            for (Katalogeintrag ke : li_eintraege) {
                ClientThread getCoverThread = new ClientThread(this, host, port, c.getKrypter());
                Message covermessage = new Message("getCover");
                covermessage.addAdditionalParameter("FILM_ID", String.valueOf(ke.getFilm().getFILMID()));
                getCoverThread.requestToServer(covermessage, false);
            }

        }
        if (m.getCommand().equalsIgnoreCase("returnCover")) {
            Cover c = null;
            for (Sendable s : m.getContent()) {
                if (s instanceof Cover) {
                    c = (Cover) s;
                }
            }
            if (c != null) {
                if (!c.getCover().equals("")) {
                    for (Katalogeintrag k : li_eintraege) {
                        try {
                            if (k.getFilm().getFILMID() == c.getFilm_id()) {
                                k.getContentcontroller().setCover(c.gibCoverImage());
                            }
                        } catch (Exception e) {
                            System.out.println(e.getMessage());
                        }
                    }
                }
            }
        }
    }

    public boolean waitForContent() {
        return !contentready;
    }

    public void addEintrag(Katalogeintrag e) {
        if (!li_eintraege.contains(e)) {
            li_eintraege.add(e);
        }
    }

    public void removeEintrag(Katalogeintrag e) {
        if (li_eintraege.contains(e)) {
            li_eintraege.remove(e);
        }
    }

    public void removeEintrag(int index) {
        if (index >= 0 && index < li_eintraege.size()) {
            li_eintraege.remove(index);
        }
    }

    public Katalogeintrag getEintrag(int index) {
        if (index >= 0 && index < li_eintraege.size()) {
            return li_eintraege.get(index);
        } else {
            return null;
        }
    }

    public Parent getContentView() {
        return content;
    }

}
