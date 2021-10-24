package future.example2;


public class Host {

    public Data request(int count, char c) {
        System.out.println("  request(" + count + "," + c + ") BEGIN");
        FutureData futureData = new FutureData( () -> new RealData(count,c));
        new Thread(futureData).start();
        System.out.println("  request(" + count + "," + c + ") END");
        return futureData;
    }
}
