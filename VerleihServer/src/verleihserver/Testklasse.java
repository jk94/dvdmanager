package verleihserver;

import de.jan.common.json.json_parser;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.filechooser.FileSystemView;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Jan
 */
public class Testklasse {

    String a = "C:\\Users\\Jan\\Pictures\\PiConnect\\Prinzip1.png";
    int b = 1;
    char c = 'c';
    double d = 25.14;
    //BufferedImage img = null;
    //byte[] imgb;
    bild bil = new bild(new File("C:\\Users\\Jan\\Pictures\\2014-08-18 16_04_33-Fortschritt - DerQuaelgeist - Battlelog _ Battlefield 3.png"));

    private class bild {

        String type, imageAsString;

        public bild(File f) {
            try {
                BufferedImage b = ImageIO.read(f);
                FileSystemView view = FileSystemView.getFileSystemView();
                type = view.getSystemTypeDescription(f);
                type = "png";
                System.out.println(type);
                imageAsString = json_parser.encodeToString(b, type);
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
        }

    }

    public Testklasse() {
        
    }

    public final byte[] extractBytes(String ImageName) throws IOException {
        // open image
        File imgPath = new File(ImageName);
        BufferedImage bufferedImage = ImageIO.read(imgPath);

        // get DataBufferBytes from Raster
        WritableRaster raster = bufferedImage.getRaster();
        DataBufferByte data = (DataBufferByte) raster.getDataBuffer();

        return (data.getData());
    }

    public String getA() {
        return a;
    }

    public void setA(String a) {
        this.a = a;
    }

    public int getB() {
        return b;
    }

    public void setB(int b) {
        this.b = b;
    }

    public char getC() {
        return c;
    }

    public void setC(char c) {
        this.c = c;
    }

    public double getD() {
        return d;
    }

    public void setD(double d) {
        this.d = d;
    }

}
