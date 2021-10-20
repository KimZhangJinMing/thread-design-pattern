package GuardedSuspension.example2;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

//  请求队列,存放请求的对象，提供put和get的方法,使用线程安全的LinkedBlockingQueue
public class RequestQueue {

    // LinkedBlockingQueue是非线程安全的,不需要由synchronized来保证线程安全
    // BlockingQueue是Queue的子接口
    private BlockingQueue<Request> queue = new LinkedBlockingDeque<>();

    // BlockingQueue的take方法是线程安全的，不需要synchronized来保证线程安全
    // 如果队列中没有元素,take方法会阻塞,也就是说while-wait由take方法内部实现了
    public  Request getRequest() {
        Request req = null;
        try {
            req = queue.take();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return req;
    }

    // BlockingQueue的put方法也是线程安全的
    public void PutRequest(Request request) {
        try {
            queue.put(request);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
