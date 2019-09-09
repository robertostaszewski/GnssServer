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
                .minimum(chartData.getMinVisiblePoint().getY())
                .maximum(chartData.getMaxVisiblePoint().getY());
        scatter.xScale()
                .minimum(chartData.getMinVisiblePoint().getX())
                .maximum(chartData.getMaxVisiblePoint().getX());

        scatter.yAxis(0).title("Latitude")
                .labels()
                .format("{%Value}{decimalsCount:4}");
        scatter.xAxis(0).title("Longitude")
                .drawFirstLabel(false)
                .drawLastLabel(false)
                .labels()
                .format("{%Value}{decimalsCount:4}");


        scatter.legend().enabled(true);

        Marker points = scatter.marker(PointConverter.toDataEntry(chartData.getPoints()));
        points.type(MarkerType.CIRCLE).size(2d);
        points.name("measurments");

        Line drmsLine = scatter.line(PointConverter.toDataEntry(chartData.getDrmsPoints()));
        drmsLine.stroke("black", 1d, null, (String) null, (String) null);
        drmsLine.name("drms");

        Line drms2Line = scatter.line(PointConverter.toDataEntry(chartData.getDrms2Points()));
        drms2Line.stroke("red", 1d, null, (String) null, (String) null);
        drms2Line.name("2drms");

        AnyChartView anyChartView = activity.findViewById(R.id.any_chart_view);
        anyChartView.setLicenceKey("s170533@student.pg.edu.pl-26e71b0b-ea45de04");
        anyChartView.setChart(scatter);

        TextView drmsTextView = activity.findViewById(R.id.drms);
        drmsTextView.setText(String.valueOf(chartData.getDrms()));
        TextView drms2TextView = activity.findViewById(R.id.drms2);
        drms2TextView.setText(String.valueOf(chartData.getDrms2()));
        TextView avgTextView = activity.findViewById(R.id.avg);
        avgTextView.setText(String.format("%f, %f", chartData.getAvgY(), chartData.getAvgX()));
    }

    @Override
    public void clear() {
        AnyChartView anyChartView = activity.findViewById(R.id.any_chart_view);
        anyChartView.clear();
    }
}
