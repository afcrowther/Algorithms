package com.afcrowther.algorithms.week5;

import java.util.LinkedList;
import java.util.TreeSet;

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

public class PointSET {

  private TreeSet<Point2D> tree;

  public PointSET() {
    tree = new TreeSet<Point2D>();
    StdDraw.setCanvasSize(1, 1);
  }

  public boolean isEmpty() {
    return tree.isEmpty();
  }

  public int size() {
    return tree.size();
  }

  public void insert(Point2D p) {
    if (p == null) {
      throw new NullPointerException();
    }
    tree.add(p);
  }

  public boolean contains(Point2D p) {
    if (p == null) {
      throw new NullPointerException();
    }
    return tree.contains(p);
  }

  public void draw() {
    for (Point2D point : tree) {
      StdDraw.point(point.x(), point.y());
    }
  }

  public Iterable<Point2D> range(RectHV rect) {
    if (rect == null) {
      throw new NullPointerException();
    }
    LinkedList<Point2D> intersections = new LinkedList<Point2D>();
    for (Point2D point : tree) {
      if (rect.contains(point)) {
        intersections.add(point);
      }
    }
    return intersections;
  }

  public Point2D nearest(Point2D p) {
    if (p == null) {
      throw new NullPointerException();
    }
    Point2D nearest = null;
    double closestDistance = Double.MAX_VALUE;
    for (Point2D point : tree) {
      double pointDistance = point.distanceTo(p);
      if (pointDistance < closestDistance) {
        nearest = point;
        closestDistance = pointDistance;
      }
    }
    return nearest;
  }

  public static void main(String[] args) {
  }
}
