package single_threaded_execution.example4;

/**
 * 如果需要改造成线程安全的SecurityGate类，需要在三个方法前都加synchronized
 */
public class SecurityGate {
    private int count;

    // 模拟count++的拆分操作
    public  void enter() {
//        count++;
        int tempCount = count;
        Thread.yield();
        count = tempCount + 1;
    }

    public  void exit() {
//        count--;
        int tempCount = count;
        Thread.yield();
        count = tempCount - 1;
    }

    // get方法加synchronized是为了保证可见性
    // 或者不加synchronized，也可是使用volatile来保证可见性
    // 因为如果在enter和exit计算过程中，要保证线程的可见性，getCount的值才是准确的
    public  int getCount() {
        return count;
    }
}
