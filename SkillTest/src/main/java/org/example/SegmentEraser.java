package org.example;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class SegmentEraser {

    public static void main(String[] args) {
        List<Segment> segments = new ArrayList<>();
        segments.add(new Segment(1, 3));
        segments.add(new Segment(2, 4));
        segments.add(new Segment(3, 5));
        segments.add(new Segment(6, 7));
        segments.add(new Segment(5, 8));
        segments.add(new Segment(8, 9));

        int result = eraseOverlapSegments(segments);
        System.out.println("Number of segments to remove: " + result);
    }
    static class Segment {
        int start;
        int end;

        Segment(int start, int end) {
            this.start = start;
            this.end = end;
        }
    }

    public static int eraseOverlapSegments(List<Segment> segments) {
        if (segments == null || segments.isEmpty()) {
            return 0;
        }
        segments.sort(Comparator.comparingInt(s -> s.end));
        // Sort segments based on their end points
        List<Integer> devicePoints = new ArrayList<>();
        int lastPoint = Integer.MIN_VALUE;
        for (Segment s : segments) {
            if (s.start > lastPoint) {
                devicePoints.add(s.end);
                lastPoint = s.end;
            }
        }
        return devicePoints.size(); // Number of segments to remove
    }
}