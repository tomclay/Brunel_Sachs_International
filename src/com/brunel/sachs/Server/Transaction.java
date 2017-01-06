package com.brunel.sachs.Server;

import com.brunel.sachs.Server.Operations.*;
import java.net.Socket;
import java.util.Random;
import java.util.logging.Level;


/**
 * Created by Tom Clay ESQ.
 */

public class Transaction extends java.lang.Thread {

    /**
     * The transaction class (which extents the Thread class) runs transactions
     * concurrently. The class interacts with the client, forming the transaction
     * which is then passed to the Accounts class. All messages (incoming and
     * outgoing) are sent through the MessageHandler class.
     *
     * In order to avoid concurrency issues, the run method of each transaction is
     * set to sleep for 0 to 100 milliseconds.
     */

    protected static Account account;
    private static MessageHandler messageHandler;
    // Keep the account ID of the user for the duration of the transaction
    private static int account_ID;
    private final Random r = new Random();
    Socket s_socket;
    private String threadName;

    /**
     * The constructor to initialise the transaction with the socket, instance of the Account class,
     * and the name of the thread. All methods besides run are protected to limit their scope.
     *
     * @param formServer The server socket
     * @param account Instance of the Account class
     * @param name Name of the thread
     */

    public Transaction(Socket formServer, Account account, String name) {
        this.account = account;
        this.s_socket = formServer;
        this.threadName = name;
    }

    /**
     * The protected constructor so sub-classes do not have to inherit
     * unnecessary variables
     */

    protected Transaction() {
    }

    /**
     * The run method begins the transaction with the client.
     *
     * The account ID is ascertained form the user, stored,
     * then thr user is asked what to do next.
     */

    public void run() {

        // Set MessageHandler to the socket
        messageHandler = new MessageHandler(s_socket);
        messageHandler.sendMessage("Welcome to Brunel Sachs International. Please enter your account number");
        try {
            account_ID = Integer.parseInt(messageHandler.incomingMessage().take());
        } catch (InterruptedException e) {
            transactionLogging.log(Level.SEVERE, "Cannot get account number");
        }
        messageHandler.sendMessage("What would you like to do? 1. See my balance 2. Deposit funds 3. Withdraw funds 4. Transfer funds");
        choices();
    }

    /**
     * This method captures what the user wishes to do via numbered options.
     */

    protected void choices() {

        int user_choice;
        while (true) {
            try {
                // Get the users option
                user_choice = Integer.parseInt(messageHandler.incomingMessage().take());
                break;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        // Run the relevant method
        switch (user_choice) {
            case 1:
                new Balance(account_ID).start();
                break;
            case 2:
                start_deposit();
                break;
            case 3:
                start_withdraw();
                break;
            case 4:
                start_transfer();
                break;
        }
    }

    /**
     * This method gathers user information before passing it to the
     * deposit transaction class. This is done so to not hang threads
     * waiting for input.
     */

    protected void start_deposit() {

        messageHandler.sendMessage("Please enter the amount to deposit.");
        float deposit = 00;
        try {
            deposit = Float.parseFloat(messageHandler.incomingMessage().take());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // Start the transaction using the account ID and deposit captured
        new Deposit(account_ID, deposit).start();
    }

    /**
     * The method to start a withdrawal transaction as above.
     */

    protected void start_withdraw() {

        messageHandler.sendMessage("Please enter the amount to withdraw.");
        float withdrawal = 00;
        try {
            withdrawal = Float.parseFloat(messageHandler.incomingMessage().take());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        new Withdraw(account_ID, withdrawal).start();

    }

    /**
     * The method to start a transfer transaction. This method uses the same logic
     * as the previous, however two pieces of information are required.
     */

    protected void start_transfer() {

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
     * This method encapsulates the sleep method of Thread to avoid using a try-
     * catch block every time.
     *
     * @param milliseconds Amount of time to sleep the thread.
     */

    protected void sleep(int milliseconds) {
        try {
            java.lang.Thread.sleep(milliseconds);
        } catch (InterruptedException ex) {
        }
    }

    /**
     * This method attempts to acquire a read lock for a desired account. If it
     * fails, it sleeps for 10 milliseconds and then tries again.
     *
     * @param i index of the account for which the lock is requested
     */
    protected void acquireReadLock(int i) {
        while (!account.acquireReadLock(i)) {
            this.sleep(10);
        }
        transactionLogging.log(Level.INFO, java.lang.Thread.currentThread() + " acquired read lock for account " + i);
    }

    /**
     * This method attempts to acquire a write lock for a desired account. If it
     * fails, it sleeps for 10 milliseconds and then tries again.
     *
     * @param i index of the account for which the lock is requested
     */
    protected void acquireWriteLock(int i) {
        while (!account.acquireWriteLock(i)) {
            this.sleep(10);
        }
        transactionLogging.log(Level.INFO, java.lang.Thread.currentThread() + " acquired write lock for account " + i);
    }

    /**
     * This method releases a read lock for a specified account
     *
     * @param i index of the account to be released
     */
    protected void releaseReadLock(int i) {
        account.releaseReadLock(i);
        transactionLogging.log(Level.INFO, java.lang.Thread.currentThread() + " released read lock for account " + i);
    }

    /**
     * This method releases a write lock for a specified account
     *
     * @param i index of the account to be released
     */
    protected void releaseWriteLock(int i) {
        account.releaseWriteLock(i);
        transactionLogging.log(Level.INFO, java.lang.Thread.currentThread() + " released write lock for account " + i);
    }

    /**
     * This method is called in the run method of each different transaction to
     * give a randomised delay before execution begins.
     */

    protected void delay() {
        this.sleep(r.nextInt(100));
    }
}


