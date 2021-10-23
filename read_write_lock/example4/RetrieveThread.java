package read_write_lock.example4;

import java.util.concurrent.atomic.AtomicInteger;

// 查找的函数
public class RetrieveThread extends Thread {
    private DataBase<String,String> dataBase;
    private String key;
    // 多个线程共享
    private static final AtomicInteger count = new AtomicInteger();

    public RetrieveThread(DataBase dataBase, String key) {
        this.dataBase = dataBase;
        this.key = key;
    }

    @Override
    public void run() {
        try {
            while(true) {
                String value = dataBase.retrieve(key);
                int counter = count.incrementAndGet();
                System.out.println(counter + " : "+ value);
            }
        } finally {
            System.out.println(getName() + " interrupt.");
        }
    }
}
