package active_object.example3.active;

// MakeStringRequest已经确定了泛型是String
// MakeStringRequest的execute方法有返回值,使用Future来接收返回值

public class MakeStringRequest extends MethodRequest<String> {

    private final int count;
    private final char fillchar;

    MakeStringRequest(Servant servant, FutureResult<String> future, int count, char fillchar) {
        super(servant, future);
        this.count = count;
        this.fillchar = fillchar;
    }

    @Override
    void execute() {
        Result<String> result = this.servant.makeString(count, fillchar);
        // 虽然execute的返回值是void,但是可以在接口中创建一个接收返回结果的实例
        // 通过把返回值设置给这个实例对象,带出去返回值
        // 这里的实例对象就是FutureResult对象
        future.setRealResult(result);
    }
}
