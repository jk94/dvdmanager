package verleihserver;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

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

    String a = "C:/Users/Jan/Pictures/mc/maxresdefault.jpg";
    int b = 1;
    char c = 'c';
    double d = 25.14;
    //BufferedImage img = null;
    byte[] imgb;

    public Testklasse() {
        try {
            //img = ImageIO.read(new File(a));
            imgb = extractBytes(new File(a).getAbsolutePath());
            System.out.println(imgb.length);
            /*for(int i=0; i<imgb.length;i++){
                System.out.println(imgb[i]);
            }*/
        } catch (IOException e) {
        }
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

    public byte[] getImgb() {
        return imgb;
    }

    public void setImgb(byte[] imgb) {
        this.imgb = imgb;
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
