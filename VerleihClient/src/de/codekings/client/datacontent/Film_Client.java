/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.codekings.client.datacontent;

import de.codekings.common.datacontents.Cover;
import de.codekings.common.datacontents.Film;

/**
 *
 * @author Jan
 */
public class Film_Client extends Film {
        private Cover cover;
        public Film_Client(Film f, Cover c) {
            super(f.getFILMID(), f.getRelease_date(), f.getLi_actors(), f.getLi_awards(), f.getGenres(), 
                    f.getS_titel(), f.getS_subtitel(), f.getS_description(), f.getS_trailer(), 
                    f.getS_regie(), f.getS_FSK(), f.getI_rating(), f.getI_duration(), f.getI_fsk(), 
                    f.getD_preis(), f.getClassType());
            this.cover = c;
        }
        
        public Cover getCover(){
            return cover;
        }

    }
