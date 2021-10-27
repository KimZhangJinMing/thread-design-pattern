package thread_specific_storage.example2;


import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class Log {

    // 如果要给多个线程使用，每个线程需要一个PrintWriter的实例
    // 因为一个PrintWriter实例只能指定一个文件存储
    private PrintWriter writer;

   public Log(String filename) {
       System.out.println(Thread.currentThread().getName() + " create writer: " + filename);
       try {
           writer = new PrintWriter(new FileWriter(filename));
       } catch (IOException e) {
           e.printStackTrace();
       }
   }

    // 写日志
    public void println(String s) {
        writer.println(s);
    }

    // 关闭日志
    public void close() {
        writer.println("===END OF LOG===");
        writer.close();
    }



}
