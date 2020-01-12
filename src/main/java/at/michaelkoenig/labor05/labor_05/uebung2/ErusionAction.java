package at.michaelkoenig.labor05.labor_05.uebung2;

import java.awt.image.Raster;
import java.awt.image.WritableRaster;
import java.util.concurrent.RecursiveAction;

public class ErusionAction extends RecursiveAction {
    private final WritableRaster wr;
    private final Raster original;
    private final int row;
    private final int k;

    public ErusionAction(Raster original, WritableRaster wr, int row, int k) {
        this.wr = wr;
        this.original = original;
        this.row = row;
        this.k = k;
    }

    @Override
    protected void compute() {
        ErusionAction forkedAction = null;
        if (row < wr.getHeight() - 1) {
            forkedAction = new ErusionAction(original, wr, row + 1, k);
            forkedAction.fork();
        }

        int data[] = null;

        for (int col = 0; col < wr.getWidth(); col++) {
            data = wr.getPixel(col, row, data);
            if (data[0] == 0 && data[1] == 0 && data[2] == 0 && getBlackCount(col, row) > k) {
                wr.setPixel(col, row, data);
            } else {
                data[0] = 255;
                data[1] = 255;
                data[2] = 255;
                wr.setPixel(col, row, data);
            }
        }

        if (forkedAction != null) {
            forkedAction.join();
        }

    }

    private int getBlackCount(int colIdx, int rowIdx) {
        int count = 0;

        int startPosRow = (rowIdx - 1 < 0) ? rowIdx : rowIdx - 1;
        int startPosCol = (colIdx - 1 < 0) ? colIdx : colIdx - 1;
        int endPosRow = (rowIdx + 1 > original.getHeight()) ? rowIdx : rowIdx + 1;
        int endPosCol = (colIdx + 1 > original.getWidth()) ? colIdx : colIdx + 1;


        int data[] = null;
        for (int rowNum = startPosRow; rowNum <= endPosRow; rowNum++) {
            for (int colNum = startPosCol; colNum <= endPosCol; colNum++) {
                try {
                    data = original.getPixel(colNum, rowNum, data);
                    if (data[0] == 0 && data[1] == 0 && data[2] == 0) {
                        count++;
                    }
                } catch (Exception e) {
                    System.err.println("err " + rowIdx + "+" + colIdx + ": " + startPosRow + "," + startPosCol + " " + endPosRow + "," + endPosCol + "   max " + original.getHeight() + "," + original.getWidth());
                }
            }
        }
        return count;
    }

}
