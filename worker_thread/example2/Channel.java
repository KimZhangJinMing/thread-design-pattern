package worker_thread.example2;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

// 接收工作请求并将工作请求交给工人线程的类
public class Channel {

    private BlockingQueue<Request> requestQueue;
    private final WorkerThread[] threadPool;

    public Channel(int size) {
        requestQueue = new ArrayBlockingQueue<>(size);
        threadPool = new WorkerThread[size];
        for (int i = 0; i < threadPool.length; i++) {
            threadPool[i] = new WorkerThread("Worker-" + i,this);
        }
    }

    public void startWorkers() {
        for (int i = 0; i < threadPool.length; i++) {
            threadPool[i].start();
        }
    }

    public Request takeRequest() throws InterruptedException {
        return requestQueue.take();
    }

    public void putRequest(Request request) throws InterruptedException {
        requestQueue.put(request);
    }

    public void stopAllWorker() {
        for (int i = 0; i < threadPool.length; i++) {
            threadPool[i].stopThread();
        }
    }
}
