package thread_specific_storage.example1;

/**
 * 不使用Thread_Specific_Storage
 */
public class Main {

    public static void main(String[] args) {
        System.out.println("main BEGIN");
        for (int i = 0; i < 10; i++) {
            Log.println("main: i= " + i);
            System.out.println("main: i= " + i);
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        Log.close();
        System.out.println("main END");
    }
}
