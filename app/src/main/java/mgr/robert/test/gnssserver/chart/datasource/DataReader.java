package mgr.robert.test.gnssserver.chart.datasource;

import java.util.List;

import mgr.robert.test.gnssserver.chart.Point;

public interface DataReader {
    List<Point> read();
}
