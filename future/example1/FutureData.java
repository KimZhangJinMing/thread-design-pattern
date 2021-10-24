package future.example1;

public class FutureData implements Data{

    private RealData realData;
    // 表示是否已经给realData数据赋值完成
    private boolean ready = false;

    @Override
    public synchronized String getContent() {
        // Guarded Suspension 确保realData已经可以获取
        while(!ready) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return realData.getContent();
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
