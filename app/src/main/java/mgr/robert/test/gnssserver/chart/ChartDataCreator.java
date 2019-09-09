package mgr.robert.test.gnssserver.chart;

import java.util.ArrayList;
import java.util.List;

import mgr.robert.test.gnssserver.chart.datasource.DataReader;

public class ChartDataCreator {

    private static final int NUM_OF_CIRCLE_POINTS = 60;
    private final DataReader dataReader;

    public ChartDataCreator(DataReader dataReader) {
        this.dataReader = dataReader;
    }

    public ChartData create() {
        List<Point> loadedPoints = dataReader.read();
        List<Point> drmsPoints = new ArrayList<>(NUM_OF_CIRCLE_POINTS + 1);
        List<Point> drms2Points = new ArrayList<>(NUM_OF_CIRCLE_POINTS + 1);
        List<Point> points = new ArrayList<>(loadedPoints.size());
        double minX = 0;
        double minY = 0;
        double maxX = 0;
        double maxY = 0;
        double sumx = 0;
        double sumy = 0;

        for (Point p : loadedPoints) {
            minX = (minX == 0 || minX > p.getX()) ? p.getX() : minX;
            minY = (minY == 0 || minY > p.getY()) ? p.getY() : minY;
            maxX = maxX <= p.getX() ? p.getX() : maxX;
            maxY = maxY <= p.getY() ? p.getY() : maxY;
            sumx += p.getX();
            sumy += p.getY();
            points.add(Point.from(p));
        }

        double avgX = sumx / points.size();
        double avgY = sumy / points.size();
        double precX = 0, precY = 0;
        for (Point p : points) {
            precX += Math.pow(p.getX() - avgX, 2);
            precY += Math.pow(p.getY() - avgY, 2);
        }

        precX = precX / (points.size() - 1);
        precY = precY / (points.size() - 1);

        double drms = Math.pow(precX + precY, 0.5);
        double drms2 = 2 * drms;

        for (int i = 0; i <= NUM_OF_CIRCLE_POINTS; i++) {
            double t = 2 * Math.PI * i / NUM_OF_CIRCLE_POINTS;
            drmsPoints.add(new Point(avgX + drms * Math.cos(t), avgY + drms * Math.sin(t)));
            drms2Points.add(new Point(avgX + drms2 * Math.cos(t), avgY + drms2 * Math.sin(t)));
        }

        double minVisibleX = avgX - drms2 > minX ? minX : avgX - drms2;
        double maxVisibleX = avgX + drms2 < maxX ? maxX : avgX + drms2;
        double minVisibleY = avgY - drms2 > minY ? minY : avgY - drms2;
        double maxVisibleY = avgY + drms2 < maxY ? maxY : avgY + drms2;

        return new ChartData(points, drmsPoints, drms2Points,
                new Point(minX, minY), new Point(maxX, maxY), new Point(minVisibleX, minVisibleY),
                new Point(maxVisibleX, maxVisibleY), drms, drms2, avgX, avgY);
    }
}
