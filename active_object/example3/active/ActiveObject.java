package active_object.example3.active;

// Result<T>是泛型的，但在ActiveObject中就规定是String类型
// 所以ActiveObject不需要是泛型类
// ActiveObject角色定义了向Client角色提供的接口
public interface ActiveObject {
    // 生产字符串
    Result<String> makeString(int count, char fillchar);

    // 输出字符串
    void displayString(String str);

    // 整数相加
    Result<String> add(String x, String y);
}
