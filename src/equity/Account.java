package equity;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Created by tomclay on 08/12/2016.
 */

public class Account {

//  Attempt to aquire a lock
//    public synchronized void acquireLock() throws InterruptedException{
//        Thread me = Thread.currentThread(); // get a ref to the current thread
//        System.out.println(me.getName()+" is attempting to acquire a lock!");
//        ++threadsWaiting;
//        while (accessing) {  // while someone else is accessing or threadsWaiting > 0
//            System.out.println(me.getName()+" waiting to get a lock as someone else is accessing...");
//            //wait for the lock to be released - see releaseLock() below
//            wait();
//        }
//        // nobody has got a lock so get one
//        --threadsWaiting;
//        accessing = true;
//        System.out.println(me.getName()+" got a lock!");
//    }
//
//    // Releases a lock to when a thread is finished
//
//    public synchronized void releaseLock() {
//        //release the lock and tell everyone
//        accessing = false;
//        notifyAll();
//        Thread me = Thread.currentThread(); // get a ref to the current thread
//        System.out.println(me.getName()+" released a lock!");
//    }

    // Database stuff



    // Start
    public synchronized void start() {

        Scanner input = new Scanner(System.in);

        int choice = 0, acc_no = 0;

        System.out.println("Welcome to Brunel Sachs International. Please enter in your account number:");

        acc_no = input.nextInt();

        while (choice != 1 && choice != 2 && choice != 3  && choice != 4 && choice != 5)
        {
            System.out.println("What would you like to do?");
            System.out.println("1. See my balance");
            System.out.println("2. Deposit funds");
            System.out.println("3. Withdraw funds");
            System.out.println("4. Transfer funds");
            System.out.println("5. Exit");

            choice = input.nextInt();
        }

    }

    // Show balance
    public synchronized void balance(int acc_no) {

    }

    // Deposit
    public synchronized void deposit(int acc_no) {

    }

    // Withdraw
    public synchronized void withdraw(int acc_no) {

    }

    // Transfer
    public synchronized void transfer(int acc_no) {

    }

    // Exit
}
