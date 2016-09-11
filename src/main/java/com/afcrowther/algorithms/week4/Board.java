package com.afcrowther.algorithms.week4;

import java.util.Arrays;
import java.util.LinkedList;

/**
 * Immutable datatype to represent the state of an n*n board, having tiles 1,2 .. n^2-1 with 0
 * representing a blank tile
 */
public class Board {

  private final byte dimension;
  private final int[][] board;
  private int blankBlockX;
  private int blankBlockY;
  private final int manhattan;

  public Board(int[][] blocks) {
    if (blocks == null) {
      throw new NullPointerException();
    }
    dimension = (byte) blocks.length;

    blankBlockX = -1;
    blankBlockY = -1;
    manhattan = -1;

    // defensive copy
    board = new int[dimension][dimension];
    for (int i = 0; i < dimension; i++) {
      for (int j = 0; j < dimension; j++) {
        board[i][j] = blocks[i][j];
        if (board[i][j] == 0) {
          blankBlockX = i;
          blankBlockY = j;
        }
      }
    }
  }

  // (where blocks[i][j] = block in row i, column j)
  public int dimension() {
    return dimension;
  }

  /**
   * The number of blocks currently out of place
   */
  public int hamming() {
    int value = 0;
    for (int i = 0; i < dimension; i++) {
      for (int j = 0; j < dimension; j++) {
        if (board[i][j] != coordToTileNumber(i, j) && (i != dimension - 1 || j != dimension - 1)) {
          value++;
        }
      }
    }
    return value;
  }

  public int manhattan() {
    if (manhattan != -1) {
      return manhattan;
    }
    int sum = 0;
    for (int i = 0; i < dimension; i++) {
      for (int j = 0; j < dimension; j++) {
        if (board[i][j] != 0) {
          int requiredX = tileNumberToXCoord(board[i][j]);
          int requiredY = tileNumberToYCoord(board[i][j]);
          sum += Math.abs(i - requiredY) + Math.abs(j - requiredX);
        }
      }
    }
    return sum;
  }

  public boolean isGoal() {
    for (int i = 0; i < dimension; i++) {
      for (int j = 0; j < dimension; j++) {
        int goal = coordToTileNumber(i, j);
        if (goal != board[i][j]) {
          return false;
        }
      }
    }
    return true;
  }

  public Board twin() {
    int[][] newBoard = copyBoard();

    // blank block not on top row
    if (blankBlockX != 0) {
      newBoard[0][1] = board[0][0];
      newBoard[0][0] = board[0][1];
    } else {
      newBoard[1][1] = board[1][0];
      newBoard[1][0] = board[1][1];
    }
    return new Board(newBoard);
  }

  @Override
  public boolean equals(Object y) {
    if (y == null || y.getClass() != getClass()) {
      return false;
    }
    if (y == this) {
      return true;
    }
    Board other = (Board) y;
    return Arrays.deepEquals(board, other.board);
  }

  public Iterable<Board> neighbors() {
    LinkedList<Board> neighbors = new LinkedList<Board>();

    // left
    if (blankBlockX != 0) {
      int[][] neighbor = copyBoard();
      neighbor[blankBlockX - 1][blankBlockY] = board[blankBlockX][blankBlockY];
      neighbor[blankBlockX][blankBlockY] = board[blankBlockX - 1][blankBlockY];
      neighbors.add(new Board(neighbor));
    }
    // right
    if (blankBlockX != dimension - 1) {
      int[][] neighbor = copyBoard();
      neighbor[blankBlockX + 1][blankBlockY] = board[blankBlockX][blankBlockY];
      neighbor[blankBlockX][blankBlockY] = board[blankBlockX + 1][blankBlockY];
      neighbors.add(new Board(neighbor));
    }
    // up
    if (blankBlockY != 0) {
      int[][] neighbor = copyBoard();
      neighbor[blankBlockX][blankBlockY - 1] = board[blankBlockX][blankBlockY];
      neighbor[blankBlockX][blankBlockY] = board[blankBlockX][blankBlockY - 1];
      neighbors.add(new Board(neighbor));
    }
    // down
    if (blankBlockY != dimension - 1) {
      int[][] neighbor = copyBoard();
      neighbor[blankBlockX][blankBlockY + 1] = board[blankBlockX][blankBlockY];
      neighbor[blankBlockX][blankBlockY] = board[blankBlockX][blankBlockY + 1];
      neighbors.add(new Board(neighbor));
    }
    return neighbors;
  }

  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("\n");
    sb.append(String.format("%d\n", dimension));
    for (int i = 0; i < dimension; i++) {
      for (int j = 0; j < dimension; j++) {
        sb.append(String.format(" %d ", board[i][j]));
      }
      sb.append("\n");
    }
    return sb.toString();
  }

  private int coordToTileNumber(int x, int y) {
    return (x == dimension - 1 && y == dimension - 1) ? 0 : (x * dimension + y) + 1;
  }

  private int tileNumberToXCoord(int index) {
    return index == 0 ? dimension - 1 : (index - 1) % dimension;
  }

  private int tileNumberToYCoord(int index) {
    return index == 0 ? dimension - 1 : (index - 1) / dimension;
  }

  private int[][] copyBoard() {
    int[][] newBoard = new int[dimension][dimension];
    for (int i = 0; i < dimension; i++) {
      newBoard[i] = Arrays.copyOf(board[i], dimension);
    }
    return newBoard;
  }

  public static void main(String[] args) {
  }
}