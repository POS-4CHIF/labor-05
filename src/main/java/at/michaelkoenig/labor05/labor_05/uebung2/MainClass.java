/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.michaelkoenig.labor05.labor_05.uebung2;

/**
 *
 * @author 20160451
 */
public class MainClass {

    public static void main(String[] args) {
        String srcFile = "htl";
        String srcFileBw = "htl_bw_100";
        for (int threshold : new int[]{60, 80, 100, 120}) {
            ImageUtils.toBlackWhite(srcFile + ".jpg", srcFile + "_bw_" + threshold + ".jpg", threshold);
            ImageUtils.toBlackWhite(srcFile + ".jpg", srcFile + "_bw_" + threshold + ".jpg", threshold, 1); // run with 1 thread
            System.out.println("========================");
        }

        for (int k : new int[]{6,7,8,9}) {
            ImageUtils.toErosion(srcFile + "_bw_100.jpg", srcFile + "_er_" + k + ".jpg", k);
            ImageUtils.toErosion(srcFile + "_bw_100.jpg", srcFile + "_er_" + k + ".jpg", k, 1); // run with 1 thread
            System.out.println("========================");
        }

    }
}
