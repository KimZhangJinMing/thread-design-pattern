package read_write_lock.example2;

import java.util.Arrays;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

// 线程读取和写入操作的数据类
// 使用Java自带的ReadWriteLock
public class Data {
    private char[] buffer;
    // 使用公平锁
    private ReadWriteLock lock = new ReentrantReadWriteLock(true);
    private Lock readLock = lock.readLock();
    private Lock writeLock = lock.writeLock();

    public Data(int length) {
        this.buffer = new char[length];
        // 填充初始值
        Arrays.fill(this.buffer,'*');
    }

    public char[] read() throws InterruptedException {
        try {
            readLock.lock();
            return this.doRead();
        } finally {
            readLock.unlock();
        }
    }

    // 写入操作会把参数的字符填充整个数组
    public void write(char c) throws InterruptedException {
        try {
            writeLock.lock();
            this.doWrite(c);
        } finally {
            writeLock.unlock();
        }
    }

    private void doWrite(char c) {
        for (int i = 0; i < this.buffer.length; i++) {
            buffer[i] = c;
            // 模拟写入操作比读取操作更加耗时
            slower();
        }
    }

    // 读取操作使用数组复制出一个新的数组返回
    private char[] doRead() {
        return Arrays.copyOf(buffer, buffer.length);
    }


    private void slower() {
        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
