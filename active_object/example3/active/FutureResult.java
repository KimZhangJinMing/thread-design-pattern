package active_object.example3.active;


// Future包装的RealResult
public class FutureResult<T> implements Result<T> {

    private Result<T> result;
    private boolean ready = false;

    @Override
    public synchronized T getResultValue() {
        // 阻塞等待setRealResult的完成
        while (!ready) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return result.getResultValue();
    }

    // 提供给外界一个设置RealResult的口子
    public synchronized void setRealResult(Result<T> result) {
        if(ready) {
            return;
        }
        this.result = result;
        this.ready = true;
        // 通知getResultValue可以返回数据了
        notifyAll();
    }
}
