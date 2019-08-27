package mgr.robert.test.gnssserver.chart.datasource;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import mgr.robert.test.gnssserver.chart.Point;
import mgr.robert.test.gnssserver.chart.datasource.DataReader;

public class FileDataReader implements DataReader {
    private List<String> dataPaths;

    public FileDataReader(List<String> dataPaths) {
        this.dataPaths = dataPaths;
    }

    @Override
    public List<Point> read() {
        List<Point> points = new ArrayList<>();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(dataPaths.get(0)))) {
            String line = bufferedReader.readLine();
            while ((line = bufferedReader.readLine()) != null) {
                String[] strings = line.split(" +");
                double y = Double.parseDouble(strings[2]);
                double x = Double.parseDouble(strings[3]);
                points.add(new Point(x, y));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return points;
    }
}
