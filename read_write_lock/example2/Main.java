package read_write_lock.example2;


public class Main {
    public static void main(String[] args) {
        Data data = new Data(10);
        String filler = "abc";
        new ReaderThread(data).start();
        new ReaderThread(data).start();
        new ReaderThread(data).start();
        new ReaderThread(data).start();
        new WriterThread(data,filler).start();
        new WriterThread(data,filler).start();
    }
}
