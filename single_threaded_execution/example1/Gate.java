package single_threaded_execution.example1;

public class Gate {
    // 通过门的人数
    private int count = 0;
    private String name = "NoBody";
    private String birthPlace = "NoWhere";

    // pass方法不是同步的，可能name赋值了，birthPlace被别的线程赋值了,就会导致数据错乱
    // 线程改写共享的实例字段时并未考虑其他线程的操作
    public void pass(String name, String birthPlace) {
        this.name = name;
        this.birthPlace = birthPlace;
        count++;
        check();
    }

    // 如果name和birthPlace的首字母不同,则记录的数据是异常的
    // check方法虽然也使用了name和birthPlace,但是check方法只被pass方法调用,可以保证线程安全
    // 所以可以不加synchronized提高性能
    private void check() {
        if(name.charAt(0) != birthPlace.charAt(0)) {
            System.out.println("***Broken***: " + toString());
        }
    }

    // 由于pass方法不是线程安全的,toString方法输出的数据可能也会错乱
    @Override
    public String toString() {
        return "Gate{" +
                "count=" + count +
                ", name='" + name + '\'' +
                ", birthPlace='" + birthPlace + '\'' +
                '}';
    }
}
