package basic.semaphore;

public class Main {
    public static void main(String[] args) {
        // 设置3个资源
        BoundedResource boundedResource = new BoundedResource(3);
        // 启动10个线程使用资源
        for (int i = 0; i < 10; i++) {
            new UserThread(boundedResource).start();
        }
    }
}
