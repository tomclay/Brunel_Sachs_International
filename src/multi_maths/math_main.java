package multi_maths;

/**
 * Created by tomclay on 31/10/2016.
 */
public class math_main {

    public static void main(String[] args) {

        // You want to create a shared data object that allows controlled reading
        // and writing by concurrent threads

        math_data mySharedData = new math_data();

        // Create three threads - you could create many more - this is just to show them sharing
        // You share mySharedData by having it in the scope of the thread (i.e. you are passing it
        // to the thread

        math_thread myThread1 = new math_thread("myThread1", mySharedData);
        math_thread myThread2 = new math_thread("myThread2", mySharedData);
        math_thread myThread3 = new math_thread("myThread3", mySharedData);

        // Now start the threads executing

        myThread1.start();
        myThread2.start();
        myThread3.start();

    }
}
