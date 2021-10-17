package basic.semaphore;

public class UserThread extends Thread{

    private BoundedResource boundedResource;

    public UserThread(BoundedResource boundedResource) {
        this.boundedResource = boundedResource;
    }

    @Override
    public void run() {
        while (true) {
            this.boundedResource.use();
        }
    }
}
