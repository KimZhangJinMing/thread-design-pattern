package guarded_suspension.example1;

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
                // 如果程序出现错误，一直没有notify/notifyAll
                // 线程就会一直阻塞，所以可以在wait方法中指定超时时间
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        // 移除元素，并返回元素
        // 如果元素在队列中不存在，会抛出NoSuchElementException
        // 但是在这里永远不会抛出这个异常，因为while阻塞保证了队列中有了元素才会执行到这一行代码
        return queue.remove();
    }

    // 这里notifyAll和offer的语句顺序可以互换,也不会对程序有什么影响
    // 如果是先执行notifyAll,方法也还没释放锁,而是会执行完方法之后才释放锁
    // 只有释放锁之后,getRequest的wait方法才会去竞争锁
    public synchronized void PutRequest(Request request) {
        queue.offer(request);
        // 唤醒this实例的等待队列中的所有线程去getRequest
        // 但是等待队列中只会有一个线程获取到notify释放的锁继续执行
        // 这里就破坏了getRequest一直阻塞的条件，线程之间的通讯就是这样
        // 一个线程阻塞了等待其他线程破坏了阻塞的等待条件
        notifyAll();
    }
}

/*
1. getRequest方法中的while能否替换成if?
    notify/notifyAll只不过是守护条件的触发器，在wait触发继续往下执行时，必须要再次检查一下守护条件
    假设队列中只有一个元素时,对于if的条件，多个线程同时都成立，都在等待队列中等着获取锁往下执行remove。
    此时只要有一个线程执行了remove方法了，其他的线程就不应该再执行remove方法了。
    否则remove方法会抛出NoSuchElementException
    在触发wait之后继续往下执行时,if不能再次进行守护条件的检查
    而while可以再进行一次守护条件的检查后才继续往下执行
2. 如果使用的是notify,而不是notifyAll,是不是就可以使用if?
    在这个例子中是没有问题的。因为使用notify，每次只会唤醒一个线程，即使使用if，也只会有一个线程往下执行。
    但是这种方式不推荐。
3. getRequest方法中，将synchronized的范围只包含wait方法，行不行？
    不行，首先LinkedList的remove方法不是线程安全的，需要synchronized来保证线程安全。
    其次，检查条件放在了synchronized代码块外面，假设队列中只有一个元素时，
    多个线程同时都满足了条件，都在等待队列中等着获取锁往下执行remove，
    此时只要有一个线程执行了remove方法了，其他的线程就不应该再执行remove方法了。
    否则remove方法会抛出NoSuchElementException
4. 可以将try..catch移到while外面吗？
    如果将try..catch移到while外面，一旦线程被中断，也就是抛出InterruptedException，
    while将不会再检查条件,而catch住异常后，程序继续往下执行，不符合程序编写的逻辑
5. getRequest方法中将wait方法替换成Thread.sleep?
    sleep方法不会释放锁， 如果线程先执行了getRequest方法，其他线程就无法获取到锁，
    也就无法调用putRequest方法，这样导致while的条件一直都是null，一直都在sleep
    但是sleep的参数设置的时间,每隔多长时间线程就会醒来重新判断一下条件是否成立
    但是只要不释放锁，条件就一直不成立。这样程序一直在运行，但是却没什么进展的现象，称为活锁
 */
