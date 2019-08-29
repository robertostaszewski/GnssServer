package mgr.robert.test.gnssserver.chart.anychart;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.charts.Scatter;
import com.anychart.core.scatter.series.Line;
import com.anychart.core.scatter.series.Marker;
import com.anychart.enums.MarkerType;

import java.math.BigDecimal;

import mgr.robert.test.gnssserver.chart.ChartData;
import mgr.robert.test.gnssserver.chart.ChartDataDisplay;

public class AnyChartDataDisplay implements ChartDataDisplay {
    private final ChartData chartData;
    private final AnyChartView anyChartView;

    public AnyChartDataDisplay(ChartData chartData, AnyChartView anyChartView) {
        this.chartData = chartData;
        this.anyChartView = anyChartView;
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

        scatter.yAxis(0).title("Latitude");
        scatter.xAxis(0).title("Longitude")
                .drawFirstLabel(false)
                .drawLastLabel(false);

        Marker marker = scatter.marker(PointConverter.toDataEntry(chartData.getMarkerData()));
        marker.type(MarkerType.CIRCLE).size(2d);

        Line scatterSeriesLine = scatter.line(PointConverter.toDataEntry(chartData.getDrmsData()));
        scatterSeriesLine.stroke("black", 1d, null, (String) null, (String) null);

        Line scatterSeriesLine1 = scatter.line(PointConverter.toDataEntry(chartData.getDrms2Data()));
        scatterSeriesLine1.stroke("red", 1d, null, (String) null, (String) null);

        anyChartView.setChart(scatter);
    }

    @Override
    public void clear() {
        anyChartView.clear();
    }

    private static BigDecimal rounded(double value) {
        return new BigDecimal(value).setScale(2, BigDecimal.ROUND_HALF_EVEN);
    }
}
