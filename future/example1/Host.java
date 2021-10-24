package future.example1;

public class Host {

    public Data request(int count,char c) {
        System.out.println("  request(" + count + "," + c + ") BEGIN");
        FutureData futureData = new FutureData();
        // 启动一个新线程，用于创建RealData的实例
        new Thread(() -> {
            RealData realData = new RealData(count,c);
            futureData.setRealData(realData);
        }).start();
        System.out.println("  request(" + count + "," + c + ") END");
        return futureData;
    }
}
