package single_threaded_execution.example6;

public class Pair {
    private Tool left;
    private Tool right;

    public Pair(Tool left,Tool right) {
        this.left = left;
        this.right = right;
    }

    public Tool getLeft() {
        return left;
    }

    public void setLeft(Tool left) {
        this.left = left;
    }

    public Tool getRight() {
        return right;
    }

    public void setRight(Tool right) {
        this.right = right;
    }
}
