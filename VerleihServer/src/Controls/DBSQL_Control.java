/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controls;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Jan
 */
public class DBSQL_Control {

    public static final ResultSet GetALLFilmsFromDB() {
        ResultSet rs = null;

        /*String sql = "SELECT\n"
         + "tbl_film.FI_ID, tbl_film.title, tbl_film.subtitle, tbl_film.\"desc\", tbl_film.rating, tbl_film.cover, tbl_film.trailer,  tbl_film.actor, tbl_film.regie, tbl_film.release_date, tbl_film.duration, tbl_film.awards, tbl_film.preis, tbl_film.last_edit_by, tbl_film.last_edit, tbl_fsk.bez, tbl_fsk.\"alter\", tbl_genre.name AS genrename \n"
         + "FROM tbl_film, tbl_fsk, tbl_genre, tbl_genre_zuordnung\n"
         + "WHERE tbl_film.fsk = tbl_fsk.FSK_ID\n"
         + "AND tbl_genre.GE_ID = tbl_genre_zuordnung.GE_ID\n"
         + "AND tbl_genre_zuordnung.FI_ID = tbl_film.FI_ID\n"
         + "ORDER BY tbl_film.FI_ID ASC";
         */
        /*String sql = "SELECT\n"
                + "tbl_film.FI_ID, tbl_film.title, tbl_film.subtitle, tbl_film.\"desc\", tbl_film.rating, tbl_film.cover, tbl_film.trailer, tbl_film.actor, \n"
                + "tbl_film.regie, tbl_film.release_date, tbl_film.duration, tbl_film.awards, tbl_film.preis, tbl_film.last_edit_by, tbl_film.last_edit, \n"
                + "tbl_fsk.bez, tbl_fsk.\"alter\", tbl_genre.name \n"
                + "FROM tbl_film INNER JOIN tbl_fsk ON tbl_film.fsk = tbl_fsk.FSK_ID ,\n"
                + "tbl_genre INNER JOIN tbl_genre_zuordnung ON tbl_genre_zuordnung.GE_ID = tbl_genre.GE_ID AND tbl_genre_zuordnung.FI_ID = tbl_film.FI_ID";
                */
        String sql = "Select * from tbl_film";
        try {
            //PreparedStatement p = DBController.getConnection().prepareStatement(sql);
            //rs = DBController.executeQuery(p);
            rs = DBController.executeQuery(sql);
        } catch (Exception e) {

        }
        

        return rs;
    }

}
