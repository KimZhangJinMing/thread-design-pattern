package basic.exchange;

import java.util.concurrent.Exchanger;

public class CustomerThread extends Thread{
    private Exchanger<char[]> exchanger;
    private char[] buffer;

    public CustomerThread(String name,Exchanger exchanger,char[] buffer) {
        super(name);
        this.exchanger = exchanger;
        this.buffer = buffer;
    }

    @Override
    public void run() {
        try {
            while(true) {
                // 输出buffer
                System.out.println(getName() + "===BEFORE EXCHANGE===");
                print(buffer);
                // 交换缓冲区
                buffer = exchanger.exchange(buffer);
                System.out.println(getName() + "===AFTER EXCHANGE===");
                print(buffer);
            }
        } catch (InterruptedException e) {
            System.out.println(getName() + " interrupt. ");
        }
    }

    private void print(char[] buffer) {
        for (char c : buffer) {
            System.out.print(c + " ");
        }
        System.out.println();
    }
}
