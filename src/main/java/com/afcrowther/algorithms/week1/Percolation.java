package com.afcrowther.algorithms.week1;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

/**
 * Model a grid, and provide api to open any point of the grid, and check if the grid percolates.
 */
public class Percolation {

  private static final int VIRTUAL_SITES = 2;

  private WeightedQuickUnionUF unionFind;
  private WeightedQuickUnionUF unionFindNoBottomVirtualSite;
  private boolean[] siteOpenStatus;
  private int n;
  private int topVirtualSite;
  private int bottomVirtualSite;
  private int size;

  /**
   * Creates an n*n grid
   */
  public Percolation(int n) {
    if (n <= 0) {
      throw new IllegalArgumentException();
    }
    // index 0 and ${size - 1} will be top and bottom virtual sites
    // respectively
    this.size = n * n + VIRTUAL_SITES;
    this.unionFind = new WeightedQuickUnionUF(size);
    this.unionFindNoBottomVirtualSite = new WeightedQuickUnionUF(size - 1);
    this.siteOpenStatus = new boolean[size];
    this.n = n;
    this.topVirtualSite = 0;
    this.bottomVirtualSite = size - 1;
    this.siteOpenStatus[topVirtualSite] = true;
    this.siteOpenStatus[bottomVirtualSite] = true;

    for (int i = 1; i <= n; i++) {
      int index = getIndexFromRowAndColumn(1, i);
      unionFind.union(topVirtualSite, index);
      unionFindNoBottomVirtualSite.union(topVirtualSite, index);

      index = getIndexFromRowAndColumn(n, i);
      unionFind.union(bottomVirtualSite, index);
    }
  }

  /**
   * Open the site with row i, column j if not already open
   */
  public void open(int i, int j) {
    if (i < 1 || i > n || j < 1 || j > n) {
      throw new IndexOutOfBoundsException();
    }
    int currentSiteIndex = getIndexFromRowAndColumn(i, j);

    if (!siteOpenStatus[currentSiteIndex]) {
      siteOpenStatus[currentSiteIndex] = true;

      // up
      if (i > 1 && isOpen(i - 1, j)) {
        unionFind.union(currentSiteIndex, getIndexFromRowAndColumn(i - 1, j));
        unionFindNoBottomVirtualSite.union(currentSiteIndex, getIndexFromRowAndColumn(i - 1, j));
      }
      // down
      if (i < n && isOpen(i + 1, j)) {
        unionFind.union(currentSiteIndex, getIndexFromRowAndColumn(i + 1, j));
        unionFindNoBottomVirtualSite.union(currentSiteIndex, getIndexFromRowAndColumn(i + 1, j));
      }
      // left
      if (j > 1 && isOpen(i, j - 1)) {
        unionFind.union(currentSiteIndex, getIndexFromRowAndColumn(i, j - 1));
        unionFindNoBottomVirtualSite.union(currentSiteIndex, getIndexFromRowAndColumn(i, j - 1));
      }
      // right
      if (j < n && isOpen(i, j + 1)) {
        unionFind.union(currentSiteIndex, getIndexFromRowAndColumn(i, j + 1));
        unionFindNoBottomVirtualSite.union(currentSiteIndex, getIndexFromRowAndColumn(i, j + 1));
      }
    }
  }

  /**
   * Check if site with row i, column j is already open
   */
  public boolean isOpen(int i, int j) {
    if (i < 1 || i > n || j < 1 || j > n) {
      throw new IndexOutOfBoundsException();
    }

    return siteOpenStatus[getIndexFromRowAndColumn(i, j)];
  }

  /**
   * Check if site with row i, column j is connected to the virtual top site
   */
  public boolean isFull(int i, int j) {
    if (i < 1 || i > n || j < 1 || j > n) {
      throw new IndexOutOfBoundsException();
    }
    int index = getIndexFromRowAndColumn(i, j);
    return (siteOpenStatus[index] && unionFindNoBottomVirtualSite.connected(topVirtualSite, index));
  }

  /**
   * Does the grid percolate (top vitrual site connected to bottom virtual site
   */
  public boolean percolates() {
    return n == 1 ? siteOpenStatus[1] : unionFind.connected(topVirtualSite, bottomVirtualSite);
  }

  /**
   * Get 2d Array Index from given row i, column j
   */
  private int getIndexFromRowAndColumn(int i, int j) {
    return (j - 1) * n + i;
  }
}
