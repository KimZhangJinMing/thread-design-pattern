package immutable.example2;

// Point不是Immutable类
public class Point {
    // public的修饰符，外界可以进行修改
    public int x;
    public int y;

    public Point(int x,int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public String toString() {
        return "Point{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
