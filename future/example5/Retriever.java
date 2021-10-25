package future.example5;


public class Retriever {

    public static Content retrieve(String url) {
        return new SyncContentImpl(url);
    }

}
