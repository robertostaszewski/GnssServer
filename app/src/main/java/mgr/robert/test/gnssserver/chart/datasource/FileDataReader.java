package mgr.robert.test.gnssserver.chart.datasource;

import android.util.Log;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import mgr.robert.test.gnssserver.chart.Point;

public class FileDataReader implements DataReader {
    private final String dataPath;

    public FileDataReader(String dataPath) {
        this.dataPath = dataPath;
    }

    @Override
    public List<Point> read() {
        List<Point> points = new ArrayList<>();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(dataPath))) {
            bufferedReader.readLine(); //skip headers
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                try {
                    String[] strings = line.split(" +");
                    double y = Double.parseDouble(strings[2]);
                    double x = Double.parseDouble(strings[3]);
                    points.add(new Point(x, y));
                } catch (Exception e) {
                Log.e("FDR", "exception in parsing double", e);
            }
            }
        } catch (Exception e) {
            Log.e("FDR", "exception in read", e);
        }
        return points;
    }
}
