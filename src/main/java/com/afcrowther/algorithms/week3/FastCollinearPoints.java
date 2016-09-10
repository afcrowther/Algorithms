package com.afcrowther.algorithms.week3;

import java.util.Arrays;
import java.util.LinkedList;

public class FastCollinearPoints {

  private LinkedList<LineSegment> lineSegments;

  public FastCollinearPoints(Point[] points) {

    if (points == null) {
      throw new NullPointerException();
    }
    // sort array into natural order
    Point[] sortedPoints = Arrays.copyOf(points, points.length);
    Arrays.sort(sortedPoints);
    for (int i = 0; i < sortedPoints.length - 1; i++) {
      if (sortedPoints[i + 1] == null || (i == 0 && sortedPoints[i] == null)) {
        throw new NullPointerException();
      }
      if (sortedPoints[i].compareTo(sortedPoints[i + 1]) == 0) {
        throw new IllegalArgumentException();
      }
    }
    this.lineSegments = new LinkedList<LineSegment>();
    findLineSegments(sortedPoints);
  }

  public int numberOfSegments() {
    return lineSegments.size();
  }

  public LineSegment[] segments() {
    return lineSegments.toArray(new LineSegment[lineSegments.size()]);
  }

  private void findLineSegments(Point[] pointsSorted) {

    for (int i = 0; i < pointsSorted.length - 3; i++) {
      Point current = pointsSorted[i];

      Point[] sortedBySlopeToCurrent = Arrays.copyOf(pointsSorted, pointsSorted.length);
      Arrays.sort(sortedBySlopeToCurrent, current.slopeOrder());

      LinkedList<Point> segment = new LinkedList<Point>();
      segment.add(current);
      for (int j = 1; j < sortedBySlopeToCurrent.length - 1; j++) {

        double slopeToCurrent = current.slopeTo(sortedBySlopeToCurrent[j]);
        double slopeToNext = current.slopeTo(sortedBySlopeToCurrent[j + 1]);

        // see if this and next have same slope to current reference point
        if (slopeToCurrent == slopeToNext) {

          // is it the first with this slope? if so add this
          if (segment.peekLast() == current) {
            segment.add(sortedBySlopeToCurrent[j]);
          }
          // add the next point the the segment
          segment.add(sortedBySlopeToCurrent[j + 1]);
        }

        // check if the slope doesn't match OR this is the last point to check with relation to
        // reference point i
        if (slopeToCurrent != slopeToNext || j == sortedBySlopeToCurrent.length - 2) {

          // is the segment large enough to add?
          if (segment.size() > 3) {
            // check if this slope is a subset, as current (i) is always zero, and the rest are
            // ordered by
            // (slope, natural), we can assume that if current is considered less than (aka comes
            // before) the
            // second element in the list, then this is not a subset line segment, as we will always
            // have the last
            // element in the slope because of the way the data is sorted
            if (current.compareTo(segment.get(1)) < 0) {
              lineSegments.add(new LineSegment(current, segment.getLast()));
            }
          }
          segment.clear();
          segment.addFirst(current);
        }
      }
    }
  }
}
