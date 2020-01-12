/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.michaelkoenig.labor05.labor_05.uebung1;

import java.util.concurrent.RecursiveTask;

/**
 *
 * @author 20160451
 */
public class Binomialkoeffizient extends RecursiveTask<Long> {

    private final int n;
    private final int k;

    public Binomialkoeffizient(int n, int k) {
        this.n = n;
        this.k = k;
    }

    @Override
    protected Long compute() {
        if (this.k == 0 || n == k) {
            return 1L;
        }

        Binomialkoeffizient left = new Binomialkoeffizient(n - 1, k - 1);
        Binomialkoeffizient right = new Binomialkoeffizient(n - 1, k);

        left.fork();
        return right.invoke() + left.join();
    }

}
