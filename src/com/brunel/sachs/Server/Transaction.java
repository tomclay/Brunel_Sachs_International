package com.brunel.sachs.Server;

import com.brunel.sachs.Server.Operations.*;
import java.net.Socket;
import java.util.Random;


/**
 * To-do
 * Why does the acc ID disappear?
 * Test all account methods individually
 * Tidy up code
 * test with 4 clients
 * develop test script
 * Add comments
 */

/**
 * Created by Tom Clay ESQ. on 05/01/2017.
 */
public class Transaction extends java.lang.Thread {

    protected static Account account;
    private static MessageHandler messageHandler;
    private final Random r = new Random();
    private int account_ID;
    Socket s_socket;
    private String threadName;


    public Transaction(Socket formServer, Account account, String name) {
        this.account = account;
        this.s_socket = formServer;
        this.threadName = name;

    }


    protected Transaction() {
    }

    public void run(){

        messageHandler = new MessageHandler(s_socket);

        messageHandler.sendMessage("Welcome to Brunel Sachs International. Please enter your account number");

        try {
            account_ID =  Integer.parseInt(messageHandler.incomingMessage().take());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        messageHandler.sendMessage("What would you like to do? 1. See my balance 2. Deposit funds 3. Withdraw funds 4. Transfer funds");

        choices();

    }


        protected void choices(){

        int user_choice = 0;

        while (true)
        {
            try {
                user_choice = Integer.parseInt(messageHandler.incomingMessage().take());
                break;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        switch (user_choice){
            case 1: new Balance(account_ID).start();
                break;
            case 2: start_deposit();
                break;
            case 3: start_withdraw();
                break;
            case 4: start_transfer();
                break;
        }

        user_choice = 0;

    }

    protected void start_deposit(){

        messageHandler.sendMessage("Please enter the amount to deposit.");

        float deposit = 00;
        try {
            deposit = Float.parseFloat(messageHandler.incomingMessage().take());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        new Deposit(account_ID, deposit).start();

    }

    protected void start_withdraw(){


        messageHandler.sendMessage("Please enter the amount to withdraw.");

        float withdrawal = 00;
        try {
            withdrawal = Float.parseFloat(messageHandler.incomingMessage().take());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        new Withdraw(account_ID, withdrawal).start();

    }

    protected void start_transfer(){

        messageHandler.sendMessage("Please enter the account number of the recipient.");

        int recip_acc_ID = 0;

        try {
            recip_acc_ID = Integer.parseInt(messageHandler.incomingMessage().take());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        messageHandler.sendMessage("And the amount you wish to transfer.");

        float amount = 00;


        try {
            amount = Float.parseFloat(messageHandler.incomingMessage().take());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        new Transfer(account_ID, recip_acc_ID, amount);
    }

    /**
     *This method encapsulates the sleep method of Thread to avoid using a try-
     * catch block every time
     * @param milliseconds
     */
    protected void sleep(int milliseconds){
        try {
            java.lang.Thread.sleep(milliseconds);
        } catch (InterruptedException ex) {
            //ignore this. Not gonna happen
        }
    }

    /**
     * This method attempts to acquire a read lock for a desired account. If it
     * fails, it sleeps for 10 milliseconds and then tries again.
     * @param i index of the account for which the lock is requested
     */
    protected void acquireReadLock(int i){
        while (!account.acquireReadLock(i)){
            this.sleep(10);
        }
        System.out.println(java.lang.Thread.currentThread() + " acquired read lock for account " + i);
    }

    /**
     *This method attempts to acquire a write lock for a desired account. If it
     * fails, it sleeps for 10 milliseconds and then tries again.
     * @param i index of the account for which the lock is requested
     */
    protected void acquireWriteLock(int i){
        while (!account.acquireWriteLock(i)){
            this.sleep(10);
        }
        System.out.println(java.lang.Thread.currentThread() + " acquired write lock for account " + i);
    }

    /**
     * This method releases a read lock for a specified account
     * @param i index of the account to be released
     */
    protected void releaseReadLock(int i){
        account.releaseReadLock(i);
        System.out.println(java.lang.Thread.currentThread() + " released read lock for account " + i);
    }

    /**
     * This method releases a write lock for a specified account
     * @param i index of the account to be released
     */
    protected void releaseWriteLock(int i){
        account.releaseWriteLock(i);
        System.out.println(Thread.currentThread() + " released write lock for account " + i);
    }

    /**
     * This method is called in the run method of each different transaction to
     * give a randomized delay before execution begins. This leads to a more interleaved
     * execution and proves the atomicity of the program
     */
    protected void delay(){
        this.sleep(r.nextInt(100));
    }

}


