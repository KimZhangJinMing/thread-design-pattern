package active_object.example3.active;


import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

// 存放请求(MakerStringRequest|DisplayStringRequest)的队列
// 使用线程安全的BlockingQueue
public class ActivationQueue {

    private final static int MAX_METHOD_REQUEST = 100;
    private BlockingQueue<MethodRequest> requestQueue;


    public ActivationQueue() {
        this.requestQueue = new ArrayBlockingQueue<>(MAX_METHOD_REQUEST);
    }


    public void putRequest(MethodRequest methodRequest) throws InterruptedException {
        this.requestQueue.put(methodRequest);
    }

    public MethodRequest takeRequest() throws InterruptedException {
        return this.requestQueue.take();
    }
}
