package future.example2;

// 一个需要花费很长时间才能创建实例的类
public class RealData implements Data {

    private String content;

    // 模拟创建RealData很耗时
    public RealData(int count, char c) {
        System.out.println("   making RealData(" + count + "," + c + ") BEGIN");
        char[] buffer = new char[count];
        for (int i = 0; i < buffer.length; i++) {
            buffer[i] = c;
            slowly();
        }
        System.out.println("   making RealData(" + count + "," + c + ") END");
        this.content = new String(buffer);
    }

    private void slowly() {
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
