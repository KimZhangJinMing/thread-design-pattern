package guarded_suspension.example5;


public class TalkThread extends Thread{

    private RequestQueue input;
    private RequestQueue output;

    public TalkThread(RequestQueue input, RequestQueue output) {
        this.input = input;
        this.output = output;
    }

    @Override
    public void run() {
        for (int i = 0; i < 20; i++) {
            System.out.println("===BEGIN===");
            Request request = input.getRequest();
            System.out.println(Thread.currentThread().getName() + " getRequest: " + request.getName());

            // 从input队列中取出request对象，添加!后放入output队列
            Request newRequest = new Request(request.getName() + "!");
            output.putRequest(newRequest);
            System.out.println(Thread.currentThread().getName() + " putRequest: " + newRequest.getName());
        }
    }
}
