package thread_per_message.example1;

// 委托端
public class Host {

    // 委托端中持有执行端的对象
    private final static Helper helper = new Helper();


    public static void request(int count, char c) {
        // 将请求委托给Helper类执行
        // 每次请求都是新启动一个线程,启动线程后,request方法就结束了
        System.out.println("  request(" + count + "," + c + ") START");
        new Thread(() -> helper.handle(count, c)).start();
        System.out.println("  request(" + count + "," + c + ") END");
    }

}
