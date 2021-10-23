package read_write_lock.example3;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

// 使用ReadWriteLock来保证线程安全
public class DataBase<K,V> {

    private Map<K,V> map = new HashMap<>();
    private ReadWriteLock lock = new ReentrantReadWriteLock();
    private Lock readLock = lock.readLock();
    private Lock writeLock = lock.writeLock();


    // 全部清楚
    public void clear() {
        writeLock.lock();
        try {
            verySlowly();
            map.clear();
        } finally {
            writeLock.unlock();
        }
    }

    // 给key分配value
    public void assign(K key,V value) {
        writeLock.lock();
        try {
            verySlowly();
            map.put(key,value);
        } finally {
            writeLock.unlock();
        }
    }

    // 获取给key分配的值
    public V retrieve(K key) {
        readLock.lock();
        try {
            slowly();
            return map.get(key);
        } finally {
            readLock.unlock();
        }
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
