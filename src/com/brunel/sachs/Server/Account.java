package com.brunel.sachs.Server;

import java.lang.*;
import java.util.Random;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.brunel.sachs.Server.Database.get_bal;


/**
 * Created by tomclay on 08/12/2016.
 */

public class Account {

    private static Database database;
    private static final boolean[] writeLock = new boolean[10];
    private static final int[] readLocks = new int[10];
    private static final boolean[] writeLockWaiting = new boolean[10];

    /**
     * The main method creates a series of transactions (which are extensions of
     * the Thread class) and runs them concurrently. In order to help them run
     * concurrently instead of sequentially, the first instruction in the run
     * method of each transaction is a sleep for 0 to 100 milliseconds
     * @param args the command line arguments
     /*/


    /**
     * Acquires a read lock for a particular account if that account does not have
     * a current write lock. The read lock will allow other read locks, but not
     * write locks, to be established
     * @param i index of the account for which a lock is requested
     * @return true if the lock is successfully acquired, false otherwise
     */
    public static boolean acquireReadLock(int i) {
        if (!writeLock[i] && !writeLockWaiting[i]) {
            readLocks[i]++;
            return true;
        }
        return false;
    }

    /**
     * Acquires a write lock for a particular account if no other locks (read or
     * write) are currently in place. No other locks can be established after a
     * write lock is in place
     * @param i index of the account for which a lock is requested
     * @return true if the lock is successfully acquired, false otherwise
     */
    public static boolean acquireWriteLock(int i) {
        if (readLocks[i] == 0 && !writeLock[i]) {
            writeLock[i] = true;
            writeLockWaiting[i] = false;
            return true;
        }
        writeLockWaiting[i] = true;
        return false;
    }

    /**
     * Releases one read lock from the specified account
     * @param i index of the account to be released
     */
    public static void releaseReadLock(int i) {
        readLocks[i]--;
    }

    /**
     * Removes the write lock from the specified account
     * @param i index of the account to be released
     */
    public static void releaseWriteLock(int i) {
        writeLock[i] = false;
    }

    /**
     * A function for reading the balance of an account with index i
     * @param i the index of the desired account
     * @return the balance of the requested account
     */
    public static float getBalance(int i) {
        return get_bal(i);
    }

    /**
     * Modifies the balance of an account with index i
     * @param i the index of the account to be modified
     * @param newBalance the new value of the account's balance
     */

    public static float deposit(int i, float amount) {
        float oldBalance = Database.get_bal(i);
        float newBalance = oldBalance + amount;
        Database.update_bal(i, newBalance);
        return newBalance;
    }

    public static float withdraw(int i, float amount) {
        float oldBalance = Database.get_bal(i);
        float newBalance = oldBalance + amount;
        Database.update_bal(i, newBalance);
        return newBalance;
    }

    public static float transfer(int i, int y, float amount) {
        float dest_account_new_bal = 0;
        withdraw(i,amount);
        dest_account_new_bal = deposit(y,amount);
        return dest_account_new_bal;

    }


}