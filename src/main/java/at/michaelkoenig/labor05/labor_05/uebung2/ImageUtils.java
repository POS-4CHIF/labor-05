/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.michaelkoenig.labor05.labor_05.uebung2;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.ForkJoinPool;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

/**
 *
 * @author 20160451
 */
public class ImageUtils {

    public static void toBlackWhite(String srcFile, String destFile, int threshold, int parallelism) {
        try {
            // JPG laden
            Image img = new ImageIcon(ImageIO.read(new File(srcFile))).getImage();

            // Höhe und Breite des Bildes bestimmen
            int w = img.getWidth(null);
            int h = img.getHeight(null);

            // Abmessungen ausgeben
            System.out.format("%s [%d/%d]\n", destFile, w, h);

            // BufferedImage anlegen
            BufferedImage bImg = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
            // Bild in das BufferedImage zeichnen
            Graphics g = bImg.getGraphics();
            g.drawImage(img, 0, 0, null);
            g.dispose();

            // Writeable Raster auf das BufferedImage besorgen
            WritableRaster wr = bImg.getRaster();
            // Dient zum Speichern der RGB-Werte eines Pixels
            int data[] = null;
            int gray;
            // Writabe Raster Pixelweise abarbeiten
            Instant start = Instant.now();

            ForkJoinPool pool = new ForkJoinPool(parallelism);
            pool.invoke(new BlackWhiteAction(wr, 0, threshold));
            pool.shutdown();

            Instant stop = Instant.now();
            System.out.println("Duration: " + Duration.between(start, stop).toMillis() + "ms");

            // Buffered Image in neue Datei schreiben
            ImageIO.write(bImg, "jpeg", new File(destFile));

        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    public static void toBlackWhite(String srcFile, String destFile, int threshold) {
        toBlackWhite(srcFile, destFile, threshold, Runtime.getRuntime().availableProcessors());
    }

    public static void toErosion(String srcFile, String destFile, int k, int parallelism) {
        try {
            // JPG laden
            Image img = new ImageIcon(ImageIO.read(new File(srcFile))).getImage();

            // Höhe und Breite des Bildes bestimmen
            int w = img.getWidth(null);
            int h = img.getHeight(null);

            // Abmessungen ausgeben
            System.out.format("%s [%d/%d]\n", destFile, w, h);

            // BufferedImage anlegen
            BufferedImage bImg = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
            BufferedImage bImgOriginal = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
            // Bild in das BufferedImage zeichnen
            Graphics g = bImg.getGraphics();
            g.drawImage(img, 0, 0, null);
            g.dispose();

            g = bImgOriginal.getGraphics();
            g.drawImage(img, 0, 0, null);
            g.dispose();

            // Writeable Raster auf das BufferedImage besorgen
            WritableRaster wr = bImg.getRaster();
            Raster original = bImgOriginal.getRaster();
            // Dient zum Speichern der RGB-Werte eines Pixels
            int data[] = null;
            int gray;
            // Writabe Raster Pixelweise abarbeiten
            Instant start = Instant.now();

            ForkJoinPool pool = new ForkJoinPool(parallelism);
            pool.invoke(new ErosionAction(original, wr, 0, k));
            pool.shutdown();

            Instant stop = Instant.now();
            System.out.println("Duration: " + Duration.between(start, stop).toMillis() + "ms");

            // Buffered Image in neue Datei schreiben
            ImageIO.write(bImg, "jpeg", new File(destFile));

        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }


    public static void toErosion(String srcFile, String destFile, int threshold) {
        toBlackWhite(srcFile, destFile, threshold, Runtime.getRuntime().availableProcessors());
    }

}
