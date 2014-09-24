/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.codekings.common.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import de.codekings.common.log.LogInitialiser;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 *
 * @author Jan
 */
public class JSON_Parser {

    private ObjectMapper om;
    private static final Logger log = Logger.getLogger(JSON_Parser.class.getSimpleName());

    public JSON_Parser() {
        LogInitialiser.initialiseLog(log, "./logs/", "JSON_Parser");
        om = new ObjectMapper();
        om.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
        om.enableDefaultTyping();
    }

    public static BufferedImage decodeToImage(String imageString) {

        BufferedImage image = null;
        byte[] imageByte;
        try {
            BASE64Decoder decoder = new BASE64Decoder();
            imageByte = decoder.decodeBuffer(imageString);
            ByteArrayInputStream bis = new ByteArrayInputStream(imageByte);
            image = ImageIO.read(bis);
            bis.close();
        } catch (Exception e) {
            log.log(Level.SEVERE, e.getMessage());
        }
        return image;
    }

    /**
     * Encode image to string
     *
     * @param image The image to encode
     * @param type jpeg, bmp, ...
     * @return encoded string
     */
    public static String encodeToString(BufferedImage image, String type) {
        String imageString = null;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();

        try {
            ImageIO.write(image, type, bos);
            byte[] imageBytes = bos.toByteArray();

            BASE64Encoder encoder = new BASE64Encoder();
            imageString = encoder.encode(imageBytes);

            bos.close();
        } catch (IOException e) {
            log.log(Level.SEVERE, e.getMessage());
        }
        return imageString;
    }

    /**
     *
     * @param o Objekt das in einen String geparsed werden soll
     * @return Objekt als String
     */
    public String parseObjectToString(Object o) {
        try {
            log.log(Level.INFO, "Parse from String to Object {0}", LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME));
            return om.writeValueAsString(o);
        } catch (JsonProcessingException ex) {
            log.log(Level.SEVERE, ex.getMessage());
        }
        return "";
    }

    /**
     *
     * @param parsingString JASON-String der in ein Objekt geparsed werden soll.
     * @param c Klasse, welche aus String erstellt werden soll.
     * @return gibt das Objekt des Typs von c zurück.
     */
    public Object parseStringToObject(String parsingString, Class<?> c) {
        try {
            log.log(Level.INFO, "Parse from String to Object {0}", LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME));
            return om.readValue(parsingString, c);
        } catch (IOException ex) {
            log.log(Level.SEVERE, ex.getMessage());
        }
        log.log(Level.INFO, "Parsing ended");
        return null;
    }

}
