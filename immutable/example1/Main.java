package immutable.example1;

public class Main {
    public static void main(String[] args) {
        UserInfo userInfo = new UserInfo("Alice","China");
        System.out.println(userInfo);

        StringBuffer info = userInfo.getInfo();
        info.append("append");
        // userInfo不是Immutable类
        System.out.println(userInfo);
    }
}
