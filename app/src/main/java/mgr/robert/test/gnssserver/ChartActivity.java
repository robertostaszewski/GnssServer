package mgr.robert.test.gnssserver;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.anychart.AnyChartView;

import java.util.ArrayList;

public class ChartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart_common);

        Intent intent = getIntent();
        ArrayList<String> dataPaths = intent.getStringArrayListExtra("dataPaths");
        ChartDataCreator chartDataCreator = new ChartDataCreator(new FileDataReader(dataPaths));

        ChartData chartData = chartDataCreator.create();

        AnyChartView anyChartView = findViewById(R.id.any_chart_view);
        anyChartView.setProgressBar(findViewById(R.id.progress_bar));

        new AnyChartDataDisplay(chartData, anyChartView).display();
    }
}
