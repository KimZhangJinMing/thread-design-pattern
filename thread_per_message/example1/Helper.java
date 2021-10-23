package thread_per_message.example1;

// 执行端
public class Helper {

    // 将字符c输出count次，在同一行显示
    public void handle(int count,char c) {
        System.out.println("   handle(" + count + "," + c + ") START");
        for (int i = 0; i < count; i++) {
            System.out.print(c);
            // 延缓显示速度
            slowly();
        }
        System.out.println();
        System.out.println("   handle(" + count + "," + c + ") END");
    }

    private void slowly() {
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
