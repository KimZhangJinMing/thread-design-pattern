package read_write_lock.example1;

import java.util.Random;

public class WriterThread extends Thread{

    private Data data;
    private String filler;
    // index是每个线程独有的，并不是static多个线程共享的
    // 也就是说每个线程都是按照字符串的顺序来读取的
    private int index = 0;
    private static Random random = new Random();

    public WriterThread(Data data,String filler) {
        this.data = data;
        this.filler = filler;
    }

    @Override
    public void run() {
        try {
            while(true) {
                data.write(nextChar());
                Thread.sleep(random.nextInt(3000));
            }
        } catch (InterruptedException e) {
            System.out.println(getName() + " interrupt. ");
        }
    }

    private char nextChar() {
        char c = filler.charAt(index);
        index = (index + 1) % filler.length();
//        index++;
//        if(index >= filler.length()) {
//            index = 0;
//        }
        return c;
    }
}
