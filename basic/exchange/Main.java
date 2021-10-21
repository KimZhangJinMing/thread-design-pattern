package basic.exchange;

import java.util.concurrent.Exchanger;

// 演示如何使用Exchange交换两个缓冲区
public class Main {
    public static void main(String[] args) {
        char[] buffer1 = new char[10];
        char[] buffer2 = new char[10];
        Exchanger<char[]> exchanger = new Exchanger<>();
        new ProducerThread("producerThread",exchanger,buffer1).start();
        new CustomerThread("customerThread",exchanger,buffer2).start();
    }
}
