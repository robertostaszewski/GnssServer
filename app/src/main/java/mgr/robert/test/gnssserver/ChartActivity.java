package mgr.robert.test.gnssserver;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.charts.Scatter;
import com.anychart.core.scatter.series.Line;
import com.anychart.core.scatter.series.Marker;
import com.anychart.enums.MarkerType;

import java.math.BigDecimal;
import java.util.ArrayList;

public class ChartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart_common);
        ChartData chartData = getchartData();

        AnyChartView anyChartView = findViewById(R.id.any_chart_view);
        anyChartView.setProgressBar(findViewById(R.id.progress_bar));

        Scatter scatter = AnyChart.scatter();

        scatter.animation(true);

        scatter.yScale()
                .minimum(rounded(chartData.getMinY()))
                .maximum(rounded(chartData.getMaxY()));
        scatter.xScale()
                .minimum(rounded(chartData.getMinX()))
                .maximum(rounded(chartData.getMaxX()));

        scatter.yAxis(0).title("Latitude");
        scatter.xAxis(0)
                .title("Longitude")
                .drawFirstLabel(false)
                .drawLastLabel(false);

        Marker marker = scatter.marker(chartData.getMarkerData());
        marker.type(MarkerType.CIRCLE).size(2d);

        Line scatterSeriesLine = scatter.line(chartData.getDrmsData());
        scatterSeriesLine.stroke("black", 1d, null, (String) null, (String) null);

        Line scatterSeriesLine1 = scatter.line(chartData.getDrms2Data());
        scatterSeriesLine1.stroke("red", 1d, null, (String) null, (String) null);

        anyChartView.setChart(scatter);
    }

    private static BigDecimal rounded(double value) {
        return new BigDecimal(value).setScale(2, BigDecimal.ROUND_HALF_EVEN);
    }

    private ChartData getchartData() {
        Intent intent = getIntent();
        ArrayList<String> dataPaths = intent.getStringArrayListExtra("dataPaths");
        DataReader dataReader = new DataReader(dataPaths);
        return new ChartData(dataReader.getLongitudes(), dataReader.getLatitudes(), dataReader.getRowsNum());
    }

}
