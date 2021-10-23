package read_write_lock.example5;

// 测试Database使用concurrentHashMap时读取的次数
// 与example3使用ReadWriteLock时读取的次数进行比较
// 与example4使用synchronized时读取的次数进行比较
public class Main {
    public static void main(String[] args) {
        DataBase<String, String> database = new DataBase<>();

        // 4个AssignThread 写入线程
        new AssignThread(database,"Alice","Alaska").start();
        new AssignThread(database,"Alice","Australia").start();
        new AssignThread(database,"Bobby","Brazil").start();
        new AssignThread(database,"Bobby","Bulgaria").start();

        // 200个RetrieveThread
        for (int i = 0; i < 100; i++) {
            new RetrieveThread(database,"Alice").start();
            new RetrieveThread(database,"Bobby").start();
        }

        // 执行10s
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // 强制退出
        System.exit(0);
    }
}
