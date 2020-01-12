/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.michaelkoenig.labor05.labor_05.uebung2;

import at.michaelkoenig.labor05.labor_05.uebung1.Binomialkoeffizient;
import java.awt.image.WritableRaster;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.RecursiveTask;

/**
 *
 * @author 20160451
 */
public class BlackWhiteAction extends RecursiveAction {

    private final WritableRaster wr;
    private final int row;
    private final int threshold;

    public BlackWhiteAction(WritableRaster wr, int row, int threshold) {
        this.wr = wr;
        this.row = row;
        this.threshold = threshold;
    }

    @Override
    protected void compute() {
        BlackWhiteAction forkedAction = null;
        if (row < wr.getHeight() - 1) {
            forkedAction = new BlackWhiteAction(wr, row + 1, threshold);
            forkedAction.fork();
        }

        int data[] = null;
        int gray;

        for (int col = 0; col < wr.getWidth(); col++) {
            data = wr.getPixel(col, row, data);
            gray = 0;
            // Mittelwert der RGB-Werte ermitteln
            for (int c : data) {
                gray += c;
            }
            gray /= data.length;

            gray = gray > threshold ? 255 : 0;

            for (int i = 0; i < data.length; i++) {
                data[i] = gray;
            }
            wr.setPixel(col, row, data);
        }

        if (forkedAction != null) {
            forkedAction.join();
        }

    }

}
