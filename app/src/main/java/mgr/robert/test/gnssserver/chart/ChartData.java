package mgr.robert.test.gnssserver.chart;

import java.util.List;

public class ChartData {
    private final List<Point> drmsPoints;
    private final List<Point> drms2Points;
    private final List<Point> points;
    private final Point minPoint;
    private final Point maxPoint;
    private final Point minVisiblePoint;
    private final Point maxVisiblePoint;
    private final double radius;

    public ChartData(List<Point> points,
                     List<Point> drmsPoints,
                     List<Point> drms2Points,
                     Point minPoint,
                     Point maxPoint,
                     Point minVisiblePoint,
                     Point maxVisiblePoint, double radius) {
        this.drmsPoints = drmsPoints;
        this.drms2Points = drms2Points;
        this.points = points;
        this.minPoint = minPoint;
        this.maxPoint = maxPoint;
        this.minVisiblePoint = minVisiblePoint;
        this.maxVisiblePoint = maxVisiblePoint;
        this.radius = radius;
    }

    public List<Point> getDrmsPoints() {
        return drmsPoints;
    }

    public List<Point> getDrms2Points() {
        return drms2Points;
    }

    public List<Point> getPoints() {
        return points;
    }

    public Point getMinPoint() {
        return minPoint;
    }

    public Point getMaxPoint() {
        return maxPoint;
    }

    public Point getMinVisiblePoint() {
        return minVisiblePoint;
    }

    public Point getMaxVisiblePoint() {
        return maxVisiblePoint;
    }

    public double getRadius() {
        return radius;
    }
}
