package active_object.example3.active;


// 表示请求(MakeStringRequest|DisplayStringRequest)的抽象
// 命令模式,请求中包含一个执行请求的对象的实例,这里是Servant
// 命令模式一般使用抽象类
public abstract class MethodRequest<T> {

    // 子类需要用到,使用protected
    protected final Servant servant;
    protected final FutureResult<T> future;

    MethodRequest(Servant servant, FutureResult<T> future) {
        this.servant = servant;
        this.future = future;
    }

    abstract void execute();
}
