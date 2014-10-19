/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.codekings.client.Controls;

import de.codekings.client.datacontent.Film_Client;
import java.util.ArrayList;

/**
 *
 * @author Jan
 */
public class DataManager {

    ArrayList<Film_Client> li_filme = new ArrayList<>();
    private final int updateTime;
    private Control control;

    public DataManager(Control c) {
        if (c.getCfgManager().getConfigs().containsKey("updateTime")) {
            this.updateTime = Integer.parseInt(c.getCfgManager().getConfigs().getProperty("updateTime"));
        } else {
            updateTime = 600000;
        }
    }

}
