package future.example6;


public class Retriever {

    public static Content retrieve(String url) {
        return new AsyncContentImpl(url);
    }

}
