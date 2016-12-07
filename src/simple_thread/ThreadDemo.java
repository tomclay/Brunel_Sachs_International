/**
 * Created by tomclay on 30/11/2016.
 */
package simple_thread;

public class ThreadDemo extends Thread{

    public void run(){
        System.out.println("My thread is in running state.");
    }

    public static void main(String args[]){
        //create the new thread
        ThreadDemo thisIsTheThread = new ThreadDemo();
        //start it
        thisIsTheThread.start();


        //create the new thread
        ThreadDemo another = new ThreadDemo();
        //start it
        another.start();
    }
}
