/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.michaelkoenig.labor05.labor_05.uebung3;

/**
 *
 * @author 20160451
 */
public class MainClass {

    public static void main(String[] args) {
        double piProblem = 0.5;
        double piKorrekt = 0.5;
        int n = 6;
        
        for (int i = 0; i < 25; i++) {
            System.out.println("n=" + n + ", pi=" + piProblem*n + ", pi=" + piKorrekt*n);
            piProblem = Math.sqrt(0.5 * (1 - Math.sqrt(1 - piProblem * piProblem)));
            piKorrekt = Math.sqrt(0.5) * piKorrekt / Math.sqrt(1 + Math.sqrt(1 - piKorrekt * piKorrekt));
            n *= 2;
        }
    }
}
