package active_object.example3.active;


// Proxy将方法的调用(Servant中的makeString|displayString)转换成对象(makeStringRequest|displayRequest)
// Proxy是active包内的对象,不暴露给外界
// Proxy实现了ActiveObject接口,外界可以当其是ActiveObject来访问它
class Proxy implements ActiveObject {

    private final Servant servant;
    private final SchedulerThread schedulerThread;

    public Proxy(Servant servant, SchedulerThread schedulerThread) {
        this.servant = servant;
        this.schedulerThread = schedulerThread;
    }

    @Override
    public Result<String> makeString(int count, char fillchar) {
        FutureResult<String> future = new FutureResult<>();
        schedulerThread.invoke(new MakeStringRequest(servant,future,count,fillchar));
        return future;
    }

    @Override
    public void displayString(String str) {
        schedulerThread.invoke(new DisplayStringRequest(servant,str));
    }

    @Override
    public Result<String> add(String x, String y) {
        FutureResult<String> future = new FutureResult<>();
        schedulerThread.invoke(new PlusNumberRequest(servant,future,x,y));
        return future;
    }


}
