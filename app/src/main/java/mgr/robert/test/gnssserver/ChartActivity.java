package mgr.robert.test.gnssserver;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;

import java.util.ArrayList;

public class ChartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart_common);
        ChartData<DataEntry> chartData = getchartData();

        AnyChartView anyChartView = findViewById(R.id.any_chart_view);
        anyChartView.setProgressBar(findViewById(R.id.progress_bar));

        new AnyChartDataDisplay(chartData, anyChartView).display();
    }

    private ChartData<DataEntry> getchartData() {
        Intent intent = getIntent();
        ArrayList<String> dataPaths = intent.getStringArrayListExtra("dataPaths");
        DataReader dataReader = new DataReader(dataPaths);
        return ChartData.<DataEntry>builder()
                .withEntryDataCreator(new AnyChartEntryDataCreator())
                .withLongtitudes(dataReader.getLongitudes())
                .withLatitudes(dataReader.getLatitudes())
                .withRowsNum(dataReader.getRowsNum())
                .build();
    }
}
