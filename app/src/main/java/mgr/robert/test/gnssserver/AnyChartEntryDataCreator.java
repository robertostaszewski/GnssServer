package mgr.robert.test.gnssserver;

import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;

public class AnyChartEntryDataCreator implements EntryDataCreator<DataEntry> {

    @Override
    public ValueDataEntry newEntryData(Number x, Number y) {
        return new ValueDataEntry(x, y);
    }
}
