package future.example3;

import java.util.concurrent.ExecutionException;

public interface Data {
    String getContent() throws ExecutionException;
}
