/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controls;

import de.jan.common.contents.Film;
import de.jan.common.json.json_parser;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 *
 * @author Jan
 */
public class Control {

    private static final Control theControl = new Control();

    public Control() {
        //getFilms();
    }

    public static Control getInstance() {
        return theControl;
    }

    public final void getFilms() {
        ArrayList<Film> films = new ArrayList<>();
        ResultSet rs = DBSQL_Control.GetALLFilmsFromDB();
        try {

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
                f.loadFilmFromDatabase(rs);
            }
            System.out.println(json_parser.getInstance().parseObjectOut(films));
            rs.close();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        
    }
}
