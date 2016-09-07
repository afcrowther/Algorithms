package com.afcrowther.algorithms.week1;

import java.util.Arrays;

import com.afcrowther.algorithms.library.StdRandom;
import com.afcrowther.algorithms.library.StdStats;

public class PercolationStats {

	int n;
	int trials;
	Percolation percolation;
	double[] threshold;
	int totalTiles;

	public PercolationStats(int n, int trials) throws IllegalArgumentException {
		if (n <= 0 || trials <= 0) {
			throw new IllegalArgumentException();
		}

		this.threshold = new double[trials];
		this.n = n;
		this.trials = trials;
		this.totalTiles = n * n;
		if (n == 1) {
			Arrays.fill(this.threshold, 1);
		} else {
			for (int i = 0; i < trials; i++) {
				this.percolation = new Percolation(n);
				int openCount = 0;
				// will always percolate before all tiles on grid are open
				while (!percolation.percolates()) {
					int[] coord = generateNewCoordinates(n, n + 1);
					if (percolation.isOpen(coord[0], coord[1])) {
						continue;
					}
					percolation.open(coord[0], coord[1]);
					openCount++;
				}
				this.threshold[i] = Double.valueOf(openCount) / Double.valueOf(totalTiles);
			}
		}
	}

	public double mean() {
		return StdStats.mean(threshold);
	}

	public double stddev() {
		return StdStats.stddev(threshold);
	}

	public double confidenceLo() {
		return StdStats.mean(threshold) - (1.96 * stddev() / Math.sqrt(trials));
	}

	public double confidenceHi() {
		return StdStats.mean(threshold) + (1.96 * stddev() / Math.sqrt(trials));
	}

	private int[] generateNewCoordinates(int gridWidth, int gridHeight) {
		return new int[] { StdRandom.uniform(1, gridWidth), StdRandom.uniform(1, gridHeight) };
	}

	public void printStats() {
		System.out.println("mean                    = " + mean());
		System.out.println("stddev                  = " + stddev());
		System.out.println("95% confidence interval = " + confidenceLo() + ", " + confidenceHi());
	}

	public static void main(String[] args) {
		PercolationStats stats = new PercolationStats(200, 100);
		stats.printStats();
	}

}
