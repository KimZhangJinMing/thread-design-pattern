package active_object.example3.active;


// 生产ActiveObject的工厂
public class ActiveObjectFactory {

    public static ActiveObject createActiveObject() {
        Servant servant = new Servant();
        ActivationQueue requestQueue = new ActivationQueue();
        SchedulerThread schedulerThread = new SchedulerThread(requestQueue);
        Proxy proxy = new Proxy(servant, schedulerThread);
        schedulerThread.start();
        return proxy;
    }
}
