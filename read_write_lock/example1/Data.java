package read_write_lock.example1;

import java.util.Arrays;

// 线程读取和写入操作的数据类
public class Data {
    private char[] buffer;
    private ReadWriteLock lock = new ReadWriteLock();

    public Data(int length) {
        this.buffer = new char[length];
        // 填充初始值
        Arrays.fill(this.buffer,'*');
    }

    public char[] read() throws InterruptedException {
        try {
            lock.readLock();
            return this.doRead();
        } finally {
            lock.readUnlock();
        }
    }

    // 写入操作会把参数的字符填充整个数组
    public void write(char c) throws InterruptedException {
        try {
            lock.writeLock();
            this.doWrite(c);
        } finally {
            lock.writeUnlock();
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
