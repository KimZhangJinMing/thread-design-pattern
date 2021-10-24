package future.example3;

public class Host {

    public Data request(int count, char c) {
        System.out.println("  request(" + count + "," + c + ") BEGIN");
        FutureData futureData = new FutureData();
        // 启动一个新线程，用于创建RealData的实例
        new Thread(() -> {
            // 如果发生异常怎么处理？
            try {
                RealData realData = new RealData(count,c);
                futureData.setRealData(realData);
            } catch (Exception e) {
                futureData.setException(e);
            }
        }).start();
        System.out.println("  request(" + count + "," + c + ") END");
        return futureData;
    }
}
