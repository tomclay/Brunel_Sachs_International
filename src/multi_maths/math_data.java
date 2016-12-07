package multi_maths;

/**
 * Created by tomclay on 31/10/2016.
 */
public class math_data {

    private boolean accessing = false; // true a thread has a lock, false otherwise
    private int threadsWaiting = 0; // number of waiting writers
    public long math = 0; // The data point to be shared

    // attempt to acquire a lock
    public synchronized void acquireLock() throws InterruptedException {
        Thread me = Thread.currentThread(); // get a ref to the current thread
        System.out.println(me.getName() + " is attempting to acquire a lock!");

        ++threadsWaiting;
        while (accessing) {  // while someone else is accessing or threadsWaiting > 0
            System.out.println(me.getName() + " waiting to get a lock as someone else is accessing...");
            //wait for the lock to be released - see releaseLock() below
            wait();
        }
        // nobody has got a lock so get one
        --threadsWaiting;
        accessing = true;
        System.out.println(me.getName() + " got a lock!");
    }

    // Releases a lock to when a thread is finished
    public synchronized void releaseLock() {
        //release the lock and tell everyone
        accessing = false;
        notifyAll();
        Thread me = Thread.currentThread(); // get a ref to the current thread
        System.out.println(me.getName() + " released a lock!");
    }
}
