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
    private final double drms;
    private final double drms2;
    private final double avgX;
    private final double avgY;

    public ChartData(List<Point> points,
                     List<Point> drmsPoints,
                     List<Point> drms2Points,
                     Point minPoint,
                     Point maxPoint,
                     Point minVisiblePoint,
                     Point maxVisiblePoint,
                     double drms,
                     double drms2,
                     double avgX,
                     double avgY) {
        this.drmsPoints = drmsPoints;
        this.drms2Points = drms2Points;
        this.points = points;
        this.minPoint = minPoint;
        this.maxPoint = maxPoint;
        this.minVisiblePoint = minVisiblePoint;
        this.maxVisiblePoint = maxVisiblePoint;
        this.drms = drms;
        this.drms2 = drms2;
        this.avgX = avgX;
        this.avgY = avgY;
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

    public double getDrms() {
        return drms;
    }

    public double getDrms2() {
        return drms2;
    }

    public double getAvgX() {
        return avgX;
    }

    public double getAvgY() {
        return avgY;
    }
}
