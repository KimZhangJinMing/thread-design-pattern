package active_object.example1.active;

// DisplayStringRequest已经确定了泛型是String
// DisplayStringRequest没有返回值,就不需要使用Future模式
public class DisplayStringRequest extends MethodRequest<String>{

    private final String str;

    DisplayStringRequest(Servant servant, String str) {
        super(servant, null);
        this.str = str;
    }

    @Override
    void execute() {
        this.servant.displayString(str);
    }
}
