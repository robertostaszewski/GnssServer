package mgr.robert.test.gnssserver.chart.anychart;

import android.app.Activity;
import android.widget.TextView;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.charts.Scatter;
import com.anychart.core.scatter.series.Line;
import com.anychart.core.scatter.series.Marker;
import com.anychart.enums.MarkerType;

import java.math.BigDecimal;

import mgr.robert.test.gnssserver.R;
import mgr.robert.test.gnssserver.chart.ChartData;
import mgr.robert.test.gnssserver.chart.ChartDataDisplay;

public class AnyChartDataDisplay implements ChartDataDisplay {
    private final ChartData chartData;
    private final Activity activity;

    public AnyChartDataDisplay(ChartData chartData, Activity activity) {
        this.chartData = chartData;
        this.activity = activity;
    }

    @Override
    public void display() {
        Scatter scatter = AnyChart.scatter();

        scatter.animation(true);

        scatter.yScale()
                .minimum(rounded(chartData.getMinVisiblePoint().getY()))
                .maximum(rounded(chartData.getMaxVisiblePoint().getY()));
        scatter.xScale()
                .minimum(rounded(chartData.getMinVisiblePoint().getX()))
                .maximum(rounded(chartData.getMaxVisiblePoint().getX()));

        scatter.yAxis(0).title("Longitude");
        scatter.xAxis(0).title("Latitude")
                .drawFirstLabel(false)
                .drawLastLabel(false);

        Marker marker = scatter.marker(PointConverter.toDataEntry(chartData.getPoints()));
        marker.type(MarkerType.CIRCLE).size(2d);

        Line scatterSeriesLine = scatter.line(PointConverter.toDataEntry(chartData.getDrmsPoints()));
        scatterSeriesLine.stroke("black", 1d, null, (String) null, (String) null);

        Line scatterSeriesLine1 = scatter.line(PointConverter.toDataEntry(chartData.getDrms2Points()));
        scatterSeriesLine1.stroke("red", 1d, null, (String) null, (String) null);

        AnyChartView anyChartView = activity.findViewById(R.id.any_chart_view);
        anyChartView.setChart(scatter);

        TextView textView = activity.findViewById(R.id.promien);
        textView.setText(String.valueOf(chartData.getRadius()));
    }

    @Override
    public void clear() {
        AnyChartView anyChartView = activity.findViewById(R.id.any_chart_view);
        anyChartView.clear();
    }

    private static BigDecimal rounded(double value) {
        return new BigDecimal(value).setScale(10, BigDecimal.ROUND_HALF_EVEN);
    }
}
