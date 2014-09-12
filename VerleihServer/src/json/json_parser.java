/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package json;

import Enumerators.LogEnum;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;
import verleihserver.Testklasse;
import verleihserver.main;

/**
 *
 * @author Jan
 */
public class json_parser {

    private static final json_parser jp = new json_parser();
    private static ObjectMapper om;

    private json_parser() {
        om = new ObjectMapper();
    }

    public static json_parser getInstance() {
        return jp;
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
            e.printStackTrace();
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
            e.printStackTrace();
        }
        return imageString;
    }

    public void parseObjectOut(Testklasse o) {
        System.out.println("parsing started");
        try {
            System.out.println(om.writeValueAsString(o));
            //om.writeValue(new File("C:/Users/Jan/Desktop/test.txt"), o);
        } catch (Exception ex) {
            main.log(LogEnum.ERROR, ex.getMessage(), getInstance());
        }
        System.out.println("parsing ended");

    }

    public Testklasse parseObjectIn(File f, Class<Testklasse> c) {
        System.out.println("start");
        try {
            return om.readValue(f, c);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        System.out.println("end");
        return null;
    }

}
