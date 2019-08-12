package mgr.robert.test.gnssserver;

import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ChartData {
    private final List<Double> xs;
    private final List<Double> ys;
    private int rowsNum;
    private List<DataEntry> drmsData;
    private List<DataEntry> drms2Data;
    private List<DataEntry> markerData;
    private double minX;
    private double minY;
    private double maxX;
    private double maxY;

    public ChartData(List<Double> xs, List<Double> ys, int rowsNum) {
        this.xs = Collections.unmodifiableList(xs);
        this.ys = Collections.unmodifiableList(ys);
        this.rowsNum = rowsNum;
        this.drmsData = new ArrayList<>(rowsNum);
        this.drms2Data = new ArrayList<>(rowsNum);
        this.markerData = new ArrayList<>(rowsNum);
        compute();
    }

    public List<DataEntry> getDrmsData() {
        return drmsData;
    }

    public List<DataEntry> getDrms2Data() {
        return drms2Data;
    }

    public List<DataEntry> getMarkerData() {
        return markerData;
    }

    public double getMinX() {
        return minX;
    }

    public double getMinY() {
        return minY;
    }

    public double getMaxX() {
        return maxX;
    }

    public double getMaxY() {
        return maxY;
    }

    public static <T> Builder<T> builder() {
        return new Builder<>();
    }

    private void compute() {
        double sumx = 0, sumy = 0;
        for (int i = 0; i < rowsNum; i++) {
            Double xValue = xs.get(i);
            Double yValue = ys.get(i);
            if (i == 0) {
                minX = xValue;
                minY = yValue;
            } else {
                minX = minX > xValue ? xValue : minX;
                minY = minY > yValue ? yValue : minY;
            }
            maxX = maxX <= xValue ? xValue : maxX;
            maxY = maxY <= yValue ? yValue : maxY;
            sumx += xValue;
            sumy += yValue;
            markerData.add(new ValueDataEntry(xValue, yValue));
        }

        double avgX = sumx / xs.size();
        double avgY = sumy / ys.size();
        double precX = 0, precY = 0;
        for (int i = 0; i < rowsNum; i++) {
            precX += Math.pow(xs.get(i) - avgX, 2);
            precY += Math.pow(ys.get(i) - avgY, 2);
        }

        precX = precX / xs.size();
        precY = precY / ys.size();

        double drms = Math.pow(precX + precY, 0.5);
        double drms2 = 2 * drms;

        int n = 60;
        for (int i = 0; i <= n; i++) {
            double t = 2 * Math.PI * i / n;
            drmsData.add(new ValueDataEntry(avgX + drms * Math.cos(t), avgY + drms * Math.sin(t)));
            drms2Data.add(new ValueDataEntry(avgX + drms2 * Math.cos(t), avgY + drms2 * Math.sin(t)));
        }

        minX = avgX - drms2 > minX ? minX : avgX - drms2;
        maxX = avgX + drms2 < maxX ? maxX : avgX + drms2;
        minY = avgY - drms2 > minY ? minY : avgY - drms2;
        maxY = avgY + drms2 < maxY ? maxY : avgY + drms2;
    }
}
