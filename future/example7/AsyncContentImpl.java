package future.example7;


// 异步拉取网页内容
public class AsyncContentImpl implements Content {

    private SyncContentImpl syncContent;
    private boolean ready = false;


    @Override
    public synchronized byte[] getBytes()  {
        while(!ready) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return syncContent.getBytes();
    }

    public synchronized void setSyncContent(SyncContentImpl syncContent) {
        if(ready) {
            return;
        }
        this.syncContent = syncContent;
        this.ready = true;
        notifyAll();
    }
}
