package read_write_lock.example4;


public class AssignThread extends Thread {
    private DataBase<String,String> dataBase;
    private String key;
    private String value;

    public AssignThread(DataBase dataBase, String key, String value) {
        this.dataBase = dataBase;
        this.key = key;
        this.value = value;
    }

    @Override
    public void run() {
        try {
            while(true) {
                dataBase.assign(key,value);
                System.out.println(getName() + " assign : key=>" + key + " value=>" + value);
                // 模拟写入的耗时操作
                Thread.sleep(1000);
            }
        } catch (InterruptedException e) {
            System.out.println(getName() + " interrupt.");
        }
    }
}
