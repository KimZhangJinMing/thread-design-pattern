package future.example3;

import java.util.concurrent.ExecutionException;

public class FutureData implements Data {

    private RealData realData;
    // 表示是否已经给realData数据赋值完成
    private boolean ready = false;
    // 异常信息
    private ExecutionException exception;

    @Override
    public synchronized String getContent() throws ExecutionException {
        // Guarded Suspension 确保realData已经可以获取
        while(!ready) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        // 处理异常
        if(exception != null) {
            throw exception;
        }
        return realData.getContent();
    }

    public synchronized void setException(Throwable throwable) {
        if(ready) {
            return;
        }
        this.exception = new ExecutionException(throwable);
        this.ready = true;
        notifyAll();
    }

    public synchronized void setRealData(RealData realData) {
        if(ready) {
            return;
        }
        this.realData = realData;
        ready = true;
        // 通知可以通过getContent方法来获取结果啦
        notifyAll();
    }
}
