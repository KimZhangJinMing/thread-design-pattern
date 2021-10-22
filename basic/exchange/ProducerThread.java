package basic.exchange;

import com.sun.scenario.effect.impl.prism.PrImage;

import java.util.Arrays;
import java.util.concurrent.Exchanger;
import java.util.stream.Stream;

public class ProducerThread extends Thread{
    private char[] buffer;
    private Exchanger<char[]> exchanger;
    // 用来生成26个字母
    private int index;

    public ProducerThread(String name,Exchanger<char[]> exchanger,char[] buffer) {
        super(name);
        this.exchanger = exchanger;
        this.buffer = buffer;
    }

    @Override
    public void run() {
        try {
            while (true) {
                // 产生字符串
                for (int i = 0; i < buffer.length; i++) {
                    buffer[i] = nextChar();
                }
                System.out.println(getName() + "===BEFORE EXCHANGE===");
                print(buffer);
                // 交换缓冲区
                buffer = exchanger.exchange(buffer);
                System.out.println(getName() + "===AFTER EXCHANGE===");
                print(buffer);
            }
        } catch (InterruptedException e) {
            System.out.println(getName() + " interrupt.");
        }
    }

    private void print(char[] buffer) {
        for (char c : buffer) {
            System.out.print(c + " ");
        }
        System.out.println();
    }
    private char nextChar() throws InterruptedException {
        char c = (char)('A' + index % 26);
        index++;
        Thread.sleep(1000);
        return c;
    }
}
