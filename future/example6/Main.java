package future.example6;


import java.io.FileOutputStream;
import java.io.IOException;
import java.util.concurrent.ExecutionException;

// 多线程的方式Future(JDK的FutureTask)获取网页的内容, 耗时4093ms
public class Main {
    public static void main(String[] args) {
        long start = System.currentTimeMillis();

        Content content1 = Retriever.retrieve("http://news.baidu.com/");
        Content content2 = Retriever.retrieve("https://jiadian.jd.com/");

        try {
            saveToFile("./news.html",content1);
            saveToFile("./jd.html",content2);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        long end = System.currentTimeMillis();

        System.out.println("Elapsed time = " + (end - start));
    }

    public static void saveToFile(String filename, Content content) throws ExecutionException, InterruptedException {
        byte[] bytes = content.getBytes();
        System.out.println(Thread.currentThread().getName() + " :saving to " + filename);
        try {
            FileOutputStream out = new FileOutputStream(filename);
            for (int i = 0; i < bytes.length; i++) {
                out.write(bytes[i]);
            }
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
