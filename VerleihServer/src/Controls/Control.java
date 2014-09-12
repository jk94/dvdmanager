/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controls;

import Enumerators.LogEnum;
import contents.Film;
import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import verleihserver.main;

/**
 *
 * @author Jan
 */
public class Control {

    private static final Control theControl = new Control();

    public Control() {

    }

    public void getFilms() {
        try {
            ArrayList<Film> films = new ArrayList<>();
            ResultSet rs = DBSQL_Control.GetALLFilmsFromDB();
            while (rs.next()) {
                Film f = new Film(rs.getInt("FI_ID"));
                boolean vorhanden = false;
                for (Film fi : films) {
                    if (fi.getFILMID() == f.getFILMID()) {
                        vorhanden = true;
                        f = fi;
                        break;
                    }
                }
                if (vorhanden) {
                    f.addGenre(rs.getString("genrename"));
                    continue;
                }
                films.add(f);
            }
            json.json_parser.getInstance();
        } catch (Exception ex) {

        }
    }
}
