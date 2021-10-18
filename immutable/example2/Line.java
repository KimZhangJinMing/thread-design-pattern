package immutable.example2;

public class Line {
    private final Point startPoint;
    private final Point endPoint;

    // Point可能被外界修改，破坏Immutable类
    // 如果Point也是Immutable的类，那么Line也是Immutable类
    // 或者可以创建新的实例来保证Line是Immutable类
    public Line(Point startPoint, Point endPoint) {
//        this.startPoint = startPoint;
//        this.endPoint = endPoint;
        this.startPoint = new Point(startPoint.getX(),startPoint.getY());
        this.endPoint = new Point(endPoint.getX(), endPoint.getY());
    }

    @Override
    public String toString() {
        return "Line{" +
                "startPoint=" + startPoint +
                ", endPoint=" + endPoint +
                '}';
    }

    public Point getStartPoint() {
        return startPoint;
    }

    public Point getEndPoint() {
        return endPoint;
    }
}
