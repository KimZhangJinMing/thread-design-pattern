package active_object.example3.active;


// 充当Producer-Customer模式中的Channel
// 能够线程安全地将MakeStringRequest|DisplayStringRequest从Producer传递给Customer角色
// Scheduler角色会把MethodRequest角色放入ActivationQueue角色或者从ActivationQueue角色取出MethodRequest角色
// 因此,Scheduler角色可以判断下次要执行哪个请求
// 如果想实现请求调度的判断逻辑,可以将它们实现在Scheduler角色中
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
