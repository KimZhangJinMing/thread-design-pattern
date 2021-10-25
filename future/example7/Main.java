package future.example7;


import java.io.FileOutputStream;
import java.io.IOException;

// 多线程的方式Future(自己实现的Future)获取网页的内容, 耗时4690ms
public class Main {
    public static void main(String[] args) {
        long start = System.currentTimeMillis();

        Content content1 = Retriever.retrieve("http://news.baidu.com/");
        Content content2 = Retriever.retrieve("https://jiadian.jd.com/");

        saveToFile("./news.html",content1);
        saveToFile("./jd.html",content2);


        long end = System.currentTimeMillis();

        System.out.println("Elapsed time = " + (end - start));
    }

    public static void saveToFile(String filename, Content content) {
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
