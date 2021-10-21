package balking.example1;

import java.io.IOException;

// 定期保存数据内容
public class SaverThread extends Thread{

    private Data data;

    public SaverThread(String name, Data data) {
        super(name);
        this.data = data;
    }

    @Override
    public void run() {
        // 发生异常就退出线程
        try {
            while(true) {
                data.save();
                // 每隔1s保存一次数据
                Thread.sleep(1000);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
