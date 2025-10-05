package Jaikin;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

public class Chaikin {
    public static List<Point> chaikinAlgo(List<Point> inputPoints) {
        List<Point> newPoints = new ArrayList<>();
        if (inputPoints.size() < 2) {
            return newPoints;
        } else if (inputPoints.size() == 2) {
            return inputPoints;
        }
        try {
            // Add first point (to keep curve start)
            newPoints.add(inputPoints.get(0));
            for (int i = 0; i < inputPoints.size() - 1; i++) {
                Point start = inputPoints.get(i);
                Point end = inputPoints.get(i + 1);

                int x0 = (int) Math.round(0.75 * start.x + 0.25 * end.x);
                int y0 = (int) Math.round(0.75 * start.y + 0.25 * end.y);

                int x1 = (int) Math.round(0.25 * start.x + 0.75 * end.x);
                int y1 = (int) Math.round(0.25 * start.y + 0.75 * end.y);

                newPoints.add(new Point(x0, y0));
                newPoints.add(new Point(x1, y1));
            }
            // Add last point (to keep curve end)
            newPoints.add(inputPoints.get(inputPoints.size() - 1));
        } catch (Exception e) {
            System.err.println("Error in chaikin: " + e);
        }
        System.err.println("Chaikin points = " + newPoints.size());
        return newPoints;
    }
}
