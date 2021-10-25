package future.example6;

import java.util.concurrent.ExecutionException;

// 获取网页内容的数组
public interface Content {
    byte[] getBytes() throws ExecutionException, InterruptedException;
}
