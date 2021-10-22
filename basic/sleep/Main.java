package basic.sleep;



public class Main {
    public static void main(String[] args) {
        System.out.println("===BEGIN===");
        // 睡眠3s
        try {
            Something.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("===END===");
    }
}
