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
import de.codekings.common.datacontents.Film;
import de.codekings.common.datacontents.Sendable;
import java.io.IOException;
import java.util.ArrayList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

/**
 *
 * @author Jan
 */
public class Katalogmanager implements MessageReturn {

    private ArrayList<Katalogeintrag> li_eintraege;
    private boolean contentready = false;

    public Katalogmanager() {
        li_eintraege = new ArrayList<>();
        Control c = Control.getControl();
        String host = c.getCfgManager().getConfigs().getProperty("ip");
        int port = Integer.parseInt(c.getCfgManager().getConfigs().getProperty("standardport"));

        ClientThread clth = new ClientThread(this, host, port, c.getKrypter());

        Message m = new Message("getFilms");
        m.addAdditionalParameter("startindex", "0");
        m.addAdditionalParameter("count", "15");
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
                    kic.setJahr("" + f.getRelease_date().getYear());
                    kic.setLaufzeit("" + f.getI_duration() + " min");
                    kic.setSubtitle(f.getS_subtitel());

                    Katalogeintrag k = new Katalogeintrag(pa, kic, f);
                    addEintrag(k);

                    vboxliste.getChildren().add(pa);
                } catch (IOException e) {
                    System.out.println(e.getCause());
                }
            }
            contentready = true;
        }
    }
    
    public boolean waitForContent(){
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
        VBox p = new VBox();
        for (int i = 1; i < 5; i++) {
            Pane pa = null;
            try {
                FXMLLoader fxmlLoader = new FXMLLoader();
                pa = fxmlLoader.load(getClass().getClassLoader().getResource("de/codekings/client/GUI/Katalog/katalog_item.fxml").openStream());

                Katalog_itemController kic = (Katalog_itemController) fxmlLoader.getController();
                kic.setTitel("Crank " + i);
                kic.setCover(new Image(CoverFlowAnzeige.class.getResource("cover" + i + ".png").toExternalForm(), false));
                if (i % 2 == 0) {
                    kic.setVerfuegbar(true);
                } else {
                    kic.setVerfuegbar(false);
                }
            } catch (IOException e) {
                System.out.println(e.getCause());
            }
            p.getChildren().add(pa);

        }
        p.autosize();

        return p;
    }

}
