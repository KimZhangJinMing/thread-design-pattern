package active_object.example1.active;


// 充当Producer-Customer模式中的Channel
// 能够线程安全地将MakeStringRequest|DisplayStringRequest从Producer传递给Customer角色
public class SchedulerThread extends Thread {

    private final ActivationQueue requestQueue;

    public SchedulerThread(ActivationQueue requestQueue) {
        this.requestQueue = requestQueue;
    }

    // 调用invoke方法即是把请求放到请求队列中去
    // Producer
    public void invoke(MethodRequest methodRequest) {
        try {
            requestQueue.putRequest(methodRequest);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    // 调用run方法即是从请求队列中取出请求执行
    // Customer
    @Override
    public void run() {
        try {
            while(true) {
                MethodRequest methodRequest = requestQueue.takeRequest();
                methodRequest.execute();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
