package mgr.robert.test.gnssserver;

import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;

import java.util.ArrayList;
import java.util.List;

public class PointConverter {

    public static List<DataEntry> toDataEntry(List<Point> toConvert) {
        List<DataEntry> converted = new ArrayList<>(toConvert.size());
        for (Point point: toConvert) {
            converted.add(toDataEntry(point));
        }
        return converted;
    }

    private static DataEntry toDataEntry(Point point) {
        return new ValueDataEntry(point.getX(), point.getY());
    }
}
