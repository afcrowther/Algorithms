package com.afcrowther.algorithms.week3;

import java.util.Comparator;

import edu.princeton.cs.algs4.StdDraw;

/**
 * Class representing a point on a 2d graph
 */
public class Point implements Comparable<Point> {

  private final short x; // x-coordinate of this point
  private final short y; // y-coordinate of this point

  /**
   * Initializes a new point.
   *
   * @param x
   *          the <em>x</em>-coordinate of the point
   * @param y
   *          the <em>y</em>-coordinate of the point
   */
  public Point(int x, int y) {
    this.x = (short) x;
    this.y = (short) y;

  }

  /**
   * Draws this point to standard draw.
   */
  public void draw() {
    StdDraw.point(x, y);
  }

  /**
   * Draws the line segment between this point and the specified point to standard draw.
   *
   * @param that
   *          the other point
   */
  public void drawTo(Point that) {
    StdDraw.line(this.x, this.y, that.x, that.y);
  }

  /**
   * Returns the slope between this point and the specified point. Formally, if the two points are
   * (x0, y0) and (x1, y1), then the slope is (y1 - y0) / (x1 - x0). For completeness, the slope is
   * defined to be +0.0 if the line segment connecting the two points is horizontal;
   * Double.POSITIVE_INFINITY if the line segment is vertical; and Double.NEGATIVE_INFINITY if (x0,
   * y0) and (x1, y1) are equal.
   *
   * @param that
   *          the other point
   * @return the slope between this point and the specified point
   */
  public double slopeTo(Point that) {
    if (that == null) {
      throw new NullPointerException();
    }

    double deltaX = that.x - this.x;
    double deltaY = that.y - this.y;

    if (compareTo(that) == 0) {
      return Double.NEGATIVE_INFINITY;
    } else if (this.y == that.y) {
      return 0.0d;
    } else if (this.x == that.x) {
      return Double.POSITIVE_INFINITY;
    }
    return deltaY / deltaX;
  }

  /**
   * Compares two points by y-coordinate, breaking ties by x-coordinate. Formally, the invoking
   * point (x0, y0) is less than the argument point (x1, y1) if and only if either y0 < y1 or if y0
   * = y1 and x0 < x1.
   *
   * @param that
   *          the other point
   * @return the value <tt>0</tt> if this point is equal to the argument point (x0 = x1 and y0 =
   *         y1); a negative integer if this point is less than the argument point; and a positive
   *         integer if this point is greater than the argument point
   */
  @Override
  public int compareTo(Point that) {
    if (this.y < that.y) {
      return -1;
    } else if (this.y == that.y) {
      if (this.x < that.x) {
        return -1;
      } else if (this.x == that.x) {
        return 0;
      }
    }
    return 1;
  }

  /**
   * Compares two points by the slope they make with this point. The slope is defined as in the
   * slopeTo() method.
   *
   * @return the Comparator that defines this ordering on points
   */
  public Comparator<Point> slopeOrder() {
    return new Comparator<Point>() {

      @Override
      public int compare(Point first, Point second) {
        if (first == null || second == null) {
          throw new NullPointerException();
        }
        double slopeToFirst = slopeTo(first);
        double slopeToSecond = slopeTo(second);
        if (slopeToFirst < slopeToSecond) {
          return -1;
        } else if (slopeToFirst > slopeToSecond) {
          return 1;
        }
        return 0;
      }
    };
  }

  /**
   * Returns a string representation of this point. This method is provide for debugging; your
   * program should not rely on the format of the string representation.
   *
   * @return a string representation of this point
   */
  @Override
  public String toString() {
    /* DO NOT MODIFY */
    return "(" + x + ", " + y + ")";
  }

  public static void main(String[] args) {

  }
}