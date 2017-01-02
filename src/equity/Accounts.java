package equity;

import java.lang.*;
import java.util.Scanner;


/**
 * Created by tomclay on 08/12/2016.
 */

public class Accounts {

    private  Database database;
    private  String account_ID;
    private  Scanner input;
    private boolean accessing=false; // true a thread has a lock, false otherwise
    private int threadsWaiting=0; // number of waiting writers


    public synchronized void acquireLock() throws InterruptedException {
        java.lang.Thread me = java.lang.Thread.currentThread(); // get a ref to the current thread
        System.out.println(me.getName()+" is attempting to acquire a lock!");
        ++threadsWaiting;
        while (accessing) {  // while someone else is accessing or threadsWaiting > 0
            System.out.println(me.getName()+" waiting to get a lock as someone else is accessing...");
            //wait for the lock to be released - see releaseLock() below
            wait();
        }
        // nobody has got a lock so get one
        --threadsWaiting;
        accessing = true;
        System.out.println(me.getName()+" got a lock!");
    }

    // Releases a lock to when a thread is finished

    public synchronized void releaseLock() {
        //release the lock and tell everyone
        accessing = false;
        notifyAll();
        java.lang.Thread me = java.lang.Thread.currentThread(); // get a ref to the current thread
        System.out.println(me.getName()+" released a lock!");
    }


    public synchronized void start()  {

    accessThread.sendMessage("Welcome to Brunel Sachs International. Please enter in your account number.");

    try {
        account_ID = accessThread.incomingMessage().take();
        System.out.println(account_ID);
    } catch (InterruptedException e) {
        e.printStackTrace();
    }

    database = new Database(account_ID);

    accessThread.sendMessage("What would you like to do? 1. See my balance 2. Deposit funds 3. Withdraw funds 4. Transfer funds 5. Exit");

    choices();

    }

    public synchronized void choices(){

        int user_choice = 0;

        while (user_choice != 1 && user_choice != 2 && user_choice != 3  && user_choice != 4 && user_choice != 5)
        {
            try {
                user_choice = Integer.parseInt(accessThread.incomingMessage().take());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        switch (user_choice){
            case 1: balance();
                break;
            case 2: deposit();
                break;
            case 3: withdraw();
                break;
            case 4: transfer();
                break;
            case 5: exit();
                break;
            default: exit();
                break;
        }

        user_choice = 0;
    }

    public synchronized void balance() {
        float result = database.get_bal();
        accessThread.sendMessage("Your account balance: £" + result + "\n" + "What would you like to do? 1. See my balance 2. Deposit funds 3. Withdraw funds 4. Transfer funds 5. Exit");
        choices();
    }


    public synchronized void deposit() {
        accessThread.sendMessage("Please enter the amount to deposit.");

        String amount = "";
        try {
            amount = accessThread.incomingMessage().take();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        float result = Database.add(Float.parseFloat(amount));
        accessThread.sendMessage("Your new balance is: £" + database.accBalance + "\n" + "What would you like to do? 1. See my balance 2. Deposit funds 3. Withdraw funds 4. Transfer funds 5. Exit");
        choices();
    }


    public synchronized void withdraw() {
        accessThread.sendMessage("Please enter the amount to withdraw.");
        float amount = 0;

        try {
            amount = Float.parseFloat(accessThread.incomingMessage().take());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        float result = Database.subtract(amount);
        accessThread.sendMessage("Your new balance is: £" + database.accBalance + "\n" + "What would you like to do? 1. See my balance 2. Deposit funds 3. Withdraw funds 4. Transfer funds 5. Exit");
        choices();
    }

    // Transfer
    public synchronized void transfer() {
        input = new Scanner(System.in);
        accessThread.sendMessage("Please enter the ID of the account you wish to transfer money to.");
        String ID = input.nextLine();
        accessThread.sendMessage("Please enter the amount you wish to transfer.");
        float amount = Float.parseFloat(input.nextLine());
        float dest_account_new_bal = database.wire(ID,amount);
        System.out.println("The account " + ID + " now has the balance of £" + dest_account_new_bal);
        choices();
    }

    public synchronized void exit(){
        accessThread.sendMessage("Thank you for using Brunel Sachs International. Have a jolly nice day.");
        //System.exit(0);

    }
}
