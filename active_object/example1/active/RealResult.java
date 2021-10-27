package active_object.example1.active;


// 返回数据的真实类型
public class RealResult<T> implements Result<T>{

    private final T resultValue;

    // 真实的返回结果通过构造函数来传值
    public RealResult(T resultValue) {
        this.resultValue = resultValue;
    }

    @Override
    public T getResultValue() {
        return this.resultValue;
    }
}
