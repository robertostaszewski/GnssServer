package mgr.robert.test.gnssserver;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DataReader {
    private List<String> dataPaths;
    private List<Double> longitudes = new ArrayList<>();
    private List<Double> latitudes = new ArrayList<>();
    private int rowsNum = 0;

    public DataReader(List<String> dataPaths) {
        this.dataPaths = dataPaths;
        fillData();
    }

    public List<Double> getLongitudes() {
        return longitudes;
    }

    public List<Double> getLatitudes() {
        return latitudes;
    }

    public int getRowsNum() {
        return rowsNum;
    }

    private void fillData() {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(dataPaths.get(0)))) {
            String line = bufferedReader.readLine();
            while ((line = bufferedReader.readLine()) != null) {
                String[] strings = line.split(" +");
                double x = Double.parseDouble(strings[2]);
                latitudes.add(x);
                double y = Double.parseDouble(strings[3]);
                longitudes.add(y);
                rowsNum++;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
