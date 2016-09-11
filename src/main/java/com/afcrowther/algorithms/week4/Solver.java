package com.afcrowther.algorithms.week4;

import java.util.LinkedList;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;

public class Solver {

  private final MinPQ<SearchNode> queue;
  private final MinPQ<SearchNode> twinQueue;
  private SearchNode finalNode;
  private int moves;
  private LinkedList<Board> solution;
  private boolean solvable;

  public Solver(Board initial) {
    if (initial == null) {
      throw new NullPointerException();
    }

    moves = -1;
    finalNode = null;
    solvable = false;
    solution = null;

    queue = new MinPQ<SearchNode>();
    twinQueue = new MinPQ<SearchNode>();
    // create initial search node, and add it to the main queue
    queue.insert(new SearchNode(0, null, initial));
    // create twin node and add to the twinQueue
    twinQueue.insert(new SearchNode(0, null, initial.twin()));

    runGame();
  }

  public boolean isSolvable() {
    return solvable;
  }

  public int moves() {
    return moves;
  }

  public Iterable<Board> solution() {
    if (!isSolvable()) {
      return null;
    } else {
      return solution;
    }
  }

  private void runGame() {
    while (finalNode == null) {
      // do loop for main node
      doTurn(queue, true);

      // do loop for twin node
      doTurn(twinQueue, false);
    }
    // check if solution is in main queue
    if (solvable) {
      solution = new LinkedList<Board>();
      moves = finalNode.moves;
      SearchNode previous = finalNode;

      while (previous != null) {
        solution.addFirst(previous.board);
        previous = previous.previous;
      }
    }
  }

  private void doTurn(MinPQ<SearchNode> minQueue, boolean main) {
    SearchNode nextNode = minQueue.delMin();
    SearchNode previousNode = nextNode.previous;

    Board board = nextNode.board;
    Board prev = null;
    if (previousNode != null) {
      prev = previousNode.board;
    }
    if (!board.isGoal()) {
      Iterable<Board> neighbors = board.neighbors();
      for (Board neighbor : neighbors) {
        if (!neighbor.equals(prev)) {
          minQueue.insert(new SearchNode(nextNode.moves + 1, nextNode, neighbor));
        }
      }
    } else {
      solvable = main;
      finalNode = nextNode;
    }
  }

  private class SearchNode implements Comparable<SearchNode> {

    private final int moves;
    private final int priority;
    private final SearchNode previous;
    private final Board board;

    public SearchNode(int moves, SearchNode previous, Board board) {
      this.moves = moves;
      this.previous = previous;
      this.board = board;
      this.priority = moves + board.manhattan();
    }

    @Override
    public int compareTo(SearchNode o) {
      if (priority > o.priority) {
        return 1;
      } else if (priority < o.priority) {
        return -1;
      } else {
        return 0;
      }
    }
  }

  public static void main(String[] args) {
    // create initial board from file
    In in = new In(args[0]);
    int n = in.readInt();
    int[][] blocks = new int[n][n];
    for (int i = 0; i < n; i++)
      for (int j = 0; j < n; j++)
        blocks[i][j] = in.readInt();
    Board initial = new Board(blocks);

    // solve the puzzle
    Solver solver = new Solver(initial);

    // print solution to standard output
    if (!solver.isSolvable())
      StdOut.println("No solution possible");
    else {
      StdOut.println("Minimum number of moves = " + solver.moves());
      for (Board board : solver.solution())
        StdOut.println(board);
    }
  }
}
