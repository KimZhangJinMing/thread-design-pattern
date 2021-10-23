package read_write_lock.example2;

public class ReaderThread extends Thread{

    private Data data;

    public ReaderThread(Data data) {
        this.data = data;
    }

    @Override
    public void run() {
        try {
            while(true) {
                char[] buffer = data.read();
                System.out.println(getName() + " read : " + new String(buffer));
            }
        } catch (InterruptedException e) {
            System.out.println(getName() + " interrupt.");
        }
    }
}
