package future.example6;


import java.io.DataInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.URL;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

// 异步拉取网页内容
public class AsyncContentImpl implements Content {

    FutureTask<byte[]> futureTask;


    public AsyncContentImpl(String url) {
        this.getData(url);
    }


    private void getData(String urlStr) {
        Callable callable  = () -> {
            DataInputStream in = null;
            try {
                URL url = new URL(urlStr);
                in = new DataInputStream(url.openStream());
                byte[] buffer = new byte[1];
                int index = 0;
                try {
                    while (true) {
                        int c = in.readUnsignedByte();
                        // 扩容
                        if(buffer.length <= index) {
                            byte[] largerbuffer = new byte[buffer.length * 2];
                            System.arraycopy(buffer,0,largerbuffer,0,index);
                            buffer = largerbuffer;
                        }
                        buffer[index++] = (byte)c;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    in.close();
                }
                byte[] results = new byte[index];
                System.arraycopy(buffer,0,results,0,index);
                return results;
            } catch (EOFException e) {
                e.printStackTrace();
            }
            return null;
        };
        futureTask = new FutureTask<byte[]>(callable);
        new Thread(futureTask).start();
    }

    @Override
    public byte[] getBytes() throws ExecutionException, InterruptedException {
        return futureTask.get();
    }
}
