package thread_per_message.example6;

public class Blackhole {

    public static void enter(Object obj) {
        System.out.println("Step 1");
        // Step1 输出后能输出 Step2，说明magic是开启了一个新的线程，否则要等magic方法执行完成后才能输出Step2
        // Step3 不能输出，说明获取不到obj的锁，也就是magic的子线程一直拥有着obj的锁
        magic(obj);
        System.out.println("Step 2");
        synchronized (obj) {
            System.out.println("Step 3(never reached here)");
        }
    }

    public static void magic(Object obj) {
        new Thread(() -> {
            // 子线程获取obj锁，一直不释放，enter方法无法获取到obj锁就无法继续往下执行了
            synchronized (obj) {
                while(true){}
            }
        }).start();
    }


//    public static void magic(Object obj) {
//        Thread thread = new Thread(){
//            // 重写Thread类的run方法
//            @Override
//            public void run() {
//                // 子线程获取obj的锁，且一直不释放
//                synchronized (obj) {
//                    synchronized (this) {
//                        this.setName("Locked");
//                        this.notifyAll();
//                    }
//                    while(true){}
//                }
//            }
//        };
//        synchronized (thread) {
//            thread.setName("");
//            thread.start();
//            //
//            while(thread.getName().equals("")) {
//                try {
//                    thread.wait();
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//    }
}
