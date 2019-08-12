package mgr.robert.test.gnssserver;

import java.util.ArrayList;
import java.util.List;

public class ChartData<T> {
    private List<T> drmsData;
    private List<T> drms2Data;
    private List<T> markerData;
    private double minX;
    private double minY;
    private double maxX;
    private double maxY;

    private ChartData(List<T> drmsData, List<T> drms2Data,
                     List<T> markerData, double minX, double minY, double maxX,
                     double maxY) {
        this.drmsData = drmsData;
        this.drms2Data = drms2Data;
        this.markerData = markerData;
        this.minX = minX;
        this.minY = minY;
        this.maxX = maxX;
        this.maxY = maxY;
    }

    public List<T> getDrmsData() {
        return drmsData;
    }

    public List<T> getDrms2Data() {
        return drms2Data;
    }

    public List<T> getMarkerData() {
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

    public interface EntryDataCreatorBuilder<T> {
        LongtitudesBuilder withEntryDataCreator(EntryDataCreator<T> entryDataCreator);
    }

    public interface LongtitudesBuilder {
        LatitudesBuilder withLongtitudes(List<Double> xs);
    }

    public interface LatitudesBuilder {
        RowsNumStep withLatitudes(List<Double> ys);
    }

    public interface RowsNumStep {
        FinalBuilder withRowsNum(int rowsNum);
    }

    public interface FinalBuilder {
        <T> ChartData<T> build();
    }

    public static class Builder<T> implements EntryDataCreatorBuilder<T>, LatitudesBuilder, LongtitudesBuilder, RowsNumStep, FinalBuilder {
        private EntryDataCreator<T> entryDataCreator;
        List<Double> xs;
        List<Double> ys;
        int rowsNum;

        @Override
        public LongtitudesBuilder withEntryDataCreator(EntryDataCreator<T> entryDataCreator) {
            this.entryDataCreator = entryDataCreator;
            return this;
        }

        @Override
        public LatitudesBuilder withLongtitudes(List<Double> xs) {
            this.xs = xs;
            return this;
        }

        @Override
        public RowsNumStep withLatitudes(List<Double> ys) {
            this.ys = ys;
            return this;
        }

        @Override
        public FinalBuilder withRowsNum(int rowsNum) {
            this.rowsNum = rowsNum;
            return this;
        }

        @Override
        public ChartData<T> build() {
            return create();
        }

        private ChartData<T> create() {
            List<T> drmsData = new ArrayList<>(rowsNum);
            List<T> drms2Data = new ArrayList<>(rowsNum);
            List<T> markerData = new ArrayList<>(rowsNum);

            double minX = 0;
            double minY = 0;
            double maxX = 0;
            double maxY = 0;
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
                markerData.add(entryDataCreator.newEntryData(xValue, yValue));
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
                drmsData.add(entryDataCreator.newEntryData(avgX + drms * Math.cos(t), avgY + drms * Math.sin(t)));
                drms2Data.add(entryDataCreator.newEntryData(avgX + drms2 * Math.cos(t), avgY + drms2 * Math.sin(t)));
            }

            minX = avgX - drms2 > minX ? minX : avgX - drms2;
            maxX = avgX + drms2 < maxX ? maxX : avgX + drms2;
            minY = avgY - drms2 > minY ? minY : avgY - drms2;
            maxY = avgY + drms2 < maxY ? maxY : avgY + drms2;

            return new ChartData<>(drmsData, drms2Data, markerData, minX, minY, maxX, maxY);
        }
    }
}
