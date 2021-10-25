package future.example7;


public class Retriever {


    public static Content retrieve(String url) {
        AsyncContentImpl future = new AsyncContentImpl();

        // 开启新的线程
        new Thread(() -> future.setSyncContent(new SyncContentImpl(url))).start();

        return future;
    }

}
