package mgr.robert.test.gnssserver.android.activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.anychart.AnyChartView;

import java.util.ArrayList;

import mgr.robert.test.gnssserver.chart.anychart.AnyChartDataDisplay;
import mgr.robert.test.gnssserver.chart.ChartData;
import mgr.robert.test.gnssserver.chart.ChartDataCreator;
import mgr.robert.test.gnssserver.chart.ChartDataDisplay;
import mgr.robert.test.gnssserver.chart.datasource.FileDataReader;
import mgr.robert.test.gnssserver.R;

public class ChartActivity extends AppCompatActivity {

    private ChartDataDisplay chartDataDisplay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart_common);

        Intent intent = getIntent();
        String dataPath = intent.getStringExtra("dataPath");
        ChartDataCreator chartDataCreator = new ChartDataCreator(new FileDataReader(dataPath));

        ChartData chartData = chartDataCreator.create();

        AnyChartView anyChartView = findViewById(R.id.any_chart_view);
        anyChartView.setProgressBar(findViewById(R.id.progress_bar));

        chartDataDisplay = new AnyChartDataDisplay(chartData, anyChartView);
        chartDataDisplay.display();
    }

    @Override
    protected void onStop() {
        chartDataDisplay.clear();
        super.onStop();
    }
}
