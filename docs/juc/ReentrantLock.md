# ReentrantLock

## AQS Node
### Node 数据结构
```java
public class Node {
    // 	当前节点在队列中的状态
    volatile int waitStatus; 
    // 前驱指针
    volatile Node prev;
    // 后继指针
    volatile Node next;
    // 表示处于该节点的线程
    volatile Thread thread;
    // 指向下一个处于CONDITION状态的节点（由于本篇文章不讲述Condition Queue队列，这个指针不多介绍）
    Node nextWaiter;
}
```
### 线程两种锁的模式
| 模式        | 含义              |
|-----------|-----------------|
| SHARED    | 表示线程以共享的模式等待锁   |
| EXCLUSIVE | 表示线程正在以独占的方式等待锁 |

### waitStatus 枚举值
| waitStatus | 解释                                  |
|------------|-------------------------------------|
| 0          | 值                                   |
| CANCELLED  | 为1，表示线程获取当一个Node被初始化的时候的默认锁的请求已经取消了 |
| SIGNAL     | 为-1，表示线程已经准备好了，就等资源释放了              |
| CONDITION  | 为-2，表示节点在等待队列中，节点线程等待唤醒             |
| PROPAGATE  | 为-3，当前线程处在SHARED情况下，该字段才会使用         |

## 公平锁

### lock()
```java
// ReentrantLock
public void lock() {
    sync.lock();
}

// FairSync
final void lock() {
    acquire(1); 
}
```
#### acquire(int arg)
```java
// AbstractQueuedSynchronizer
public final void acquire(int arg) {
    if (!tryAcquire(arg) && // 尝试获取锁
            acquireQueued(addWaiter(Node.EXCLUSIVE), arg))
        selfInterrupt();
}


```

#### tryAcquire(int acquires)
```java
// FairSync
protected final boolean tryAcquire(int acquires) {
    final Thread current = Thread.currentThread();
    int c = getState();
    if (c == 0) {
        if (!hasQueuedPredecessors() && // 没有前驱节点，那么自己的就是头节点
                compareAndSetState(0, acquires)) { //尝试改变锁状态
            setExclusiveOwnerThread(current); // 设置锁所属的线程，可重入锁
            return true;
        }
    } else if (current == getExclusiveOwnerThread()) { // 可重入锁
        int nextc = c + acquires;
        if (nextc < 0)
            throw new Error("Maximum lock count exceeded");
        setState(nextc);
        return true;
    }
    return false;
}
```
#### hasQueuedPredecessors()
```java
// AbstractQueuedSynchronizer
public final boolean hasQueuedPredecessors() {
    // 1. h != t  : 都为 null 是 未初始化、不为 null 是 这没有后继节点
    // 2. (s = h.next) == null : 有线程刚刚获取尾指针，但是好没有改后继指针。还在插入节点
    // 3. s.thread != Thread.currentThread() : 下一个节点不是本身线程
    Node t = tail; // Read fields in reverse initialization order
    Node h = head;
    Node s;
    return h != t &&
        ((s = h.next) == null || s.thread != Thread.currentThread());
}
```

#### addWaiter(Node mode)

```java
// AbstractQueuedSynchronizer
private Node addWaiter(Node mode) {
    Node node = new Node(Thread.currentThread(), mode);
    // Try the fast path of enq; backup to full enq on failure
    Node pred = tail;
    if (pred != null) {
        node.prev = pred;
        if (compareAndSetTail(pred, node)) {
            pred.next = node;
            return node;
        }
    }
    enq(node);
    return node;
}

private Node enq(final Node node) {
    for (;;) {
        Node t = tail;
        if (t == null) { // 初始化链表
            if (compareAndSetHead(new Node()))
                tail = head;
        } else {
            node.prev = t;
            if (compareAndSetTail(t, node)) {
                t.next = node;
                return t;
            }
        }
    }
}
```
#### acquireQueued(final Node node, int arg)

```java
// AbstractQueuedSynchronizer
final boolean acquireQueued(final Node node, int arg) {
    boolean failed = true;
    try {
        boolean interrupted = false;
        for (;;) {
            final Node p = node.predecessor();
            if (p == head && tryAcquire(arg)) {
                setHead(node);
                p.next = null; // help GC
                failed = false;
                return interrupted;
            }
            if (shouldParkAfterFailedAcquire(p, node) &&
                parkAndCheckInterrupt())
                interrupted = true;
        }
    } finally {
        if (failed)
            cancelAcquire(node);
    }
}
```
#### shouldParkAfterFailedAcquire(Node pred, Node node)
```java
// AbstractQueuedSynchronizer
private static boolean shouldParkAfterFailedAcquire(Node pred, Node node) {
    int ws = pred.waitStatus;
    if (ws == Node.SIGNAL)
        /*
         * This node has already set status asking a release
         * to signal it, so it can safely park.
         */
        return true;
    if (ws > 0) {
        /*
         * Predecessor was cancelled. Skip over predecessors and
         * indicate retry.
         */
        do {
            node.prev = pred = pred.prev;
        } while (pred.waitStatus > 0);
        pred.next = node;
    } else {
        /*
         * waitStatus must be 0 or PROPAGATE.  Indicate that we
         * need a signal, but don't park yet.  Caller will need to
         * retry to make sure it cannot acquire before parking.
         */
        compareAndSetWaitStatus(pred, ws, Node.SIGNAL);
    }
    return false;
}
```
#### parkAndCheckInterrupt()
```java
// AbstractQueuedSynchronizer
private final boolean parkAndCheckInterrupt() {
    LockSupport.park(this);
    return Thread.interrupted();
}
```

#### cancelAcquire(Node node)
```java
// AbstractQueuedSynchronizer
private void cancelAcquire(Node node) {
    // Ignore if node doesn't exist
    if (node == null)
        return;

    node.thread = null;

    // Skip cancelled predecessors
    Node pred = node.prev;
    while (pred.waitStatus > 0)
        node.prev = pred = pred.prev;

    // predNext is the apparent node to unsplice. CASes below will
    // fail if not, in which case, we lost race vs another cancel
    // or signal, so no further action is necessary.
    Node predNext = pred.next;

    // Can use unconditional write instead of CAS here.
    // After this atomic step, other Nodes can skip past us.
    // Before, we are free of interference from other threads.
    node.waitStatus = Node.CANCELLED;

    // If we are the tail, remove ourselves.
    if (node == tail && compareAndSetTail(node, pred)) {
        compareAndSetNext(pred, predNext, null);
    } else {
        // If successor needs signal, try to set pred's next-link
        // so it will get one. Otherwise wake it up to propagate.
        int ws;
        if (pred != head &&
                    ((ws = pred.waitStatus) == Node.SIGNAL ||
                    (ws <= 0 && compareAndSetWaitStatus(pred, ws, Node.SIGNAL))) &&
                    pred.thread != null) {
            Node next = node.next;
            if (next != null && next.waitStatus <= 0)
                compareAndSetNext(pred, predNext, next);
        } else {
            unparkSuccessor(node);
        }

        node.next = node; // help GC
    }
}
```

#### unparkSuccessor(Node node)

```java
// AbstractQueuedSynchronizer
private void unparkSuccessor(Node node) {
    /*
     * If status is negative (i.e., possibly needing signal) try
     * to clear in anticipation of signalling.  It is OK if this
     * fails or if status is changed by waiting thread.
     */
    int ws = node.waitStatus;
    if (ws < 0)
        compareAndSetWaitStatus(node, ws, 0);

    /*
     * Thread to unpark is held in successor, which is normally
     * just the next node.  But if cancelled or apparently null,
     * traverse backwards from tail to find the actual
     * non-cancelled successor.
     */
    Node s = node.next;
    if (s == null || s.waitStatus > 0) {
        s = null;
        for (Node t = tail; t != null && t != node; t = t.prev)
            if (t.waitStatus <= 0)
                s = t;
    }
    if (s != null)
        LockSupport.unpark(s.thread);
}
```


### unlock()

```java
// java.util.concurrent.locks.ReentrantLock.unlock
public void unlock() {
    sync.release(1);
}
```

#### release(int arg)
```java
// AbstractQueuedSynchronizer
public final boolean release(int arg) {
    if (tryRelease(arg)) {
        Node h = head;
        if (h != null && h.waitStatus != 0)
            unparkSuccessor(h);
        return true;
    }
    return false;
}
```

#### tryRelease(int releases)
```java
protected final boolean tryRelease(int releases) {
    int c = getState() - releases;
    if (Thread.currentThread() != getExclusiveOwnerThread())
        throw new IllegalMonitorStateException();
    boolean free = false;
    if (c == 0) {
        free = true;
        setExclusiveOwnerThread(null);
    }
    setState(c);
    return free;
}
```

#### unparkSuccessor(Node node)
```java
// AbstractQueuedSynchronizer
private void unparkSuccessor(Node node) {
    
    // 如果状态为负(即，可能需要信号)，
    // 尝试在预期信号的情况下清除。如果这失败了，或者状态被等待线程改变了，这是可以的。
    int ws = node.waitStatus;
    if (ws < 0)
        compareAndSetWaitStatus(node, ws, 0);

    // 要unpark的线程保存在后继者中，后继者通常是下一个节点。
    // 但如果已取消或为空，则从tail向后遍历以找到实际未取消的后继程序。
    Node s = node.next;
    if (s == null || s.waitStatus > 0) {
        s = null;
        for (Node t = tail; t != null && t != node; t = t.prev)
            if (t.waitStatus <= 0)
                s = t;
    }
    if (s != null)
        LockSupport.unpark(s.thread);
}
```
