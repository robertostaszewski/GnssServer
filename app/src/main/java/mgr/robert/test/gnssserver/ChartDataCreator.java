package mgr.robert.test.gnssserver;

import java.util.ArrayList;
import java.util.List;

public class ChartDataCreator {

    private final DataReader dataReader;

    public ChartDataCreator(DataReader dataReader) {
        this.dataReader = dataReader;
    }

    public ChartData create() {
        List<Point> points = dataReader.read();
        List<Point> drmsData = new ArrayList<>(points.size());
        List<Point> drms2Data = new ArrayList<>(points.size());
        List<Point> markerData = new ArrayList<>(points.size());
        double minX = 0;
        double minY = 0;
        double maxX = 0;
        double maxY = 0;
        double sumx = 0;
        double sumy = 0;

        for (Point p : points) {
            minX = minX > p.getX() ? p.getX() : minX;
            minY = minY > p.getY() ? p.getY() : minY;
            maxX = maxX <= p.getX() ? p.getX() : maxX;
            maxY = maxY <= p.getY() ? p.getY() : maxY;
            sumx += p.getX();
            sumy += p.getY();
            markerData.add(Point.from(p));
        }

        double avgX = sumx / points.size();
        double avgY = sumy / points.size();
        double precX = 0, precY = 0;
        for (Point p : points) {
            precX += Math.pow(p.getX() - avgX, 2);
            precY += Math.pow(p.getY() - avgY, 2);
        }

        precX = precX / points.size();
        precY = precY / points.size();

        double drms = Math.pow(precX + precY, 0.5);
        double drms2 = 2 * drms;

        int n = 60;
        for (int i = 0; i <= n; i++) {
            double t = 2 * Math.PI * i / n;
            drmsData.add(new Point(avgX + drms * Math.cos(t), avgY + drms * Math.sin(t)));
            drms2Data.add(new Point(avgX + drms2 * Math.cos(t), avgY + drms2 * Math.sin(t)));
        }

        double minVisibleX = avgX - drms2 > minX ? minX : avgX - drms2;
        double maxVisibleX = avgX + drms2 < maxX ? maxX : avgX + drms2;
        double minVisibleY = avgY - drms2 > minY ? minY : avgY - drms2;
        double maxVisibleY = avgY + drms2 < maxY ? maxY : avgY + drms2;

        return new ChartData(markerData, drmsData, drms2Data,
                new Point(minX, minY), new Point(maxX, maxY),
                new Point(minVisibleX, minVisibleY), new Point(maxVisibleX, maxVisibleY));
    }
}
