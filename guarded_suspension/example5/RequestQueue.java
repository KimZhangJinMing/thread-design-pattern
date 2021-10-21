package guarded_suspension.example5;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;

// 使用超时的机制来预防死锁的产生
public class RequestQueue {

    private BlockingQueue<Request> queue = new LinkedBlockingDeque<>();

    // 默认超时时间
    private final long defaultTimeout = 3;

    // BlockingQueue的take方法是线程安全的，不需要synchronized来保证线程安全
    // 如果队列中没有元素,take方法会阻塞,也就是说while-wait由take方法内部实现了
    public Request getRequest() {
        Request req = null;
        try {
            // poll方法，如果超时，会返回null
            req = queue.poll(defaultTimeout, TimeUnit.SECONDS);
            if(req == null) {
                throw new LivenessException("getRequest timeout！");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return req;
    }

    // BlockingQueue的put方法也是线程安全的
    public void putRequest(Request request) {
        try {
            // offer方法，如果超时会返回false
            boolean result = queue.offer(request,defaultTimeout,TimeUnit.SECONDS);
            if(!result) {
                throw new LivenessException("putRequest timeout");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
