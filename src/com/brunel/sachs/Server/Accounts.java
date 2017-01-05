package com.brunel.sachs.Server;

import java.lang.*;
import java.util.Scanner;


/**
 * Created by tomclay on 08/12/2016.
 */

public class Accounts {

    private Database database;
    private  String account_ID;
    private  Scanner input;
    private boolean accessing=false; // true a thread has a lock, false otherwise
    private int threadsWaiting=0; // number of waiting writers


    public void acquireLock() throws InterruptedException {
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

    public void releaseLock() {
        //release the lock and tell everyone
        accessing = false;
        notifyAll();
        java.lang.Thread me = java.lang.Thread.currentThread(); // get a ref to the current thread
        System.out.println(me.getName()+" released a lock!");
    }

    public void start() throws InterruptedException {

    Thread.sendMessage("Welcome to Brunel Scahs International. Please enter your account number.");

    try {
        releaseLock();
        account_ID = Thread.incomingMessage().take();
    } catch (InterruptedException e) {
        e.printStackTrace();
    }

    database = new Database(account_ID);

    releaseLock();

    Thread.sendMessage("What would you like to do? 1. See my balance 2. Deposit funds 3. Withdraw funds 4. Transfer funds 5. Exit");
    }

    public void choices(){


        int user_choice = 0;

        while (true)
        {
            try {
                user_choice = Integer.parseInt(Thread.incomingMessage().take());
                break;
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

    public void balance() {
        float result = database.get_bal();
        Thread.sendMessage("Your account balance: £" + result + ". What would you like to do? 1. See my balance 2. Deposit funds 3. Withdraw funds 4. Transfer funds 5. Exit");
        releaseLock();
        choices();
    }



    public void deposit() {

        Thread.sendMessage("Please enter the amount to deposit.");

        String amount = "";
        try {
            releaseLock();
            amount = Thread.incomingMessage().take();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        float result = Database.add(Float.parseFloat(amount));
        Thread.sendMessage("Your new balance is: £" + database.accBalance + ". What would you like to do? 1. See my balance 2. Deposit funds 3. Withdraw funds 4. Transfer funds 5. Exit");
        releaseLock();
        choices();
    }


    public void withdraw() {
        Thread.sendMessage("Please enter the amount to withdraw.");
        float amount = 0;

        try {
            releaseLock();
            amount = Float.parseFloat(Thread.incomingMessage().take());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        float result = Database.subtract(amount);
        Thread.sendMessage("Your new balance is: £" + database.accBalance + ". What would you like to do? 1. See my balance 2. Deposit funds 3. Withdraw funds 4. Transfer funds 5. Exit");
        releaseLock();
        choices();

    }


    public void transfer() {

        Thread.sendMessage("Please enter the ID of the account you wish to transfer money to.");

        try {
            releaseLock();
            String ID = Thread.incomingMessage().take();

        Thread.sendMessage("Please enter the amount you wish to transfer.");
        float amount = Float.parseFloat(input.nextLine());
        float dest_account_new_bal = database.wire(ID,amount);
        Thread.sendMessage("The account " + ID + " now has the balance of £" + dest_account_new_bal);
        releaseLock();
        choices();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void exit(){
        Thread.sendMessage("Thank you for using Brunel Sachs International. Have a jolly nice day.");
        releaseLock();
        //System.exit(0);

    }
}
