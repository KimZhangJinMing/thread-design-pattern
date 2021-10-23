package read_write_lock.example4;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

// 使用synchronized来保证线程安全
public class DataBase<K,V> {

    private Map<K,V> map = new HashMap<>();


    // 全部清楚
    public synchronized void clear() {
        verySlowly();
        map.clear();
    }

    // 给key分配value
    public synchronized void assign(K key,V value) {
        verySlowly();
        map.put(key,value);
    }

    // 获取给key分配的值
    public synchronized V retrieve(K key) {
        slowly();
        return map.get(key);
    }

    // 模拟耗时的操作
    private void slowly() {
        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    // 模拟非常耗时的操作
    private void verySlowly() {
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
