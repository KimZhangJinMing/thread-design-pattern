### thread-design-pattern

12种常用的多线程设计模式:



#### 1.Single Threaded Execution 

在同一时刻只允许一个线程执行，Java可以有以下几种实现方式:

* 使用synchronized关键字(singleThreadedExecution.example2)
* 使用Semaphore(1)来确保被保护的代码只有一个线程在执行(singleThreadedExecution.example3)



#### 2.Immutable

Imutable是创建不可变的类来确保线程安全。

在Java中用到Imutable模式的常见类有:

* java.lang.String
* java.math.BigInteger、java.math.BigDecimal
* java.util.Pattern
* java.lang.Integer等包装类



以下的情况会破坏Immutable:

* 将字段里保存的实例直接作为getter方法的返回值(immutable.example1)
* 将构造函数的参数中传入的实例直接赋值给字段(immutable.example2)



#### 3.Guarded Suspension

Guarded是‘被保护，被守卫’的意思，Suspension是‘暂停’的意思。

如果执行现在的处理会造成问题，就让执行处理的线程进行等待，直到达到了执行线程的必要条件。通过让线程等待来保证实例的安全性。

一个典型的例子就是线程通信的例子。(GuardedSuspension.example1)

在Single Threaded Execution模式中，只要有一个线程进入临界区，其他线程就无法进入，只能等待。而在Guarded Suspension模式中，线程是否等待取决于守护条件。**Guarded Suspension模式是在Single Threaded Execution模式的基础上附加了条件而形成的。**



#### 4.Balking

如果现在不适合执行这个操作，或者没必要执行这个操作，就停止处理，直接返回。

在Guarded Suspension模式中,guardedMeethod的守护条件成立之前，线程会一直等待。而在Balking模式中，当守护条件不成立时，线程立即返回。



### 知识点

#### 1.关于synchronized方法互斥的处理:  

* 当方法没有加synchronized修饰:

  * 该方法可以在任意时间由多个线程访问。即使有线程正在访问与该方法同个类的synchronized修饰的方法。

* 如果方法有synchronized修饰:
  * 一个实例一个锁，不同实例的synchronized的方法可以同时访问。当然,同个类内的静态synchronized方法和实例的synchronized方法也可以同时访问，因为加锁的对象不同。**synchronized的静态方法加锁对象是类名.class，synchronized的实例方法加锁对象是this**
  * 同个类的静态synchronized方法不能同时访问。因为加锁的对象都是Class类的实例**xxx.class**
  
  

#### 2.Thread.sleep

* Thread.sleep方法是Thread类的静态方法，虽然通过实例调用静态方法的写法在语法上没有错，但是容易让人产生误解。(参考basic/simple/SleepDemo)
* Thread.sleep虽然提供了纳米的控制方法，但这依赖于Java的运行环境。

* Thread.sleep暂停的是当前执行的线程。



#### 3. 判断题

1. start()和run()声明在Runnable接口中。 

   ❌,start()在Thread类中，run()在Runnable中。

2. 执行sleep方法后的线程仅在指定时间内待在等待队列中。

   ❌,只有调用wait()线程才会进入等待队列。

3. wait()的调用语句必须写在synchronized方法中。

   ❌,只要执行wait()的线程在执行时获取了对象实例的锁即可，可以是synchronized方法，可以是synchronized代码块，也可以是两者调用的其他方法中。



#### 4.wait/notify/notifyAll

wait/notify/notifyAll都需要获取锁后才能调用，否则会抛出illegalMonitorStateException.(basic/simple/WaitDemo)

obj.wait(): obj实例调用wait方法后，线程会释放锁，进入obj实例的一个等待队列，当以下的任意一种情况发生时，线程会退出等待队列。

* 调用obj实例的notify/notifyAll方法时
* 有其他线程的interrupt方法来唤醒线程
* wait方法超时



obj.notify/notifyAll: obj实例调用notify/notifyAll方法后，notify/notifyAll不会释放锁，直到执行结束才会释放锁。

当notify/notifyAll释放锁后，等待队列中的线程会争夺锁继续执行。

notify只会唤醒等待队列中的一个线程。

notifyAll会唤醒等待队列中的所有线程。

notify/notifyAll唤醒等待队列中的线程是随机的，无顺序的。



Wait/notify/notifyAll是Object类的方法，不是Thread类的方法，但是Thread类也是继承自Object类，所以Thread类中也有wait/notify/notifyAll方法。与其说这三个方法是针对线程的操作，倒不如说是针对实例的等待队列的操作。



#### 5.long/double的操作不是原子性的

在操作long/double类型的，为了保证线程安全，可以有以下几个解决方法：

* 在synchronized中操作，synchronized可以保证原子性
* 在声明字段时加上volatile关键字，volatile可以保证long/double操作的原子性
* 使用JUC包的原子类进行操作



#### 6.解决死锁的思路

死锁一般是由于交叉获取临界区资源引起的，以下思路可以参考：

* 多个线程按照一定的顺序去获取资源(singleThreadedExecution.example5)
* 将多个临界区的资源封装成一个对象，线程对对象加锁(singleThreadedExecution.example6)
* 外界条件破坏(GuardedSuspension.example3)



#### 7.线程安全/非安全的Queue

LinkedList实现了Queue接口，Queue接口中定义了offer/peek的方法，但是这两个方法是非线程安全的，在使用的时候需要使用synchronized或其他方法来保证线程安全。

LinkedBlockingQueue继承了Queue接口，并提供了take/put的方法，这两个方法是线程安全的，使用时无需使用synchronized来保证线程安全。