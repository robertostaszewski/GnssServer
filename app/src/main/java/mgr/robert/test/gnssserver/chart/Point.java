package mgr.robert.test.gnssserver.chart;

public class Point {
    private final double x;
    private final double y;

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public static Point from(Point p) {
        return new Point(p.x, p.y);
    }
}
