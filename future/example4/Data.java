package future.example4;

import java.util.concurrent.ExecutionException;

public interface Data {
    String getContent() throws ExecutionException;
}
