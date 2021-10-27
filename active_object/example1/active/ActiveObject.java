package active_object.example1.active;

// Result<T>是泛型的，但在ActiveObject中就规定是String类型
// 所以ActiveObject不需要是泛型类
public interface ActiveObject {
    // 生产字符串
    Result<String> makeString(int count, char fillchar);

    // 输出字符串
    void displayString(String str);
}
