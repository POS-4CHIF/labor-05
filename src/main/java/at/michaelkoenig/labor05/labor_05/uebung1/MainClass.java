/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.michaelkoenig.labor05.labor_05.uebung1;

import java.util.concurrent.ForkJoinPool;

/**
 *
 * @author 20160451
 */
public class MainClass {

    public static void main(String[] args) {
        ForkJoinPool pool = new ForkJoinPool();
        System.out.println(pool.invoke(new Binomialkoeffizient(45, 6)));
        pool.shutdown();
    }
}
