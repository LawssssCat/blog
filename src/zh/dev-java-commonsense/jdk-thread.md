---
title: JDK thread 功能
date: 2024-05-24
tag:
  - java
  - thread
order: 66
---

## 概念

### JUC

JUC `java.util.concurrent` 缩写

并发场景进行多线程编程的工具类

### 进程、线程、协程

**进程** —— 一个应用，系统资源分配单位

**线程** —— 一个应用的其中一个任务，共享进程资源

**协程**/**虚拟线程（Visual Thread）** —— 在一个任务中，实现多任务有序的协作开展任务

- 一个线程中可以有多个虚拟线程
- 不由系统管理，由 jvm 管理
- 由于由 jvm 管理，完全在内存中进行状态切换，所以创建和销毁的开销小，更高效

::: tip
可以形象的理解： 进程=饭馆；线程=饭桌；协程=座椅；
:::

#### 内核线程

线程实现方式有三种：

1. 使用内核线程实现
1. 使用用户线程实现
1. 使用用户线程 + 内核线程混合实现

内核线程（Kernel-Level Thread，KLT）就是直接由操作系统内核（Kernel）支持的线程。这种线程由操作系统内核来完成线程切换，操作系统内核通过操纵调度器（Scheduler）对线程进行调度，并负责将线程的任务映射刀各个处理器上。

> 可以参考 linux 源码
>
> ```bash
> Thread.c
> jvm.h
> jvm.cpp
> thread.cpp
> os_linux.cpp
> ```
>
> 参考： <https://www.bilibili.com/video/BV1Bw4m1Z7eg?p=11>

**Java 创建线程的方式就是采用内核线程的方式创建的**

#### 内核线程数量

```bash
# 查看指定参数
sysctl -a | grep threads-max # 查看所有参数
sysctl kernel.threads-max
cat /proc/sys/kernel/threads-max # 内核参数在 /proc/sys 目录下的格式为： 目录.文件

# 修改指定参数
sysctl -w kernel.threads-max=102400 # 修改 /etc/sysctl.conf 文件，该文件在系统重启后自动加载

# 手动生效配置
sysctl -p
```

当实际线程数量超过上述设置值后，Java 继续创建线程会报错：

```bash
Exception in thread "main" java.lang.OutOfMemoryError: unable to create new native thread
  at java.lang.Thread.start0(Native Method)
  at java.lang.Thread.start(Thread.java:717)
  at Test02.main(Test02.java:9)
```

#### 用户线程

一般认为，一个线程只要不是内核线程，都是用户线程（User Thread，UT）

用户线程指完全建立再用户自己的程序线程库上，系统内核不能感知到存在的线程（用户线程的创建、同步、销毁和调度完全由用户程序完成，不需要内核的帮助）。

对比：

- 系统线程上下文切换需要系统调度，代价高；用户线程不需要调用内核，操作快速且代价低，且能够支持规模更大的线程数量
- 系统线程调用方便，只要是支持多线程的系统都能轻松调起；用户线程调用复杂，需要用户程序自己处理线程的创建、销毁、切换和调度

在 Java 1.2 之前 Thread 是用户线程，从 1.2 版本之后采用了内核线程，但如今考虑更好的程序性能，JDK 17 又推出 “协程/~~纤程~~/虚拟线程” 来辅助用户定义用户线程。

### 并发、并行、串行

并行 = 多个线程**同时**执行**完整**任务

串行 = 多个线程**依次**执行**完整**任务

并发 = 多个线程**轮流**执行**部分**任务

### 线程数、CPU 的核心数

线程是 CPU 调度的最小单位 —— 即同一时刻，一个 CPU 核心数量运行一个线程

#### 逻辑处理器（Intel 超线程技术）

Intel 引入超线程技术后，产生了 “逻辑处理器” 的概念，即使 CPU 核心数与线程数可以形成 1:2 的关系。

::: tip
在 Java 中使用 `Runtime.getRuntime().availableProcessors()` 可以获取当前的 CPU 核心数。 ❗ 实际上是逻辑处理器核心数
:::

::: tip
更多的线程一般意味着更多线程创建/销毁开销、更频繁的上下文切换，所以一般需要根据现有的 CPU 核心数量/逻辑处理器核心数量估算最大可并发的线程数。
:::

### 时间片、上下文切换

**时间片**： 为了让一个 CPU 核心并发执行多个线程，操作系统设计了 “时间片” 机制，即 CPU 核心轮流执行不同线程小段时间，让多个任务的状态在一个大时间内总能保持更新。

**上下文切换**： 两个连续的时间片可能给到同一个线程，也可能给到不同的线程。当两个连续的时间片给到不同的线程后，CPU 核心执行到对应时间片时，由于执行的是另外的线程任务，就需要进行线程上下文的切换。

## Thread API

start / run

setName / getName

sleep （💡 不释放锁）（推荐：TimeUnit） / interrupt / isInterrupted / interrupted

> 参考：【Java 并发·08】线程中断 interrupt - https://www.bilibili.com/video/BV1CM4y157vc/

yield （💡 不释放锁） —— 允许相同优先级其他线程抢占时间片。

setPriority / getPriority —— 优先级

join / isAlive —— 等待线程执行完成

setDaemon —— 守护线程

setUncaughtExceptionHandler —— 处理未捕获的异常

### 线程中断（interrupt）

Java Thread 有如下打断线程相关方法

`public void interrupt()` 打断线程，线程抛出中断异常 （❗ 仅打上中断标记，不保证中断立即执行）

`public boolean isInterrupted()` 判断当前线程是否被打断，不清除打断标记

`public static boolean  interrupted()` 判断当前线程是否被打断，清除打断标记

```java
Thread thread = new Thread(() -> {
  while(true) {
    System.out.println(Thread.currentThread.isInterrupted()); // true
    if (Thread.interrupted()) {
      System.out.println(Thread.currentThread.isInterrupted()); // false
      break; // 线程中断标记，不会主动中断线程，需要手动结束线程任务
    }
    System.out.println("定时任务...");
  }
});
thread.start();
thread.interrupt(); // 打上线程中断标记
```

::: tip
`Thread.sleep()` 中会调用 `Thread.interrupted()` 判断并消除中断标记
:::

### 线程状态

> 参考：
>
> - https://www.cnblogs.com/i-code/p/13839020.html
> - https://www.bilibili.com/video/BV1Bw4m1Z7eg?p=52

::: tip
理解线程状态为了啥？ todo
:::

在 JAVA 环境中，线程 Thread 有如下几个状态： （💡 通过 `Thread.State` 查看枚举） （💡 通过 `thread.getState()` 查看线程状态）

![image.png](https://s2.loli.net/2024/05/26/vlUnRbkdoXBuTNE.png)

### Callable、Future

获取线程返回值

```java
FutureTask<Integer> future = new FutureTask<>(
  (Callable<Integer>) () -> {
    return 5;
  }
);
new Thread(future).start();
try {
  Integer value = future.get();
  // ...
}
```

### 異常抛出时机

- 无返回值用 execute 方法调用，异常马上在子线程抛出

- 有返回值用 submit 方法调用得到 future 类，异常在 `future.get` 时在主线程抛出

::: tip
异常抛出后，先给由 setUncaughtExceptionHandler 方法绑定的处理器处理
:::

### LockSupport

LockSupport 是 `java.util.concurrent.locks` 包下的一个类，是用来创建锁和其他同步类的基本线程阻塞工具类。

通过 `park` 和 `unpark` 方法可以实现线程调度中的 wait（等待） 和 notify（唤醒） 功能。

todo 具体使用方法 https://www.bilibili.com/video/BV1Bw4m1Z7eg?p=47

## ThreadPool API

Java 里的线程池顶级接口是 `java.util.concurrent.Executor` 一个执行线程的工具和 `java.util.concurrent.ExecutorService` 一个线程管理服务。

> 配置参考：
>
> - <https://www.bilibili.com/video/BV1xE421M78a/>

```java
ExecutorService threadPool = new ThreadPoolExecutor(
  10, // 💡 corePoolSize 核心线程数量 —— 创建，不回收
  20, // 💡 maximumPoolSize 最大线程数量 —— 创建，回收
  0L, // 💡 keepAliveTime 非核心线程存活时间
  TimeUnit.SECONDS,
  // 💡 workQueue 工作队列/阻塞队列 —— 超过核心线程数量后排队，队列满后才创建非核心线程处理任务
  // e.g.
  new ArrayBlockingQueue<Runnable>(3), // 基于数组的有界队列，先入先出（FIFO）原则排序
  // new LinkedBlockingQueue // 基于链表的有界阻塞队列（不设置大小时，默认为 Integer.MAX_VALUE），先入先出（FIFO）原则排序
  Executors.defaultThreadFactory(), // 💡 threadFactory 线程工厂 —— 可以用来绑定线程的异常处理器
  // 💡 handler 拒绝策略 —— 阻塞队列满了、最大线程数也满了，则有拒绝策略处理
  // e.g.
  // new ThreadPoolExecutor.AbortPolicy() // 丢弃任务并抛出 RejectedExecutionException 异常 ❗可能造成调用者线程终止
  new ThreadPoolExecutor.DiscardPolicy() // 丢弃任务不抛出异常
  // new ThreadPoolExecutor.DiscardOldestPolicy() // 丢弃队列最前面的任务，然后重新尝试执行任务（重复此过程）
  // new ThreadPoolExecutor.CallerRunsPolicy() // 由调用线程处理该任务，e.g. 由 main 线程调用 runnable.run 方法
);
try {
  for (int i=1; i<=10; i++) {
    // 💡无返回值使用 execute；有返回值使用 submit
    threadPool.execute(() -> {
      // ...
    });
  }
} finally {
  // threadPool.shutdownNow(); // （中断所有线程，）立即关闭线程池
  threadPool.shutdown(); // （中断所有线程，）等待线程池中所有任务（正在执行的任务，队列中的任务）执行完毕后，关闭线程池
  threadPool.awaitTermination(Long.MAX_VALUE, TimeUnit.SECONDS); // 等待线程池关闭，即线程池中所有线程执行完毕
  threadPool.isTerminated(); // 判断线程正真结束。true = 线程池中的所有线程执行完毕
}
```

线程池排队逻辑：

- 核心线程空闲 —— 核心线程处理
- 核心线程满了 —— 排队
- 核心线程满了，队列满了 —— 非核心线程处理
- 核心线程满了，队列满了，非核心线程满了 —— 拒绝策略

### Executors

线程池有很多配置，为了简化配置，官方推荐使用 `java.util.concurrent.Exectors` 中的静态工厂类来生成一些常用的线程池。

- newFixedThreadPool —— 固定容量线程池
- newCachedThreadPool —— 可缓存线程池。当需求较小，回收空闲线程；当需求过量，增加线程数（无上限）
- newSingleThreadPoolExecutor —— 单线程 Executor
- newScheduledThreadPool —— 固定容量线程池，且可延时启动任务和定时任务启动

### 非核心线程淘汰机制

参考： https://www.bilibili.com/video/BV177421Z7as?p=29

todo 理解 `ThreadPoolExecutor.getTask` 逻辑

- time、timeout 作用
- cas 竞争淘汰

### 拒绝策略

当核心线程（corePoolSize）、任务队列（workQueue）、最大线程数（maximumPoolSize）都满了，就要执行 “拒绝策略”。

JDK 内置 4 种拒绝策略：

- AbortPolicy （默认） —— 丢弃任务，并抛出 RejectedExecutionException 异常 for 让程序员知道
- CallerRunsPolicy —— 丢弃任务，不抛出异常 for 无关紧要的业务
- DiscardOldestPolicy —— 丢弃任务队列最前的任务，将新任务放入队列末尾 for 重试业务
- DiscardPolicy —— 任务调度线程来执行当前任务 for 让所有任务都能得到执行，而使用多线程只作为增加吞吐量的手段 so 适合大量计算类型的业务

自定义拒绝策略：通过实现 `RejectExecutionHandler` 接口实现自定义拒绝策略。

```java
class MyRejectedExecutionHandler implements RejectedExecutionHandler {
  @Override
  public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
    try {
      executor.getQueue().offer(r, 60, TimeUnit.SECONDS); // 超时等待
    } catch (InterruptedExecution e) {
      e.printStackTrace();
    }
  }
}
```

开源项目自定义拒绝策略：

- dubbo（`org.apache.dubbo.common.threadpool.support.AbortPolicyWithReport`） —— 当 dubbo 的工作线程触发了线程拒绝策略后，为了让使用者清楚触发线程拒绝策略的原因，拒绝策略做了三件事：
  1. 输出告警日志 —— 内容包括：线程池的详细设置参数、线程池当前状态、拒绝的任务的详细信息
  1. 输出当前线程堆栈详情，将发生拒绝策略时的现场情况 dump 线程上下文信息到一个文件中
  1. 发送事件 onEvent
  1. 抛出拒绝执行异常，使本次任务失败（使用 JDK 默认拒绝策略的异常）

### 扩展方法

线程池里面提供了几个空方法（钩子方法）：

- beforeExecute
- afterExecute
- terminated

通过这些钩子方法可以实现如线程池状态统计、日志输出、告警通知等功能。

todo https://www.bilibili.com/video/BV1Bw4m1Z7eg?p=113

## 线程安全

线程安全 = 共享数据符合预期

- 原子性 —— atomic
- 可见性 —— violated
- 有序性 —— 指令重排、内存屏障、synchronized

### JMM 内存模型

todo 可见性 violated、指令重排 内存屏障

### ThreadLocal

todo

#### 问题：线程池中内存泄漏

如果在线程池中使用 ThreadLocal 可能会造成内存泄漏。

可能造成内存泄漏的推论：

1. 线程对象是通过强引入指向 ThreadLocalMap 的
1. ThreadLocalMap 也是强引用指向内部的 Entry
1. 内部的 Entry key 和 value 值分别是 “`Runnable` 中的 `new ThreadLocal` `ThreadLocal#xxxx`” 和 “`Runnable.run` 存入的值”
1. 当 `Runnable.run` 结束后，`ThreadLocal#xxxx` 依然被 Entry 强引用，但以无其他方式获取它
1. 因为线程池该线程可能长时间存在，从而导致 Entry 这块内存无法被 gc 回收，导致内存泄漏

解决方法：

在线程池中，使用了 ThreadLocal 对象后，手动调用 ThreadLocal 的 remove 方法，手动清除 Entry 对象。

#### InheritableThreadLocal

解决 ThreadLocal 无法获取父线程中的 ThreadLocal 的值的问题

```java
InheritableThreadLocal<String> threadLocal = new InheritableThreadLocal<>();
threadLocal.set("test");
Thread thread = new Thread(new Runnable() {
  @Override
  public void run() {
    String value = threadLocal.get();
    // ...
  }
});
thread.start();
Thread.sleep(1000);
```

::: tip
无法感知到父线程中途修改 ThreadLocal 的值

```java
InheritableThreadLocal<String> threadLocal = new InheritableThreadLocal<>();
threadLocal.set("test");
for (int i=0; i<10; i++) {
  if (i==5) {
    threadLocal.set("test5");
  }
  Thread thread = new Thread(new Runnable() {
    @Override
    public void run() {
      String value = threadLocal.get(); // 依然是 test
      // ...
    }
  }
}
```

为了解决这个问题，可以使用阿里开源组件 TransmittableThreadLocal

:::

### 原子类（atomic）

在 `java.util.concurrent.atomic` 包中

| 类型                                     | 具体类                                                                         |
| ---------------------------------------- | ------------------------------------------------------------------------------ |
| 基本类型                                 | AtomicInteger、AtomicLong、AtomicBoolean                                       |
| 引用类型                                 | AtomicReference、AtomicStampedReference、AtomicMarkableReference （涉及 CAS）  |
| 数组类型                                 | AtomicIntegerArray、AtomicLongArray、AtomicReferenceArray                      |
| 升级类型                                 | AtomicIntegerFieldUpdater、AtomicLongFieldUpdater、AtomicReferenceFieldUpdater |
| 累加器 Adder                             | LongAdder、DoubleAdder                                                         |
| 积累器 Accumulator <br> 可自定义累加方式 | LongAccumulator、DoubleAccumulator                                             |

::: tip
lazySet 不会保证可见性（没有加内存屏障）
:::

### ABA 问题

并发场景下，存在上下文数值被其他线程篡改的情况。

这种情况通过 “原子类 + 版本号” 的方式识别。

todo 例子 AtomicStampedReference

### 锁

#### 悲观锁/互斥锁/阻塞锁

悲观锁 —— 获取锁/释放锁均有 “线程状态的切换”，这会消耗性能

::: tip
所谓 “线程状态切换” 即： 让没有得到锁资源的线程进入 BLOCK 状态，然后在争夺到锁资源后恢复为 RUNNABLE 状态。
这个过程涉及到操作系统用户模式和内核模式的转换，所以代价比较高。
:::

##### synchronized 关键字

```java
public synchronized void test() throws InterruptedException { // 💡线程状态切换
  this.wait()
  this.notify() // 不能指定线程唤醒
}
```

##### ReentrantLock 类

`ReentrantLock lock = new ReentrantLock()`

```java
ReentrantLock lock = new ReentrantLock()
lock.lock() // 💡线程状态切换
con.wait()
con.signal()
lock.unlock()
```

#### 乐观锁/非阻塞锁

乐观锁 —— 通过系统指令，保证修改某变量状态时是原子性的，从而通过判断该变量状态，判断是否进入锁

##### 自旋锁（spinlock）/CAS（Compare and Swap）

自旋锁是在当前线程上，不停地循环判断原子变量的状态，判断是否进入锁。

::: tip
自旋锁通过循环将线程卡在某段代码上，从而避免线程状态的改变为 BLOCK，所以响应速度更快。
但当线程数不停增加时，因为每个线程都需要执行，占用 CPU 时间，所以性能会下降明显。
所以只有当线程竞争不激烈，并且保持锁的时间短时，适合使用自旋锁。
:::

e.g.

```java
for(;;) { // 自旋
  // 使用操作系统指令保证 compare and set 这两步操作的原子性
  if (Unsafe.getUnsafe().compareAndSwapInt()) { // 只有一个线程能进入
    // ...
    return
  }
}
```

or

```java
// 使用了CAS原子操作，lock函数将owner设置为当前线程，并且预测原来的值为空。unlock函数将owner设置为null，并且预测值为当前线程
public class SpinLock {
  private AtomicReference<Thread> sign = new AtomicReference<>();
  public void lock() {
    Thread current = Thread.currentThread();
    while (!sign.compareAndSet(null, current)) {}
  }
  public void unlock() {
    Thread current = Thread.currentThread();
    sign.compareAndSet(current, null);
  }
}
```

#### 偏向锁、轻量级锁、重量级锁

##### synchronized 锁升级问题

在 JDK 1.6 之前，JVM 通过内核态的 **管程（Monitor，监视器，对象锁）** 来实现 synchronized 锁的互斥。这种锁属于重量级锁，响应效率低。

::: info
管程（Monitor，监视器）是指管理共享变量操作的过程，让它们支持并发时的线程安全。（简单来说就是两个作用：同步和互斥）
Java 中的 `synchronized`、`wait()`、`notify()`、`notifyAll()` 均是管程技术的一部分。

💡 源码： Hotspot jdk 1.6 c++
:::

在 JDK 1.6 之后，JVM 为了提高锁的获取与释放效率，对 synchronized 的实现进行优化，引入了 “**偏向锁**” 和 “**轻量级锁**”。
（此时，锁有四种级别，级别从低到高依次为：**无锁**、**偏向锁**、**轻量级锁**、**重量级锁**）
随着锁竞争加剧，锁级别会逐渐升级。
（锁升级过程不可逆，即：锁级别只升不降）

todo markWord pol

todo 四个级别

#### 可重入锁（reentrant）/递归锁

可重入锁，也叫做递归锁，指的是同一线程 外层函数获得锁之后 ，内层递归函数仍然有获取该锁的代码，但不受影响。
在 JDK 中 `ReentrantLock` 和 `synchronized` 都是可重入锁。

::: tabs

@tab ReentrantLock 示例

```java
public class Test implements Runnable {
  ReentrantLock lock = new ReentrantLock();

  public void get() {
    lock.lock();
    // ...
    set();
    lock.unlock();
  }

  public void set() {
    lock.lock();
    // ...
    lock.unlock();
  }

  @Override
  public void run() {
    get();
  }
}
```

@tab synchronized 示例

```java
public class Test implements Runnable {
  public synchronized void get() {
    // ...
    set();
  }

  public synchronized void set() {
    // ...
  }

  @Override
  public void run() {
    get();
  }
}
```

@tab 自定义

```java
public class SpinLock1 {
  private AtomicReference<Thread> owner = new AtomicReference<>();
  private int count = 0;
  public void lock() {
    Thread current = Thread.currentThread();
    if (current == owner.get()) {
      count++;
      return;
    }
    while(!owner.compareAndSet(null, current)) {
      // wait ... or else cpu busy
    }
  }
  public void unlock() {
    Thread current = Thread.currentThread();
    if(current == owner.get()) {
      if(count!=0) {
        count--;
      } else {
        owner.compareAndSet(current, null);
      }
    }
  }
}
```

:::

#### ReentrantLock 使用

todo timeout

todo trylock —— 防止获取锁失败后一直等待，可以自定义获取锁失败后的处理

todo lockInterruptibly —— 等待过程中中断

#### 公平锁、非公平锁

概念：

- 非公平 —— 优先将锁给统一线程的任务 💡 非公平锁能减少线程上下文切换的开销，理论上性能更好，所以锁默认都是非公平的
- 公平 —— 不同线程获取锁机会一样

e.g.

- synchronized 非公平
- ReentrantLock 可公平、可非公平 `new ReentrantLock(true) ; // fair true/false 默认 false`

## 生产者/消费者模型

todo

## 协程/虚拟线程

Java 19 引入虚拟线程概念，Java 21 落地虚拟线程。

::: tip
JVM 使用轻量级的任务队列来调度虚拟线程，实现多个协同任务的调度，这避免使用多个真实线程来调度多个协同的任务，从而避免线程间上下文切换的带来的系统开销。
:::

## 问题

### 问题：循环多线程

```java
Arrays.asList().stream().parallel()....
IntStream.of().parallel()....
LongStream.of().parallel()....
```

### 问题：多线程操作集合（CopyOnWrite）

多线程同时操作同一个集合会抛出异常，因为异常迭代器有 “Fail-Fast（快速失败机制）”：当迭代器发现（其他代码）增删后，便抛出异常 `java.util.ConcurrentModificationException` —— 保证迭代器的独立性和隔离性

```java
List<String> list = new ArrayList();
list.add("hello");
Iterator iterator = list.iterator();
list.add("world"); // 其他代码增删
iterator.next(); // 抛出异常
```

处理这种情况，可以用 “写入时复制机制（CopyOnWrite，COW）” —— 希望迭代期间，能增删和高性能

```java title="java.util.concurrent.CopyOnWriteArrayList"
public boolean add(E e) {
    final ReentrantLock lock = this.lock;
    lock.lock();
    try {
        Object[] elements = getArray();
        int len = elements.length;
        Object[] newElements = Arrays.copyOf(elements, len + 1);
        newElements[len] = e;
        setArray(newElements);
        return true;
    } finally {
        lock.unlock();
    }
}
static final class COWIterator<E> implements ListIterator<E> {
    public E next() {
        if (! hasNext())
            throw new NoSuchElementException();
        return (E) snapshot[cursor++];
    }
}
```

1. 增删时，复制新数组，将引用挨个复制到新数组后，在新数组上操作 —— ❗ 所以写的性能，非常差！非常差！非常差！
1. 遍历时，正常遍历
1. 适用于读多，写少的情况

### 问题：ConcurrentHashMap 实现原理

ConcurrentHashMap 数据结构如下：

```
Segment[] （💡 Segment 继承 ReentrantLock 实现分段线程安全）
0 - HashEntry[] - HashEntry1,HashEntry2,... （单向链表）
1 - HashEntry[]
2 - HashEntry[]
3 - HashEntry[]
4 - HashEntry[]
5 - HashEntry[]
6 - HashEntry[]
7 - HashEntry[]
8 - HashEntry[]
...
16 （默认 16 个 segment 锁，相当于最大支持 16 个并发 put 操作）
```

::: tip
与 HashMap 一样，在 JDK 1.8 后，对碰撞增加了 “红黑树” 的处理。
:::

::: tip
concurrencyLevel 配置与 segment 数量的关系： <https://www.infoq.cn/article/ConcurrentHashMap>

- segments 数组的长度 ssize 通过 concurrencyLevel 计算得出
- 必须保证 segments 数组的长度是 2 的 N 次方（power-of-two size）
- e.g. 假如 concurrencyLevel 等于 14，15 或 16，ssize 都会等于 16，即容器里锁的个数也是 16

:::

### 问题：实务多线程失效（❗ 解决方案有问题）

todo 移到 spring 并在这里提示

问题：每个线程中的数据库连接（CurrentConnection）是不同的、独立的

```java
@Transactional
public void transactionAsyncFail() {
  new Thread(() -> {
    addUser(1);
  }).start();
  addUser(3);
  throw new RuntimeException("手动回滚");
}
```

解决：

```java
public void transactionAsyncSuccess() {
  int size = 10;
  CyclicBarrier barrier = new CyclicBarrier(size);
  AtomicReference<Boolean> roolback = new AtomicReference<>(false);

  for (int i=0; i<size; i++) {
    int currentNum = i;

    new Thread(() -> {
      // 手动开启事务
      TransactionStatus transaction = transactionManager.getTransaction(transactionDefinition);
      try {
        // insert 操作，如果插入数据 < 1 则异常
        if (addUser(currentNum) < 1) {
          log..info("手动异常");
          throw new RuntimeException("插入数据失败");
        }
      } catch (Exception e) {
        // 如果当前线程执行异常，则设置回滚标志
        rollback.set(true);
      }

      // 等待所有线程的事务结束
      try {
        barrier.await();
      } catch (InterruptedException | BrokenBarrierException e) {
        throw new RuntimeException(e);
      }
      // 如果标志需要回滚，则回滚
      log.info("我执行了{}", currentNum);
      if (rollback.get()) {
        transactionManager.rollback(transaction);
        log.info("rollback for {}", currentNum);
        return;
      }

      transactionManager.commit(transaction);
    }).start();
  }

  try {
    Thread.sleep(1000);
  } catch(InterruptedException e) {
    throw new RuntimeException(e);
  }
  log.info("hello");
}
```

坑：

1. 多线程长时间占用，线程池占满
1. 死锁
1. 干扰实际的数据库实务间的隔离性
1. 可以用 “分布式实务” 或 “最终一致” 解决

### 问题：动态线程池 with Nacos

todo https://www.bilibili.com/video/BV1Bw4m1Z7eg?p=108

所谓 “动态线程池” 指在不重启服务的情况下，改变线程池核心线程数量、最大线程数量、队列容量等。

#### 动态修改配置

环境：（基于 [Spring Cloud Alibaba 版本说明](https://github.com/alibaba/spring-cloud-alibaba/wiki/)）

- Spring Boot 2.6.3
- Spring Cloud 2021.0.1
- Spring Cloud Alibaba 2021.0.1.0
- [Nacos](https://nacos.io/) 1.4.2

```bash
./startup.sh -m standalone # 单机启动，否则以集群方式启动需要额外配置，麻烦
# 日志： ${NACOS_HOME}/logs/start.out
# 访问： http://ip:8848/nacos 默认用户/密码： nacos/nacos
```

```properties title="application.properties"
server.port=7070
spring.application.name=dynamic-thread-pool
spring.cloud.nacos.config.server-addr=8.142.44.107:8848
spring.cloud.nacos.config.name=dynamic-thread-pool
spring.cloud.nacos.username=nacos
spring.cloud.nacos.password=nacos
```

```java title="ThreadPoolConfig.java"
/**
 * 添加下面配置，会生成对自定义配置文件的提示
 * <dependency>
 *  <groupId>org.springframework.boot</groupId>
 *  <artifactId>spring-boot-configuration-processor</artifactId>
 *  <optional>true</optional>
 * </dependency>
 *
 * 默认配置
 * thread.pool.core-pool-size=16
 * thread.pool.maximum-pool-size=16
 * thread.pool.work-queue-size=1024
 */
@Data
@Component
@ConfigurationProperties(perfix = "thread.pool")
public class ThreadPoolProperties {
  private int corePoolSize;
  private int maximumPoolSize;
  private long keepAliveTime;
  private int workQueue;
}
```

```java title="ThreadPoolConfig.java"
@Configuration
public class ThreadPoolConfig {
  @Autowired
  private ThreadPoolProperties threadPoolProperties;
  @Bean
  public ThreadPoolExecutor threadPoolExecutor() {
    ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
      threadPoolProperties.getCorePoolSize(),
      threadPoolProperties.getMaximumPoolSize(),
      threadPoolProperties.getKeepAliveTime(),
      TimeUnit.SECONDS,
      new ArrayBlockingQueue<Runnable>(threadPoolProperties.getWorkQueueSize()),
      Executors.defaultThreadFactory(),
      // Executors.privilegedThreadFactory(),
      new ThreadPoolExecutor.DiscardPolicy()
    )
  }
}
```

方法一： `@RefreshScope` —— 刷新 bean 配置

```java title="ThreadPoolConfig.java"
@RefreshScope
@Bean
public ThreadPoolExecutor threadPoolExecutor() { // ...
```

方法二： 自己编写刷新代码

::: tabs

@tab 注册自定义线程池

```java title="ThreadPoolConfig.java"
@Bean
public ThreadPoolExecutor threadPoolExecutor() {
  ThreadPoolExecutor threadPoolExecutor = new DynamicThreadPoolExecutor( // DynamicThreadPoolExecutor 自定义类
    // ...
```

@tab 实现自定义线程池

```java title="DynamicThreadPoolExecutor.java"
public class DynamicThreadPoolExecutor extends ThreadPoolExecutor {
  // ... super method
}
```

@tab 实现自定义动态线程池管理器

```java title="DynamicThreadPoolExecutorManager"
@Component
public class DynamicThreadPoolExecutorManager {
  @Autowired
  private ApplicationContext applicationContext;
  public synchronized void refreshThreadPoolExecutor(Properties properties) { // 💡加锁，避免ABA
    applicationContext.getBeansOfType(DynamicThreadPoolExecutor.class).forEach((beanName, executor) -> {
      executor.setCorePoolSize(Integer.parseInt(properties.getProperty("thread.pool.core-pool-size")));
      executor.setMaximumPoolSize(Integer.parseInt(properties.getProperty("thread.pool.maximum-pool-size")));
      // 自定义队列 because of 队列无法通过线程池设置大小
      if (executor.getQueue() instanceof ResizeLinkedBlockingQueue) {
        ((ResizeLinkedBlockingQueue) executor.getQueue()).updateCapacity(properties.getProperty("work-queue-size"));
      }
    });
  }
}
```

@tab 实现自定义队列

```java
public class ResizeLinkedBlockingQueue<E> extends AbstractQueue<E> implements BlockingQueue<E>, java.io.Serializable {
  // ...
  void updateCapacity(int size) {
    // ...
  }
}
```

```java title="ThreadPoolConfig.java"
 @Bean
  public ThreadPoolExecutor threadPoolExecutor() {
    ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
      threadPoolProperties.getCorePoolSize(),
      threadPoolProperties.getMaximumPoolSize(),
      threadPoolProperties.getKeepAliveTime(),
      TimeUnit.SECONDS,
      new ResizeLinkedBlockingQueue<Runnable>(threadPoolProperties.getWorkQueueSize()), // 💡注入自定义队列
      Executors.defaultThreadFactory(),
      // Executors.privilegedThreadFactory(),
      new ThreadPoolExecutor.DiscardPolicy()
    )
  }
```

@tab 配置 nacos 监听器

```java title="NacosConfigListener.java"
@Component
public class NacosConfigListener implements ApplicationRunner {
  @Atuowired
  private NacosConfigManager nacosConfigManager;
  @Autowired
  private DynamicThreadPoolExecutorManager dynamicThreadPoolExecutorManager;
  @Override
  public void run(ApplicationArguments args) throws Execption {
    // 开始监听 nacos 的配置更新
    String dataId = nacosConfigManager.getNacosConfigProperties().getName();
    String group = nacosConfigManager.getNacosConfigProperties().getGroup();
    nacosConfigManager.getConfigService().addListener(dataId, group, new Listener() {
      @Override
      public Executor getExecutor() {
        return null;
      }
      // 更新后的配置信息在此方法中接收
      @Override
      public void receiveConfigInfo(
        String configInfo // 更新后的配置信息
      ) {
        // 刷新我们的线程池配置类
        Properties properties = new Properties();
        try {
          properties.load(new StringReader(configInfo));
        } catch (IOException e) {
          e.printStackTrace();
        }
        dynamicThreadPoolExecutorManager.refreshThreadPoolExecutor(properties);
      }
    })
  }
}
```

:::

#### 队列缩容处理

todo 多余线程的处理

#### 开源框架

- `dynamic-tp` —— 美团开源的动态线程池，支持通过 nacos 配置中心配置线程池，对线程池进行扩缩容。
