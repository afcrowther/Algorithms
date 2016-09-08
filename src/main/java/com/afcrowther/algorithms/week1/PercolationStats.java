package com.afcrowther.algorithms.week1;

import java.util.Arrays;

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {

  private int trials;
  private double[] threshold;
  private int totalTiles;

  public PercolationStats(int n, int trials) {
    if (n <= 0 || trials <= 0) {
      throw new IllegalArgumentException();
    }

    this.threshold = new double[trials];
    this.trials = trials;
    this.totalTiles = n * n;
    if (n == 1) {
      Arrays.fill(this.threshold, 1);
    } else {
      for (int i = 0; i < trials; i++) {
        Percolation percolation = new Percolation(n);
        int openCount = 0;
        // will always percolate before all tiles on grid are open
        while (!percolation.percolates()) {
          int row = StdRandom.uniform(1, n + 1);
          int col = StdRandom.uniform(1, n + 1);
          if (percolation.isOpen(row, col)) {
            continue;
          }
          percolation.open(row, col);
          openCount++;
        }
        this.threshold[i] = Double.valueOf(openCount) / Double.valueOf(totalTiles);
      }
    }
  }

  /**
   * Returns the mean of the threshold
   */
  public double mean() {
    return StdStats.mean(threshold);
  }

  /**
   * Returns the standard deviation of the threshold
   */
  public double stddev() {
    if (trials == 1) {
      return Double.NaN;
    }
    return StdStats.stddev(threshold);
  }

  /**
   * Returns the lo confidence value (95%) of the threshold
   */
  public double confidenceLo() {
    return mean() - (1.96 * stddev() / Math.sqrt(trials));
  }

  /**
   * Returns the hi confidence value (95%) of the threshold
   */
  public double confidenceHi() {
    return mean() + (1.96 * stddev() / Math.sqrt(trials));
  }

  private void printStats() {
    System.out.println("mean                    = " + mean());
    System.out.println("stddev                  = " + stddev());
    System.out.println("95% confidence interval = " + confidenceLo() + ", " + confidenceHi());
  }

  public static void main(String[] args) {
    int n = Integer.parseInt(args[0]);
    int t = Integer.parseInt(args[1]);
    PercolationStats stats = new PercolationStats(n, t);
    stats.printStats();
  }

}
