package Balking.example1;

public class Main {
    public static void main(String[] args) {
        Data data = new Data("data.txt", "(empty)");
        // 多个线程进行数据的保存，不会重复保存(没有重复的编号)
        new ChangerThread("ChangerThread",data).start();
        new SaverThread("SaverThread",data).start();
    }
}
