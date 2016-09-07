package com.afcrowther.algorithms.week1;

import java.util.function.BiFunction;

import com.afcrowther.algorithms.library.WeightedQuickUnionUF;

/**
 * Model a grid, and provide api to open any point of the grid, and check if the
 * grid percolates.
 * 
 */
public class Percolation {

	private static final int virtualSites = 2;

	WeightedQuickUnionUF unionFind;
	boolean[] siteOpenStatus;
	int n;
	int topVirtualSite;
	int bottomVirtualSite;
	int size;

	public Percolation(int n) throws IllegalArgumentException {
		if (n <= 0) {
			throw new IllegalArgumentException();
		}
		// index 0 and ${size - 1} will be top and bottom virtual sites
		// respectively
		size = n * n + virtualSites;
		unionFind = new WeightedQuickUnionUF(n * n + virtualSites);
		this.siteOpenStatus = new boolean[size];
		this.n = n;
		this.topVirtualSite = 0;
		this.bottomVirtualSite = size - 1;
	}

	public void open(int i, int j) throws IndexOutOfBoundsException {
		if (i < 1 || i > n || j < 1 || j > n) {
			throw new IndexOutOfBoundsException();
		}

		if (!isOpen(i, j)) {
			int index = getIndexFromCoords(i, j);
			siteOpenStatus[index] = true;

			checkSurroundingTiles(i, j);
		}
	}

	public boolean isOpen(int i, int j) throws IndexOutOfBoundsException {
		if (i < 1 || i > n || j < 1 || j > n) {
			throw new IndexOutOfBoundsException();
		}

		return siteOpenStatus[getIndexFromCoords(i, j)];
	}

	public boolean isFull(int i, int j) throws IndexOutOfBoundsException {
		if (i < 1 || i > n || j < 1 || j > n) {
			throw new IndexOutOfBoundsException();
		}

		return unionFind.connected(topVirtualSite, getIndexFromCoords(i, j));
	}

	public boolean percolates() {
		return unionFind.connected(topVirtualSite, bottomVirtualSite);
	}

	private int getIndexFromCoords(int x, int y) {
		return (x - 1) * n + y;
	}

	private void checkSurroundingTiles(int x, int y) {
		int currentTileIndex = getIndexFromCoords(x, y);
		// above
		if (y == 1) {
			siteOpenStatus[topVirtualSite] = true;
			unionFind.union(currentTileIndex, topVirtualSite);
		} else {
			doOpenIfOtherTileOpen(currentTileIndex, x, y, (X, Y) -> getIndexFromCoords(X, Y - 1));
		}
		// below
		if (y == n) {
			siteOpenStatus[bottomVirtualSite] = true;
			unionFind.union(currentTileIndex, bottomVirtualSite);
		} else {
			doOpenIfOtherTileOpen(currentTileIndex, x, y, (X, Y) -> getIndexFromCoords(X, Y + 1));
		}
		// left
		if (x != 1) {
			doOpenIfOtherTileOpen(currentTileIndex, x, y, (X, Y) -> getIndexFromCoords(X - 1, Y));
		}
		// right
		if (x != n) {
			doOpenIfOtherTileOpen(currentTileIndex, x, y, (X, Y) -> getIndexFromCoords(X + 1, Y));
		}
	}

	private void doOpenIfOtherTileOpen(int tile, int otherTileX, int otherTileY,
			BiFunction<Integer, Integer, Integer> fn) {
		int otherTileIndex = fn.apply(otherTileX, otherTileY);
		if (siteOpenStatus[otherTileIndex])
			unionFind.union(tile, otherTileIndex);
	}
}
