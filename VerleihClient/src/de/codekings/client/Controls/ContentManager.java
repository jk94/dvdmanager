package de.codekings.client.Controls;

import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.Pane;
import de.codekings.client.Enum.ContentPageType;

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

    private Pane contentPane;

    public ContentManager(Pane cp) {
        this.contentPane = cp;
    }

    public void changeContent(ContentPageType t) {
        Parent p = null;
        switch (t) {
            case Katalog_Start:
                break;
            case Katalog_Titelsuche:
                break;
            case MA_KundeRegistrieren:
                try {
                    p = FXMLLoader.load(getClass().getResource("../GUI/MainFrame/create_user.fxml"));
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
                break;
        }
        contentPane.getChildren().clear();
        contentPane.getChildren().add(p);
    }

}
