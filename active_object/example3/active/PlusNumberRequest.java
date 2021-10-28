package active_object.example3.active;


public class PlusNumberRequest extends MethodRequest<String>{

    private final String x;
    private final String y;

    PlusNumberRequest(Servant servant, FutureResult<String> future, String x, String y) {
        super(servant, future);
        this.x = x;
        this.y = y;
    }

    @Override
    void execute() {
        Result<String> result = this.servant.add(x, y);
        future.setRealResult(result);
    }
}
