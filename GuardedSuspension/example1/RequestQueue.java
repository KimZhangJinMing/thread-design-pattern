package GuardedSuspension.example1;

import java.util.LinkedList;
import java.util.Queue;

//  请求队列,存放请求的对象，提供put和get的方法
public class RequestQueue {

    // 使用链表实现的队列
    // LinkedList是非线程安全的,但是可以由synchronized来保证线程安全
    private Queue<Request> queue = new LinkedList<>();

    // 从请求队列中获取请求对象，先进先出，获取第一个加入队列的Request对象
    // 如果队列中还没有元素的话,阻塞等待
    // 等到队列中有元素之后才开始取，这里等待的条件就是 queue.peek != null
    // LinkedList实现的队列不是线程安全的，但是这个方法加了synchronized方法
    // 能保证在同一时刻只有一个线程来getRequest，也就是说synchronized保证了线程安全
    public synchronized Request getRequest() {
        while(queue.peek() == null){
            try {
                // 调用wait方法需要获取锁,而synchronized提供了这把锁
                // wait方法执行后，会释放锁，并进入this实例的等待队列
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        // 移除元素，并返回元素
        // 如果元素在队列中不存在，会跑出NoSuchElementException
        // 但是在这里永远不会抛出这个异常，因为while阻塞保证了队列中有了元素才会执行到这一行代码
        return queue.remove();
    }


    public synchronized void PutRequest(Request request) {
        queue.offer(request);
        // 唤醒this实例的等待队列中的所有线程去getRequest
        // 但是等待队列中只会有一个线程获取到notify释放的锁继续执行
        // 这里就破坏了getRequest一直阻塞的条件，线程之间的通讯就是这样
        // 一个线程阻塞了等待其他线程破坏了阻塞的等待条件
        notifyAll();
    }
}
