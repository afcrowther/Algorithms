package com.afcrowther.algorithms.week5;

import java.util.LinkedList;

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

public class KdTree {

  private static final int DIMENSION = 2;

  private Node root;
  private int size;

  public KdTree() {
    StdDraw.setScale(-0.1, 1.1);
    size = 0;
  }

  public boolean isEmpty() {
    return root == null;
  }

  public int size() {
    return size;
  }

  public void insert(Point2D p) {
    if (p == null) {
      throw new NullPointerException();
    }
    root = insert(root, p, 0);
  }

  private Node insert(Node x, Point2D point, int level) {

    if (x == null) {
      size++;
      return new Node(point, level, 1);
    }
    int compare = doCompare(point, x);

    if (compare <= 0) {
      if (x.point.equals(point)) {
        return x;
      } else {
        x.left = insert(x.left, point, ++level);
      }
    } else {
      x.right = insert(x.right, point, ++level);
    }
    return x;
  }

  public boolean contains(Point2D p) {
    if (p == null) {
      throw new NullPointerException();
    }
    return get(p, root) != null;
  }

  public void draw() {
    if (root == null) {
      return;
    }
    StdDraw.setPenRadius(0.001);
    StdDraw.setPenColor(StdDraw.BLACK);
    StdDraw.rectangle(0.5, 0.5, 0.5, 0.5);
    draw(root, new RectHV(0, 0, 1, 1));
  }

  private void draw(Node node, RectHV prev) {
    if (node == null) {
      return;
    }

    StdDraw.setPenRadius(0.003);
    if (node.isVertical()) {
      StdDraw.setPenColor(StdDraw.RED);
      StdDraw.line(node.point.x(), prev.ymin(), node.point.x(), prev.ymax());
    } else {
      StdDraw.setPenColor(StdDraw.BLUE);
      StdDraw.line(prev.xmin(), node.point.y(), prev.xmax(), node.point.y());
    }
    StdDraw.setPenRadius(0.014);
    StdDraw.setPenColor(StdDraw.BLACK);
    node.point.draw();
    RectHV leftRectangle = getLeftRectagle(node, prev),
        rightRectangle = getRightRectangle(node, prev);

    // save a bit of memory
    prev = null;

    draw(node.left, leftRectangle);
    draw(node.right, rightRectangle);
  }

  public Iterable<Point2D> range(RectHV rect) {
    if (rect == null) {
      throw new NullPointerException();
    }
    LinkedList<Point2D> points = new LinkedList<Point2D>();
    if (root == null) {
      return points;
    }
    range(points, root, new RectHV(0, 0, 1, 1), rect);
    return points;
  }

  private void range(LinkedList<Point2D> points, Node node, RectHV nodeRect, RectHV rect) {
    if (node != null) {
      if (rect.contains(node.point)) {
        points.add(node.point);
      }
      if (nodeRect.intersects(rect)) {
        range(points, node.left, getLeftRectagle(node, nodeRect), rect);
        range(points, node.right, getRightRectangle(node, nodeRect), rect);
      }
    }
  }

  public Point2D nearest(Point2D p) {
    if (root == null) {
      return null;
    }
    return nearest(p, root, root.point, new RectHV(0, 0, 1, 1));
  }

  private Point2D nearest(Point2D point, Node node, Point2D champion, RectHV prev) {
    if (node == null) {
      return champion;
    }
    if (point.distanceSquaredTo(node.point) < point.distanceSquaredTo(champion)) {
      champion = node.point;
    }

    if (prev.distanceSquaredTo(point) < point.distanceSquaredTo(champion)) {
      if (doCompare(point, node) < 0) {
        // do left/ bottom first
        champion = nearest(point, node.left, champion, getLeftRectagle(node, prev));
        if (champion.distanceSquaredTo(point) > prev.distanceSquaredTo(point)) {
          champion = nearest(point, node.right, champion, getRightRectangle(node, prev));
        }
      } else {
        // do right/ top first
        champion = nearest(point, node.right, champion, getRightRectangle(node, prev));
        if (champion.distanceSquaredTo(point) > prev.distanceSquaredTo(point)) {
          champion = nearest(point, node.left, champion, getLeftRectagle(node, prev));
        }
      }
    }
    return champion;
  }

  private Point2D get(Point2D point, Node node) {
    if (node == null) {
      return null;
    }
    int compare = doCompare(point, node);
    if (compare <= 0) {
      if (node.point.equals(point)) {
        return node.point;
      }
      return get(point, node.left);
    } else if (compare > 0) {
      return get(point, node.right);
    } else {
      return null;
    }
  }

  private int doCompare(Point2D point, Node node) {
    if (node.isVertical()) {
      return Point2D.X_ORDER.compare(point, node.point);
    } else {
      return Point2D.Y_ORDER.compare(point, node.point);
    }
  }

  private RectHV getLeftRectagle(Node node, RectHV prev) {
    if (node.isVertical()) {
      return new RectHV(prev.xmin(), prev.ymin(), node.point.x(), prev.ymax());
    } else {
      return new RectHV(prev.xmin(), prev.ymin(), prev.xmax(), node.point.y());
    }
  }

  private RectHV getRightRectangle(Node node, RectHV prev) {
    if (node.isVertical()) {
      return new RectHV(node.point.x(), prev.ymin(), prev.xmax(), prev.ymax());
    } else {
      return new RectHV(prev.xmin(), node.point.y(), prev.xmax(), prev.ymax());
    }
  }

  private final class Node {

    private Node right, left;
    private Point2D point;
    private int level;

    public Node(Point2D point, int level, int size) {
      this.point = point;
      this.level = level;
    }

    private boolean isVertical() {
      return level % DIMENSION == 0;
    }
  }

  public static void main(String[] args) {
  }
}
