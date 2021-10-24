package future.example4;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class FutureData extends FutureTask<RealData> implements Data {


    public FutureData(Callable<RealData> callable) {
        super(callable);
    }

    // 在这里直接抛出ExecutionException
    @Override
    public String getContent() throws ExecutionException{
        String string = null;
        try {
            string = get().getContent();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return string ;
    }
}
