/**
 * Created by tomclay on 30/11/2016.
 */

package simple_thread;

public class Count extends Thread
{
    Count()
    {
        super("MY THREAD");
        System.out.println("the thread just created is called " + this.getName());
        start();
    }

    public void run()
    {
        try
        {
            for (int i=0 ;i<10;i++)
            {
                System.out.println("Printing the count " + i);
                Thread.sleep(000);  // Pause the thread
                long threadID = Thread.currentThread().getId();
                System.out.println("child " + threadID);
            }
        }
        catch(InterruptedException e)
        {
            System.out.println("my thread interrupted");
        }
        System.out.println("My thread run is over" );
    }


    public static void main(String args[])
    {
        Count cnt = new Count();
        try
        {
            while(cnt.isAlive()) // Check the thread is still alive
            {
                System.out.println("Main thread will be alive till the child thread is live");
                Thread.sleep(500);
            }
        }
        catch(InterruptedException e)
        {
            System.out.println("Main thread interrupted");
        }
        System.out.println("Main thread's run is over" );
    }
}
