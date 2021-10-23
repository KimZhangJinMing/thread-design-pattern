package read_write_lock.example1;

// 读写锁
// 读读不冲突，读写冲突，写写冲突
public class ReadWriteLock {
    // 正在执行读取操作的线程个数
    private int readingCount;
    // 正在执行写入操作的线程个数
    private int writingCount;
    // 等待写入操作的线程个数
    private int waitingWriteCount;
    // true=写入优先，false=读取优先，在释放锁的时候改变状态
    private boolean writePrefer;

    public synchronized void readLock() throws InterruptedException {
        // 获取读锁的时候，需要满足的条件有：
        // 1.其他线程不能执行写操作 writingCount <= 0
        // 2.如果是写入优先，那么获取读锁的时候应该让给写锁 writePrefer && waitingWriteCount > 0

        // 使用的是Guarded Suspension的模式，需要对成立的条件取反进行等待
        // 不能使用Balking的模式是因为等待条件满足后需要继续往下执行，而不是终止运行
        while(writingCount > 0 || (writePrefer && waitingWriteCount > 0)){
            wait();
        }
        readingCount++;
    }

    public synchronized void readUnlock() {
        readingCount--;
        // 释放读锁，写入优先
        writePrefer = true;
        // 释放读锁后，通知其他正在等待获取读锁的线程可以竞争读锁啦
        notifyAll();
    }

    public synchronized void writeLock() throws InterruptedException {
        // 执行writeLock之后，正在等待wait的线程的个数
        waitingWriteCount++;

        // 获取写锁的时候，需要满足的条件
        // 1.不能有其他的线程正在操作，无论其他线程是在读还是写 readingCount <=0 && writingCount <=0
        // 2.正在等待执行的线程个数应该大于或等于0 waitingCount >= 0 默认成立
        try {
            while (readingCount > 0 || writingCount > 0) {
                wait();
            }
        } finally {
            waitingWriteCount--;
        }
        writingCount++;
    }

    public synchronized void writeUnlock() {
        writingCount--;
        //  释放了写锁，当然就读取优先啦
        writePrefer = false;
        notifyAll();
    }

}
