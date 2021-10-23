package thread_per_message.example1;

public class Main {
    public static void main(String[] args) {
        System.out.println(" main START");
        Host.request(10,'a');
        Host.request(10,'b');
        Host.request(10,'c');
        System.out.println(" main END");

    }
}
