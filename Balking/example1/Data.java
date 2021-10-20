package Balking.example1;


import java.io.FileWriter;
import java.io.IOException;

// 数据类
public class Data {
    private final String fileName;
    // 修改的文件内容
    private String content;
    // 如果修改后的数据未进行保存,该值为true
    private boolean changed = true;
    public Data(String fileName, String content) {
        this.fileName = fileName;
        this.content = content;
    }

    public synchronized void change(String content) {
        this.content = content;
        // 修改了内容并为保存
        this.changed = true;
    }

    public synchronized void save() throws IOException {
        // 文件没有修改过，或者修过过后已经保存了
        if(!changed) {
            return;
        }
        doSave();
        changed = false;
    }

    // 保存内容到文件，每次都会覆盖掉原来文件的内容
    // 私有方法,且save()已经保证了线程安全，这里可以不加synchronized
    private void doSave() throws IOException {
        System.out.println(Thread.currentThread().getName() + "calls doSave method, content =" + content);
        FileWriter writer = new FileWriter(fileName);
        writer.write(content);
        writer.close();
    }
}
