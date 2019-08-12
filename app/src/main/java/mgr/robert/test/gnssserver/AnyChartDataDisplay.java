package mgr.robert.test.gnssserver;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.charts.Scatter;
import com.anychart.core.scatter.series.Line;
import com.anychart.core.scatter.series.Marker;
import com.anychart.enums.MarkerType;

import java.math.BigDecimal;

public class AnyChartDataDisplay implements ChartDataDisplay {
    private final ChartData<DataEntry> chartData;
    private final AnyChartView anyChartView;

    public AnyChartDataDisplay(ChartData<DataEntry> chartData, AnyChartView anyChartView) {
        this.chartData = chartData;
        this.anyChartView = anyChartView;
    }

    @Override
    public void display() {
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
}
