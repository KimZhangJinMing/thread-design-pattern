package active_object.example2.active;

import java.util.concurrent.Future;

public interface ActiveObject {
    Future<String> makeString(int count, char fillchar);

    void displayString(String str);

    void shutdown();
}
