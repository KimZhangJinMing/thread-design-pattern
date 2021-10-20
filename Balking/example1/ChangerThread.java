package Balking.example1;

import java.io.IOException;
import java.util.Random;

// 手动修改数据
public class ChangerThread extends Thread{

    private Data data;
    private Random random = new Random();

    public ChangerThread(String name,Data data) {
        super(name);
        this.data = data;
    }

    @Override
    public void run() {
        // 发生异常，线程能正常结束
        try {
            for (int i = 0; i < 100; i++) {
                // 修改内容
                data.change("No" + i );
                // 随机休眠异一定时间
                Thread.sleep(random.nextInt(100));
                // 进行数据保存
                data.save();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
