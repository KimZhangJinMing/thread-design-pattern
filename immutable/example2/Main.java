package immutable.example2;

public class Main {
    public static void main(String[] args) {
        Point startPoint = new Point(0, 0);
        Point endPoint = new Point(100, 100);
        Line line = new Line(startPoint, endPoint);
        System.out.println(line);

        // 修改startPoint
        startPoint.x = 1;
        startPoint.y = 1;
        System.out.println(line);
    }
}
