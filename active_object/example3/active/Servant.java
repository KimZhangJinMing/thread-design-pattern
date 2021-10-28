package active_object.example3.active;


import java.math.BigInteger;

// 实际执行ActiveObject的类
public class Servant implements ActiveObject {

    // 生成一个由fillchar组成的count长度的字符串
    // 这里返回RealResult，由Proxy再去包装成FutureResult
    @Override
    public Result<String> makeString(int count, char fillchar) {
        char[] buffer = new char[count];
        for (int i = 0; i < buffer.length; i++) {
            buffer[i] = fillchar;
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return new RealResult<>(new String(buffer));
    }

    @Override
    public void displayString(String str) {
        System.out.println("displayString: " + str);
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Result<String> add(String x, String y) {
        try {
            BigInteger result = new BigInteger(x).add(new BigInteger(y));
            return new RealResult<>(result.toString());
        } catch (Exception e) {
            return new RealResult<>(null);
        }
    }
}
