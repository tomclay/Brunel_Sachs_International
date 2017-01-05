package com.brunel.sachs.Server;

import java.lang.*;
import java.lang.Thread;
import java.util.Random;

/**
 * Created by Tom Clay ESQ. on 05/01/2017.
 */
public abstract class Transaction extends java.lang.Thread {

    protected static Account account;
    private static MessageHandler messageHandler;
    private final Random r = new Random();

    public Transaction(Account account, MessageHandler messageHandler){
        this.account = account;
        this.messageHandler = messageHandler;
    }

    protected Transaction() {
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



//    public void choices(){
//
//
//        int user_choice = 0;
//
//        while (true)
//        {
//            try {
//                user_choice = Integer.parseInt(Thread.incomingMessage().take());
//                break;
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
//
//
//        switch (user_choice){
//            case 1: balance();
//                break;
//            case 2: deposit();
//                break;
//            case 3: withdraw();
//                break;
//            case 4: transfer();
//                break;
//            case 5: exit();
//                break;
//            default: exit();
//                break;
//        }
//
//        user_choice = 0;
//
//    }

//    public void deposit() {
//
//        Thread.sendMessage("Please enter the amount to deposit.");
//
//        String amount = "";
//        try {
//            releaseLock();
//            amount = Thread.incomingMessage().take();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//
//        float result =
//        Thread.sendMessage("Your new balance is: £" + database.accBalance + ". What would you like to do? 1. See my balance 2. Deposit funds 3. Withdraw funds 4. Transfer funds 5. Exit");
//        releaseLock();
//        choices();
//    }


 //   public void withdraw() {
//        Thread.sendMessage("Please enter the amount to withdraw.");
//        float amount = 0;
//
//        try {
//            releaseLock();
//            amount = Float.parseFloat(Thread.incomingMessage().take());
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//
//
//        float result = Database.subtract(amount);
//        Thread.sendMessage("Your new balance is: £" + database.accBalance + ". What would you like to do? 1. See my balance 2. Deposit funds 3. Withdraw funds 4. Transfer funds 5. Exit");
//        releaseLock();
//        choices();
//
//    }
//
//
//    public void transfer() {
//
//        Thread.sendMessage("Please enter the ID of the account you wish to transfer money to.");
//
//        try {
//            releaseLock();
//            String ID = Thread.incomingMessage().take();
//
//        Thread.sendMessage("Please enter the amount you wish to transfer.");
//        float amount = Float.parseFloat(input.nextLine());
//        float dest_account_new_bal = database.wire(ID,amount);
//        Thread.sendMessage("The account " + ID + " now has the balance of £" + dest_account_new_bal);
//        releaseLock();
//        choices();
//
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//    }
//
//    public void exit(){
//        Thread.sendMessage("Thank you for using Brunel Sachs International. Have a jolly nice day.");
//        releaseLock();
//        //System.exit(0);
//
//    }