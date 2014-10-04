package de.codekings.client.Controls;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.Pane;
import de.codekings.client.Enum.ContentPageType;
import static de.codekings.client.Enum.ContentPageType.BACK;
import static de.codekings.client.Enum.ContentPageType.FORWARD;
import javafx.scene.control.ScrollPane;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Jan
 */
public class ContentManager {

    private static Verlaufsmanager verlManager;

    public ContentManager() {
        Verlaufsmanager v = new Verlaufsmanager();
        setVerlaufsManager(v);
    }

    public static void setVerlaufsManager(Verlaufsmanager vlm) {
        verlManager = vlm;
    }

    public static Verlaufsmanager getVerlaufsManager() {
        return verlManager;
    }

    public boolean changeContent(ScrollPane contpane, ContentPageType t, boolean fwORbw) {
        if (verlManager.getPage() != null) {
            if (verlManager.getPage().equals(t)) {
                return false;
            }
        }
        Parent p = null;
        switch (t) {
            case Katalog_Start:

                break;
            case Katalog_Titelsuche:
                break;

            case Kunde_Start:
                try {
                    p = FXMLLoader.load(getClass().getClassLoader().getResource("de/codekings/client/GUI/Kunden/kunde_start.fxml"));
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
                break;
            case Kunde_Ausleihen:
                try {
                    p = FXMLLoader.load(getClass().getClassLoader().getResource("de/codekings/client/GUI/Kunden/kunde_ausleihen.fxml"));
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
                break;
            case Kunde_EntlieheneArt:
                try {
                    p = FXMLLoader.load(getClass().getClassLoader().getResource("de/codekings/client/GUI/Kunden/kunde_entliehen.fxml"));
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
                break;
            case Kunde_Verwaltung:
                try {
                    p = FXMLLoader.load(getClass().getClassLoader().getResource("de/codekings/client/GUI/Kunden/kunde_kontoverwaltung.fxml"));
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
                break;
            case MA_KundeRegistrieren:
                try {
                    p = FXMLLoader.load(getClass().getClassLoader().getResource("de/codekings/client/GUI/Mitarbeiter/create_user.fxml"));
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
                break;
            case MA_CreateFilm:
                try {
                    p = FXMLLoader.load(getClass().getClassLoader().getResource("de/codekings/client/GUI/Mitarbeiter/create_film.fxml"));
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
                break;
            case BACK:
                if (verlManager.canBack()) {
                    changeContent(contpane, verlManager.backPage(), true);
                    verlManager.decreaseIndex();
                    return true;
                }
                break;
            case FORWARD:
                if (verlManager.canNext()) {
                    changeContent(contpane, verlManager.nextPage(t), true);
                    verlManager.increaseIndex();
                    return true;
                }
                break;
            default:
        }
        if (!t.equals(BACK) && !t.equals(FORWARD) && !fwORbw) {
            verlManager.addPage(t);
        }
        contpane.setContent(p);
        return true;
    }

}
