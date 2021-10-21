package single_threaded_execution.example2;

public class Gate {
    // 通过门的人数
    private int count = 0;
    private String name = "NoBody";
    private String birthPlace = "NoWhere";

    /*
     解决线程安全的办法:
     1. synchronized保证同一时刻只有一个线程执行
     2.
     */
    public synchronized void pass(String name, String birthPlace) {
        this.name = name;
        this.birthPlace = birthPlace;
        count++;
        check();
    }

    // 如果name和birthPlace的首字母不同,则记录的数据是异常的
    public void check() {
        if(name.charAt(0) != birthPlace.charAt(0)) {
            System.out.println("***Broken***: " + toString());
        }
    }

    // toString会被多个方法调用，应该加synchronized
    @Override
    public synchronized String toString() {
        return "Gate{" +
                "count=" + count +
                ", name='" + name + '\'' +
                ", birthPlace='" + birthPlace + '\'' +
                '}';
    }
}
