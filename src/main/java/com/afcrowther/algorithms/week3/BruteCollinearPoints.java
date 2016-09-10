package com.afcrowther.algorithms.week3;

import java.util.Arrays;
import java.util.LinkedList;

public class BruteCollinearPoints {

  private final LinkedList<LineSegment> lineSegments;

  public BruteCollinearPoints(Point[] points) {

    if (points == null) {
      throw new NullPointerException();
    }

    Point[] sortedPoints = Arrays.copyOf(points, points.length);
    Arrays.sort(sortedPoints);

    this.lineSegments = new LinkedList<LineSegment>();

    // check for null or repeating point
    for (int i = 0; i < sortedPoints.length - 1; i++) {
      if (sortedPoints[i].compareTo(sortedPoints[i + 1]) == 0) {
        throw new IllegalArgumentException();
      }
      if (sortedPoints[i + 1] == null || (i == 0 && sortedPoints[i] == null)) {
        throw new NullPointerException();
      }
    }

    // find line segments
    for (int i = 0; i < sortedPoints.length - 3; i++) {
      Point firstPoint = sortedPoints[i];
      for (int j = i + 1; j < sortedPoints.length - 2; j++) {
        Point secondPoint = sortedPoints[j];
        for (int k = j + 1; k < sortedPoints.length - 1; k++) {
          Point thirdPoint = sortedPoints[k];
          for (int l = k + 1; l < sortedPoints.length; l++) {
            Point fourthPoint = sortedPoints[l];
            if (firstPoint.slopeTo(secondPoint) == firstPoint.slopeTo(thirdPoint)
                && firstPoint.slopeTo(thirdPoint) == firstPoint.slopeTo(fourthPoint)) {
              lineSegments.add(new LineSegment(firstPoint, fourthPoint));
            }
          }
        }
      }
    }
  }

  public int numberOfSegments() {
    return lineSegments.size();
  }

  public LineSegment[] segments() {
    return lineSegments.toArray(new LineSegment[lineSegments.size()]);
  }
}
