/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.codekings.client.GUI.Katalog;

import de.codekings.client.GUI.ContentView;
import de.codekings.client.Controls.DataManager;
import de.codekings.client.datacontent.Film_Client;
import java.io.IOException;
import java.util.ArrayList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

/**
 *
 * @author Jan
 */
public class Katalogmanager implements ContentView {

    private ArrayList<Katalogeintrag> li_eintraege;
    private boolean contentready = false;
    private VBox content = new VBox();
    private DataManager datamanager;

    public Katalogmanager(DataManager dmgr) {
        this.datamanager = dmgr;
        li_eintraege = new ArrayList<>();
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

    @Override
    public Parent getContentView() {

        VBox vboxliste = new VBox();

        for (Film_Client f : datamanager.getFilme()) {
            Pane pa = null;
            try {
                FXMLLoader fxmlLoader = new FXMLLoader();
                pa = fxmlLoader.load(getClass().getClassLoader().getResource("de/codekings/client/GUI/Katalog/katalog_item.fxml").openStream());

                Katalog_itemController kic = (Katalog_itemController) fxmlLoader.getController();

                kic.setTitel(f.getS_titel());

                kic.setDescription(f.getS_description());
                //kic.setJahr("" + f.getRelease_date().getYear());
                kic.setLaufzeit("" + f.getI_duration() + " min");
                kic.setSubtitle(f.getS_subtitel());

                kic.setCover(f.getCover().gibCoverImage());

                Katalogeintrag k = new Katalogeintrag(pa, kic, f);
                addEintrag(k);
                kic.loadAfterInit();
                vboxliste.getChildren().add(pa);

            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
        content = vboxliste;
        contentready = true;

        return content;
    }

    public Parent getContentView(String search) {
        VBox vboxliste = new VBox();

        for (Film_Client f : datamanager.getFilme()) {
            Pane pa = null;
            if (f.getS_titel().contains(search)) {
                try {
                    FXMLLoader fxmlLoader = new FXMLLoader();
                    pa = fxmlLoader.load(getClass().getClassLoader().getResource("de/codekings/client/GUI/Katalog/katalog_item.fxml").openStream());

                    Katalog_itemController kic = (Katalog_itemController) fxmlLoader.getController();

                    kic.setTitel(f.getS_titel());

                    kic.setDescription(f.getS_description());
                    //kic.setJahr("" + f.getRelease_date().getYear());
                    kic.setLaufzeit("" + f.getI_duration() + " min");
                    kic.setSubtitle(f.getS_subtitel());

                    kic.setCover(f.getCover().gibCoverImage());

                    Katalogeintrag k = new Katalogeintrag(pa, kic, f);
                    addEintrag(k);
                    kic.loadAfterInit();
                    vboxliste.getChildren().add(pa);

                } catch (IOException e) {
                    System.out.println(e.getMessage());
                }
            }
        }
        if (vboxliste.getChildren().size() < 1) {
            Pane pa = null;
            try {
                    FXMLLoader fxmlLoader = new FXMLLoader();
                    pa = fxmlLoader.load(getClass().getClassLoader().getResource("de/codekings/client/GUI/Katalog/nokatalog_item.fxml").openStream());

                    NoKatalog_itemController kic = (NoKatalog_itemController) fxmlLoader.getController();

                    kic.setSuchbefehl(search);
                    vboxliste.getChildren().add(pa);

                } catch (IOException e) {
                    System.out.println(e.getMessage());
                }
        }
        content = vboxliste;
        contentready = true;

        return content;
    }

}
