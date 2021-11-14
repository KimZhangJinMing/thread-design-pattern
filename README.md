- [线程设计模式](#线程设计模式)
  - [1.Single Threaded Execution](#1single-threaded-execution)
  - [2.Immutable](#2immutable)
  - [3.Guarded Suspension](#3guarded-suspension)
  - [4.Balking](#4balking)
  - [5.Producer-Consumer](#5producer-consumer)
  - [6.Read Write Lock](#6read-write-lock)
  - [7.Thread-Per-Message](#7thread-per-message)
  - [8.Worker Thread](#8worker-thread)
  - [9.Future](#9future)
  - [10.Two-Phase Termination](#10two-phase-termination)
  - [11.Thread-Specific-Storage](#11thread-specific-storage)
  - [12.Active-Object](#12active-object)
- [知识点](#知识点)
  - [1.关于synchronized方法互斥的处理:](#1关于synchronized方法互斥的处理)
  - [2.Thread.sleep](#2threadsleep)
  - [3. 判断题](#3-判断题)
  - [4.wait/notify/notifyAll](#4waitnotifynotifyall)
  - [5.long/double的操作不是原子性的](#5longdouble的操作不是原子性的)
  - [6.解决死锁的思路](#6解决死锁的思路)
  - [7.线程安全/非安全的Queue](#7线程安全非安全的queue)
  - [8.notify/notifyAll和interrupt的区别](#8notifynotifyall和interrupt的区别)
  - [9.线程中断](#9线程中断)
  - [10.性能比较](#10性能比较)
  - [11.线程的异常处理](#11线程的异常处理)
  - [12.volatile](#12volatile)
    - [12.1 volatile底层实现](#121-volatile底层实现)
    - [12.2 volatile的特性](#122-volatile的特性)
    - [12.3 volatile内存语义的实现](#123-volatile内存语义的实现)
    - [12.4 volatile与synchronzied的区别](#124-volatile与synchronzied的区别)
  - [13.synchronized](#13synchronized)
  - [14.原子性](#14原子性)
    - [14.1 处理器保证原子性](#141-处理器保证原子性)
    - [14.2 Java中保证原子性](#142-java中保证原子性)
      - [14.2.1 自旋CAS](#1421-自旋cas)
      - [14.2.2 使用锁](#1422-使用锁)
  - [15.Java内存模型](#15java内存模型)
  - [16.重排序](#16重排序)
    - [16.1 重排序的类型](#161-重排序的类型)
    - [16.2 JMM与重排序的关系](#162-jmm与重排序的关系)
    - [16.3 as-if-serial](#163-as-if-serial)
  - [17.happens-before](#17happens-before)



12种常用的多线程设计模式:

### 线程设计模式

#### 1.Single Threaded Execution 

在同一时刻只允许一个线程执行，Java可以有以下几种实现方式:

* 使用synchronized关键字(single_threaded_execution.example2)
* 使用Semaphore(1)来确保被保护的代码只有一个线程在执行(single_threaded_execution.example3)

> 问题

如果多个线程同时访问同一个有状态的实例时，实例会失去安全性。

> 实现

在同一时刻只允许一个线程访问的代码(临界区)，使用Java的synchronized来保护。

> 相关模式

`Immutable:` 当实例的状态不会发生变化时，可以使用Immutable模式来提高吞吐量。

`Read-Write Lock:` 当Reader角色和Writer角色被分开时,可以使用Read-Write Lock模式来提高吞吐量。





#### 2.Immutable

Immutable是创建不可变的类来确保线程安全。

在Java中用到Imutable模式的常见类有:

* java.lang.String
* java.math.BigInteger、java.math.BigDecimal
* java.util.Pattern
* java.lang.Integer等包装类

以下的情况会破坏Immutable:

* 将字段里保存的实例直接作为getter方法的返回值(immutable.example1)
* 将构造函数的参数中传入的实例直接赋值给字段(immutable.example2)

> 问题

如果使用Single-Thread-Execution模式，吞吐量会下降。

> 实现

* 使用private来隐藏字段
* 使用final来确保字段无法改变
* 只提供getter方法，不提供setter方法





#### 3.Guarded Suspension

Guarded是‘被保护，被守卫’的意思，Suspension是‘暂停’的意思。

如果执行现在的处理会造成问题，就让执行处理的线程进行等待，直到达到了执行线程的必要条件。通过让线程等待来保证实例的安全性。

一个典型的例子就是线程通信的例子。(guarded_suspension.example1)

在Single Threaded Execution模式中，只要有一个线程进入临界区，其他线程就无法进入，只能等待。而在Guarded Suspension模式中，线程是否等待取决于守护条件。**Guarded Suspension模式是在Single Threaded Execution模式的基础上附加了条件而形成的。**

> 问题

使用Guarded Suspension模式时，可以通过守护条件来控制方法的执行。但是，如果永远无法满足守护条件，那么线程会永远等待，所以可能会失去生存性。

> 实现

在Java中，我们可以使用while语句来检查守护条件，调用wait方法来让线程等待。接着，调用notify/notifyAll方法来发送守护条件变化的通知。而检查和改变守护条件则可以使用Single-Thread-Execution模式来实现。

> 相关模式

`Balking:` 如果希望在不满足守护条件时，线程不等待，而是直接返回，可以使用Balking模式。

`Single-Threaded-Execution:` Guarded Suspension模式的检查和改变守护条件的部分可以使用Single-Thread-Execution模式。



#### 4.Balking

如果现在不适合执行这个操作，或者没必要执行这个操作，就停止处理，直接返回。

在Guarded Suspension模式中,guardedMethod的守护条件成立之前，线程会一直等待。而在Balking模式中，当守护条件不成立时，线程立即返回。

> 实现

Java可以使用if语句来检查守护条件。这里可以使用return语句从方法中返回或是通过throw语句抛出异常来进行中断。



#### 5.Producer-Consumer

生产者和消费者之间存在一定的速度差异，可以利用一个中间角色Channel来缓解速度的差异。

想从某个线程(Producer角色)向其他线程(Consumer角色)安全传递数据时。

> 实现

在Producer角色和Customer角色之间准备一个中转站-Channel角色。接着，让Channel角色持有多个数据。这样，就可以缓解Producer角色与Customer角色之间的处理速度差异。另外，如果在Channel角色中进行互斥处理，就不会失去数据的安全性。这样就可以既不降低吞吐量，又可以在多个线程之间安全地传递数据。

> 相关模式

`Guarded Suspension:` Channel角色安全传递数据地部分可以使用Guarded Suspension模式。

`Future:` 在Future模式中传递返回值的使用可以使用Producer-Consumer模式。

`Worker Thread:` 在传递请求的时候可以使用Producer-Customer模式。



#### 6.Read Write Lock

利用读取操作的线程之间不会冲突的特性来提高程序性能。

* 适合读取操作繁重时(多个线程可以同时读取提高程序性能)
* 适合读取频率比写入频率高时

synchronized是**物理锁**，每个实例都持有一个锁，但同一个锁不能由两个以上的线程同时获取，Java虚拟机也是这么实现的。

Read Write Lock是**逻辑锁**，是开发人员自己实现的一种结构，逻辑锁也是依赖于物理锁而实现的。

> 实现

将"控制Reader角色的锁"与"控制Writer角色的锁"分开，引入一个提供这两种锁的ReadWriteLock角色。ReadWriteLock角色会进行Writer角色之间的互斥处理，以及Reader角色与Writer角色之间的互斥处理。Reader角色之间即使发生冲突也不会有影响，因此无需进行互斥处理。

> 相关模式

`Guarded Suspension:` ReadWriteLock角色实现互斥处理的部分可以使用Guarded Suspension模式。

`Immutable:` 当Writer角色完全不存在时,可以使用Immutable模式。



#### 7.Thread-Per-Message

为每个命令或请求新分配一个线程，由这个线程来执行处理。

消息的‘委托端’线程会告诉‘执行端’线程，“这项工作就交给你了”。

* 适用于handle操作很耗时的操作，可以启动一个新的线程来执行这些耗时的操作，主线程返回。
* 适用于操作顺序没有要求时
* 适用于不需要返回值时(当需要获取操作结果时，可以使用Future模式)
* 服务器接收客户端的请求，将这些请求的实际处理交由其他线程执行，就是采用的这种方式

> 相关模式

`Worker Thread:` 当想要节省线程启动锁花费的时间时，可以使用Worker Thread模式。

`Future:` 当想要将处理结果返回时，可以使用Future模式。





#### 8.Worker Thread

在Worker Thread模式中，工人线程会逐个取回工作并进行处理。当所有工作全部完成后，工人线程会等待新的工作的到来。

一般在Channel类中使用线程池来缓存工人线程。相比Thread-Per-Message模式，不是每一次都创建一个新的线程，减少创建线程的消耗。

> 相关模式

`Future:` 将工人线程的处理结果返回给调用方时可以使用Future模式。

`Producer-Consumer:` 将代表请求的实例传递给工人线程时可以使用Producer-Consumer模式。



#### 9.Future

如果有一个方法需要花费很长时间才能获取运行结果。那么，与其一直等待，不如使用Future来获取结果。如果运行结果已经出来了，那么直接获取结果即可。如果运行结果还没出来，那么需要等待结果出来。

> 相关模式

`Guarded Suspension:` 在Client角色等待处理的部分可以使用Guarded Suspension模式。

`Thread-Per-Message:` 在Thread-Per-Message模式中和获取处理结果时可以使用Future模式。

`Worker Thread:` 在Worker-Thread模式中获取处理结果时可以使用Future模式。



#### 10.Two-Phase Termination

分两阶段终止。

先从"操作中"状态变为"终止处理中"状态，然后再真正地终止线程。

* 安全地终止线程(安全性):仅在线程运行至不会破坏对象安全性的位置时，程序才会开始终止处理。
* 必定会进行终止处理(生存性)：线程在收到终止请求后，会中断可以中断的wait，转入终止处理。
* 发出终止请求后尽快进行终止处理(响应性)：线程在收到终止请求后，会中断可以中断的sleep，尽快进入终止处理。在耗时的操作之前，可以检查是否收到终止请求，如果收到终止请求，在具体的操作执行前立即抛出Interrupted，可以提高程序的响应性。

> 实现

Java不仅仅要设置终止请求的标志，还要使用interrupt方法来中断wait/sleep/join方法。由于线程在wait/sleep/join中抛出InterruptedException异常时会清除中断状态,所以在使用isInterrupted方法检查终止请求是否到来时需要格外注意。当想要实现即使在运行时发生异常也能进行终止处理时，可以使用finally块。

> 相关模式

`Balking:` 当想在执行终止处理时禁止其他处理，可以使用Balking模式。



#### 11.Thread-Specific-Storage

当想让原本为单线程环境设计的对象运行于多线程环境时。

"每个线程特有的储物柜"，"为每个线程准备的存储空间","各个线程各自专用","只属于该线程"

即使是只有一个入口，也会在内部为每个线程分配特有的存储空间的模式。

ThreadLocal实现了该模式。

ThreadLocal的优势：

* 将线程独有的信息保存在"线程外"的ThreadLocal中，不改变线程结构即可实现程序
* 没有显式地执行互斥处理，编程时犯错的可能性较小



#### 12.Active-Object

Active Object模式中的主动对象会通过自己特有的线程在合适的时机处理从外部接收到的异步消息，并根据需要返回处理结果。

Active-Object有以下特征：

* 接收来自外部的异步请求
* 能够自由地调度请求
* 可以单线程执行实际的处理
* 可以返回执行结果
* 拥有独立的线程



> 相关模式

`Worker Thread:` 在实现Scheduler角色的部分可以使用Worker Thread模式。

`Producer-Consumer:` 在将请求从Proxy角色传递给Scheduler角色的部分可以使用Producer-Consumer模式。

`Future:` 在将执行结果返回给Client角色的部分可以使用Future模式。 





![summary](summary.png)



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

* 多个线程按照一定的顺序去获取资源(single_threaded_execution.example5)
* 将多个临界区的资源封装成一个对象，线程对对象加锁(single_threaded_execution.example6)
* 外界条件破坏(guarded_suspension.example3)
* 超时处理(guarded_suspension.example5)



#### 7.线程安全/非安全的Queue

LinkedList实现了Queue接口，Queue接口中定义了offer/peek的方法，但是这两个方法是非线程安全的，在使用的时候需要使用synchronized或其他方法来保证线程安全。

LinkedBlockingQueue继承了Queue接口，并提供了take/put的方法，这两个方法是线程安全的，使用时无需使用synchronized来保证线程安全。



#### 8.notify/notifyAll和interrupt的区别

notify/notifyAll 和 interrupt 方法都能唤醒wait方法。区别如下：

| notify/notifyAll                               | interrupt                                     |
| ---------------------------------------------- | --------------------------------------------- |
| Object的方法                                   | Thread的方法                                  |
| 不能指定线程唤醒                               | 可以指定线程唤醒                              |
| 执行notify/notifyAll时，线程必须要获取实例的锁 | 执行interrupt时，并不需要获取要取消的线程的锁 |



#### 9.线程中断

如果没有调用wait/join/sleep方法，或者没有编写检查线程的中断状态并抛出Interrupted异常的代码，那么Interrupted异常就不会被抛出。

* interrupt是让线程变成中断状态的方法
* interrupted是检查并清除中断状态的方法

当线程被intertupt的时候，会带来以下的结果之一：

* 线程变成"中断状态",但是不会抛出InterruptedException
* 当线程正在sleep/wait/join时,会抛出InterruptedException,但是线程不会变成'中断状态'

这两种结果是可以进行转换的：

1.中断状态转换成InterruptedException:

如果想在不清除中断状态的前提下检查当前线程的中断状态,可以使用isInterrupted方法。

```java
if(Thread.interrupted()) {
    throw new InterruptedException("interrupt");
}
```

2.InterruptedException向中断状态的转换

```java
try {
    Thread.sleep(100);
} catch (InterruptedException e) {
    Thread.currentThread().interrupt();
}
```





#### 10.性能比较

测试了使用ReadWriteLock,synchronized,ConcurrentHashMap时读取的性能比较，通过读取的次数来判读：(read_write_lock.example3~5)

性能从高到低：concurrentHashMap > readWriteLock > synchronized



#### 11.线程的异常处理

如果在线程中没有try..catch捕获异常进行处理，可以使用以下两种方式对线程的异常统一处理：

* 设置未捕获的异常处理器(two_phase_termination/example1)
* 线程退出钩子，线程退出钩子是指在Java虚拟机退出时启动的线程，使用java.lang.Runtime的addShutdownHock来设置退出钩子(two_phase_termination/example2)



#### 12.volatile

##### 12.1 volatile底层实现

当对一个volatite修饰的共享变量进行写操作的时候(value = xxx),JVM会通知处理器做两件事情：

1.将当前处理器缓存行的数据写回到系统内存中

2.使其他CPU里缓存了该内存地址的数据无效

当处理器执行1的操作的时候,会进行`缓存锁定`或者`总线锁定`来保证在执行操作1的过程中，没有别的处理器再对该共享变量的内存地址进行操作。

其他的处理器会通过`嗅探`的技术,当发现其他处理器正打算将该共享变量写回到系统内存中时，正在嗅探的处理器会将自己处理器内的缓存置为无效，在下次访问相同的内存地址时，强制从系统内存中读取，填充到处理器自己的缓冲中，此操作称为`缓冲行填充`。



##### 12.2 volatile的特性

* 可见性。对一个volatile变量的读，总是能看到任意线程对这个volatile变量最后的写入。
* 原子性。对**任意单个**volatile变量的读/写具有原子性，但类似于volatile++这种复合操作不具有原子性。



##### 12.3 volatile内存语义的实现

**volatile内存语义的实现是通过编译器插入特定类型的内存屏障来实现的。**

* volatile写之前的操作不能被重排序,避免写之前的操作被重排序到volatile写操作之后。
* volatile读之后的操作不能被重排序,避免读之后的操作被重排序到volatile读之前。
* 第一个操作是volatile写,第二个操作是volatile读时,不能重排序。



##### 12.4 volatile与synchronzied的区别

volatile仅仅保证对单个volatile变量的读/写具有原子性,而锁的互斥执行可以确保对整个临界区代码的执行具有原子性。**在功能上,锁比volatile更强大。在可伸缩性和执行性能上,volatile更有优势。**

如果想用volatile代替锁,一定要谨慎。



#### 13.synchronized

Java中的每一个对象都可以做为锁。synchronized是基于进入(monitorenter)和退出(monitorexit)Moinitor对象来实现同步的。

任何对象都会有一个monitor与之关联。monitorenter指令是在编译后插入到同步代码块的开始位置，而monitorexit是插入到`方法结束处`或者`异常处`(方法介绍或发生异常会释放monitor锁)。当线程执行到monitorenter指定的时候,将会尝试获取对象所对应的monitor的所有权,如果获取到即该线程获取到了锁。

`偏向锁：`大多数情况下，锁不仅不存在多线程竞争，而且总是由同一线程多次获取，为了让线程获取锁的代价更低而引入了偏向锁。当一个线程访问同步代码块并获取锁时，会在对象头里记录该线程id,以后该线程在进入和退出同步块时不需要进行CAS操作来进行加锁和解锁。

`轻量级锁：`线程在执行同步代码块之前，JVM会先在当前线程的栈帧中创建用于存储锁记录的空间，并将对象头的Mark Word复制到锁记录中,然后线程尝试使用CAS将对象头中的Mark  Word替换为指向锁记录的指针。



#### 14.原子性

##### 14.1 处理器保证原子性

`总线锁：`使用处理器提供的一个LOCK信号，当一个处理器在总线上输出此信号时,其他护理器的请求将被阻塞住,那么该处理器可以独占共享内存。

`缓存锁：`内存区域如果被缓存在处理器的缓存行中,那么当它执行锁操作回写到内存时,通过缓存一致性来保存操作的原子性。因为缓存一致性会保证当其他处理器回写已被锁定的缓存行的数据时,会使缓存行无效。

有两种情况不会使用缓存锁：

* 当操作的数据不能被缓存在处理器内部,或操作的数据跨多个缓存行时,会使用总线锁定

* 有些处理器不支持缓存锁定



##### 14.2 Java中保证原子性

###### 14.2.1 自旋CAS

CAS是由JVM保证的原子性操作。可以使用循环进行CAS操作直到成功为止。

CAS实现原子操作的三大问题：

* ABA(AtomicStampedReference检查预期饮用检查预期标志来解决ABA问题)
* 循环时间开销大
* 只能保证一个共享变量的原子操作(AtomicReference可以把多个变量放在一个对象里来进行CAS操作)

###### 14.2.2 使用锁

JVM内部实现了很多种锁的机制，有偏向锁，轻量级锁和互斥锁。除了偏向锁,JVM实现锁的方式都用了循环CAS,当一个线程进入同步块的时候使用循环CAS的方式来获取锁,当它退出同步块的时候使用循环CAS释放锁。



#### 15.Java内存模型

**堆内存**(实例域、静态域、数组)在线程之间共享。

JMM定义了线程和主内存之间的抽象关系,JMM决定一个线程对共享变量的写入何时对另外一个线程可见。

JMM通过控制主内存与每个线程的本地内存之间的交互来提供内存可见性保证。

JMM确保在不同的编译器和不同的处理器平台上,通过禁止特定类型的编译器重排序和处理器重排序,为程序员提供一致的内存可见性。



#### 16.重排序

##### 16.1 重排序的类型

从Java源代码到最终实际执行的指令排序,会经历下面3种重排序:

1. 编译器优化重排序
2. 指令集并行重排序
3. 内存系统重排序

其中1是属于编译器重排序，2和3是属于处理器重排序。

##### 16.2 JMM与重排序的关系

JMM会禁止编译器的特定类型的重排序(不是所有的编译器的重排序都要禁止),对于处理器重排序,JMM会要求编译器在生成指令重排序时,插入特定类型的内存屏障,通过内存屏障来禁止特定类型的处理器重排序。

##### 16.3 as-if-serial

无论是在单线程还是多线程,编译器和处理器都会进行重排序优化。但是由于as-if-serial语义的存在,编译器和处理器不会对存在数据依赖关系的操作做重排序，因为这种重排序会改变执行结果，但是操作之间如果不存在数据依赖关系,则可能被编译器和处理器进行重排序。

无论如何编译器和处理器怎么重排序，都不会影响在`单线程`环境下的执行结果。as-if-serial语义确保了单线程环境下无需考虑重排序和内存可见性问题。



#### 17.happens-before

JSR-133使用happens-before的概念来阐述操作之间的内存可见性。

两个操作之间具有happens-before关系，并不意味着前一个操作必须要在后一个操作之前执行。happens-before仅仅要求前一个操作对后一个操作**可见**，且前一个操作按顺序排在第二个操作之前。



#### 18.公平锁/非公平锁

##### 18.1 公平锁/非公平锁的实现

公平锁的内存语义:

* 公平锁获取时,会去读volatile变量

* 公平锁解锁时,会去写volatile变量

非公平锁的内存语义:

* 非公平锁获取时,会利用CAS更新volatile变量,**CAS的操作同时具有volatile读和volatile写的内存语义**
* 非公平锁释放时,会去写volatile变量

##### 18.2 锁的内存语义实现方式

1.利用volatile变量的写-读所具有的内存语义

2.利用CAS所附带的volatile读和volatile写的内存语义
