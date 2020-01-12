package at.michaelkoenig.labor05.labor_05.uebung2;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

/**
 * Das Programm dient zur Demontsration der pixelweisen Bildverarbeitung
 * mit Hilfe eines BufferedImage und eines WritableRaster.
 * @author Mag. Otto Reichel
 */
public class ConvertPixel {

  public static void main(String[] args) {
    try {
      // JPG laden
      Image img = new ImageIcon(ImageIO.read(new File("pic_2.jpg"))).getImage();

      // Höhe und Breite des Bildes bestimmen
      int w = img.getWidth(null);
      int h = img.getHeight(null);

      // Abmessungen ausgeben
      System.out.format("[%d/%d]\n", w, h);

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
      for (int x = 0; x < w; x++) {
        //try { Thread.sleep(1); } catch(Exception ex) {}
        for (int y = 0; y < h; y++) {
          data = wr.getPixel(x, y, data);
          gray = 0;
          // Mittelwert der RGB-Werte ermitteln
          for (int c : data) {
            gray += c;
          }
          gray /= data.length;
          for(int i = 0; i < data.length; i++) {
            data[i] = gray;
          }
          wr.setPixel(x, y, data);
        }
      }
      Instant stop = Instant.now();
      System.out.println("Duration: " + Duration.between(start,stop));
      

      // Buffered Image in neue Datei schreiben
      ImageIO.write(bImg, "jpeg", new File("pic_2_gray.jpg"));

    } catch (IOException e) {
      System.err.println("Error: " + e.getMessage());
    }

  }
}
