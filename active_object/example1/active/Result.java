package active_object.example1.active;

// Result是泛型类,由子类决定返回的类型是什么
public interface Result<T> {

    T getResultValue();
}
