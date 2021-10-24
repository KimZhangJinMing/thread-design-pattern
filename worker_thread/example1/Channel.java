package worker_thread.example1;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

// 接收工作请求并将工作请求交给工人线程的类
public class Channel {

    private Request[] requestQueue;
    private final Thread[] threadPool;
    // 当前队列中请求的个数
    private int count = 0;
    private int head = 0;
    private int tail = 0;

    public Channel(int size) {
        requestQueue = new Request[size];
        threadPool = new Thread[size];
        for (int i = 0; i < threadPool.length; i++) {
            threadPool[i] = new WorkerThread("Worker-" + i,this);
        }
    }

    public void startWorkers() {
        for (int i = 0; i < threadPool.length; i++) {
            threadPool[i].start();
        }
    }

    public synchronized Request takeRequest() throws InterruptedException {
        while (count <= 0){
            wait();
        }
        Request request = requestQueue[head];
        head = (head + 1) % requestQueue.length;
        count--;
        notifyAll();
        return request;
    }

    public synchronized void putRequest(Request request) throws InterruptedException {
        while(count >= requestQueue.length) {
            wait();
        }
        requestQueue[tail] = request;
        tail = (tail + 1) % requestQueue.length;
        count++;
        notifyAll();
    }
}
