package future.example5;


import java.io.DataInputStream;
import java.io.IOException;
import java.net.URL;

// 同步方式实现获取网页的内容
public class SyncContentImpl implements Content{

    private byte[] contentBytes;

    public SyncContentImpl(String url) {
        this.getData(url);
    }

    public void getData(String urlStr) {
        DataInputStream in = null;
        try {
            URL url = new URL(urlStr);
            in = new DataInputStream(url.openStream());
            byte[] buffer = new byte[1];
            int index = 0;
            try {
                while (true) {
                    int c = in.readUnsignedByte();
                    // 扩容
                    if(buffer.length <= index) {
                        byte[] largerbuffer = new byte[buffer.length * 2];
                        System.arraycopy(buffer,0,largerbuffer,0,index);
                        buffer = largerbuffer;
                    }
                    buffer[index++] = (byte)c;
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                in.close();
            }
            this.contentBytes = new byte[index];
            System.arraycopy(buffer,0,contentBytes,0,index);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public byte[] getBytes() {
        return contentBytes;
    }
}
