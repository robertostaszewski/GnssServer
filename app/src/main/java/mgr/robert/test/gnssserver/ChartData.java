package mgr.robert.test.gnssserver;

import java.util.List;

public class ChartData {
    private final List<Point> drmsData;
    private final List<Point> drms2Data;
    private final List<Point> markerData;
    private final Point minPoint;
    private final Point maxPoint;
    private final Point minVisiblePoint;
    private final Point maxVisiblePoint;

    public ChartData(List<Point> markerData,
                     List<Point> drmsData,
                     List<Point> drms2Data,
                     Point minPoint,
                     Point maxPoint,
                     Point minVisiblePoint,
                     Point maxVisiblePoint) {
        this.drmsData = drmsData;
        this.drms2Data = drms2Data;
        this.markerData = markerData;
        this.minPoint = minPoint;
        this.maxPoint = maxPoint;
        this.minVisiblePoint = minVisiblePoint;
        this.maxVisiblePoint = maxVisiblePoint;
    }

    public List<Point> getDrmsData() {
        return drmsData;
    }

    public List<Point> getDrms2Data() {
        return drms2Data;
    }

    public List<Point> getMarkerData() {
        return markerData;
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
}
